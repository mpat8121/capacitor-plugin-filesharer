package com.interapptive.filesharer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.File;

import androidx.activity.result.ActivityResult;
import androidx.core.content.FileProvider;

@CapacitorPlugin(name = "FileSharer")
public class FileSharerPlugin extends Plugin {

    private FileSharer implementation = new FileSharer();
    private static final String FILESHARE_DIR = "capfilesharer";

    @PluginMethod
    public void share(PluginCall call) {
        String fileProviderName = getContext().getPackageName() + ".filesharer.fileprovider";
        JSObject data = call.getData();
        JSObject ret = new JSObject();
        if(!data.has("filename")) {
            ret.put("result", false);
            ret.put("message", "Missing argument filename");
            call.resolve(ret);
        }
        if(!data.has("contentType")) {
            ret.put("result", false);
            ret.put("message", "Missing argument contentType");
            call.resolve(ret);
        }
        if(!data.has("base64Data")) {
            ret.put("result", false);
            ret.put("message", "Missing argument base64Data");
            call.resolve(ret);
        }

        String filename = data.getString("filename");
        String base64Data = data.getString("base64Data");
        String contentType = data.getString("contentType");

        File cachePath = implementation.getCacheDir(getContext(), FILESHARE_DIR);
        File cachedFile = new File(cachePath, filename);

        boolean fileWrite = implementation.writeFile(cachedFile, base64Data);
        if(!fileWrite) {
            ret.put("result", false);
            ret.put("message", "Unable to write file");
            Log.d(getLogTag(), "Unable to write file");
            call.resolve(ret);
        }
        Uri contentUri;
        try {
            contentUri = FileProvider.getUriForFile(getContext(), fileProviderName, cachedFile);
        } catch (IllegalArgumentException ex) {
            contentUri = null;
            Log.e(getLogTag(), ex.getLocalizedMessage());
            ret.put("result", false);
            ret.put("message", "Unable to get file reference: " + ex.getLocalizedMessage());
            call.resolve(ret);
        }
        final Intent shareIntent = implementation.createShareIntent(contentUri, contentType, filename);
        try {
            startActivityForResult(call, shareIntent, "handleFileShare");
        } catch (Exception exception) {
            Log.e(getLogTag(), exception.getLocalizedMessage());
            ret.put("result", false);
            ret.put("message", exception.getLocalizedMessage());
            call.resolve(ret);
        }
    }

    @ActivityCallback
    private void handleFileShare(PluginCall call, ActivityResult result) {
        JSObject ret = new JSObject();
        final int grc = result.getResultCode();
        // ACTION_SEND Returns 0 by default
        if(grc == Activity.RESULT_CANCELED) {
            ret.put("result", true);
        } else {
            ret.put("result", false);
        }
        ret.put("message", "Share Sheet Opened with Code: " + String.valueOf(grc));
        call.resolve(ret);
    }
}
