package com.interapptive.filesharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.activity.result.ActivityResult;
import androidx.core.content.FileProvider;

@CapacitorPlugin(name = "FileSharer")
public class FileSharerPlugin extends Plugin {

    private FileSharer implementation;
    private Context context;
    private static final String FILESHARE_DIR = "capfilesharer";
    private String fileProviderName;
    public void load() {
        super.load();
        context = getContext();
        implementation = new FileSharer(context);
        fileProviderName = context.getPackageName() + ".filesharer.fileprovider";
    }

    @PluginMethod
    public void share(PluginCall call) {
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

        File cachePath = implementation.getDirectory(FILESHARE_DIR);
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
        final Intent shareIntent = implementation.createShareIntent(contentUri, contentType);
        startActivityForResult(call, shareIntent, "handleFileShare");
    }

    @PluginMethod
    public void shareMultiple(PluginCall call) {
        JSObject ret = new JSObject();
        JSObject data = call.getData();
        if(!data.has("files")) {
            ret.put("result", false);
            ret.put("message", "Missing argument filename");
            call.resolve(ret);
        }
        JSArray files = call.getArray("files");
        File cachePath = implementation.getDirectory(FILESHARE_DIR);
        ArrayList<Uri> fileUris = new ArrayList<Uri>();
        for(int i = 0; i < files.length(); i++) {
            try {
                JSONObject file = files.getJSONObject(i);
                String filename = file.getString("filename");
                String base64Data = file.getString("base64Data");
                File cachedFile = new File(cachePath, filename);
                boolean fileWrite = implementation.writeFile(cachedFile, base64Data);
                Uri fileUri = FileProvider.getUriForFile(getContext(), fileProviderName, cachedFile);
                fileUris.add(fileUri);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(fileUris.size() > 0) {
            final Intent multiShareIntent = implementation.createShareMultipleIntent(fileUris);
            startActivityForResult(call, multiShareIntent, "handleFileShare");
        }
    }

    @ActivityCallback
    private void handleFileShare(PluginCall call, ActivityResult result) {
        call.resolve();
    }
}
