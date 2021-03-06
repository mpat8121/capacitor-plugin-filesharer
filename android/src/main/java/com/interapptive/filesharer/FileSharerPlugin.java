package com.interapptive.filesharer;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
        if(!data.has("filename" )
                || data.getString("filename") == null
                || data.getString("filename").isEmpty()) {
            ret.put("result", false);
            ret.put("message", "Missing argument filename");
            call.resolve(ret);
            return;
        }
        if(!data.has("contentType")
                || data.getString("contentType") == null
                || data.getString("contentType").isEmpty()) {
            ret.put("result", false);
            ret.put("message", "Missing argument contentType");
            call.resolve(ret);
            return;
        }
        if(!data.has("base64Data")
                || data.getString("base64Data") == null
                || data.getString("base64Data").isEmpty()) {
            ret.put("result", false);
            ret.put("message", "Missing argument base64Data");
            call.resolve(ret);
            return;
        }

        String filename = data.getString("filename");
        String base64Data = data.getString("base64Data");
        String contentType = data.getString("contentType");
        String header = data.getString("header");

        File cachePath = implementation.getDirectory(FILESHARE_DIR);
        File cachedFile;
        try {
            cachedFile = new File(cachePath, filename);
        } catch (Exception exception) {
            Log.e(getLogTag(), "Error caching file");
            ret.put("result", false);
            ret.put("message", "Error caching file");
            call.resolve(ret);
            return;
        }

        try {
            boolean fileWrite = implementation.writeFile(cachedFile, base64Data);
            if(!fileWrite) {
                ret.put("result", false);
                ret.put("message", "Unable to write file");
                Log.d(getLogTag(), "Unable to write file");
                call.resolve(ret);
                return;
            }
        } catch (Exception exception) {
            Log.e(getLogTag(), exception.getLocalizedMessage());
            ret.put("result", false);
            ret.put("message", "Unable to write file");
            Log.d(getLogTag(), "Unable to write file");
            call.resolve(ret);
            return;
        }

        Uri contentUri;
        try {
            contentUri = FileProvider.getUriForFile(getContext(), fileProviderName, cachedFile);
        } catch (IllegalArgumentException ex) {
            Log.e(getLogTag(), ex.getLocalizedMessage());
            ret.put("result", false);
            ret.put("message", "Unable to get file reference: " + ex.getLocalizedMessage());
            call.resolve(ret);
            return;
        }

        try {
            final Intent shareIntent = implementation.createShareIntent(contentUri, contentType, header, filename);
            Intent chooseIntent = Intent.createChooser(shareIntent, header);
            startActivityForResult(call, chooseIntent, "handleFileShare");
        } catch (Exception exception) {
            Log.e(getLogTag(), exception.getLocalizedMessage());
            ret.put("result", false);
            ret.put("message", exception.getLocalizedMessage());
            call.resolve(ret);
            return;
        }
    }

    @PluginMethod
    public void shareMultiple(PluginCall call) {
        JSObject ret = new JSObject();
        JSObject data = call.getData();
        if(!data.has("files")
                || call.getArray("files") == null
                || call.getArray("files").length() == 0) {
            ret.put("result", false);
            ret.put("message", "Missing argument files");
            call.resolve(ret);
            return;
        }
        String header = "Share your files";
        if(data.has("header") && !data.getString("header").isEmpty()) {
            header = data.getString("header");
        }
        JSArray files = call.getArray("files");
        File cachePath;
        try {
            cachePath = implementation.getDirectory(FILESHARE_DIR);
        } catch(Exception ex) {
            Log.e(getLogTag(), ex.getLocalizedMessage());
            ret.put("result", false);
            ret.put("message", "Unable to get write directory");
            call.resolve(ret);
            return;
        }
        ArrayList<Uri> fileUris = new ArrayList<Uri>();
        for(int i = 0; i < files.length(); i++) {
            try {
                JSONObject file = files.getJSONObject(i);
                if(!file.has("filename") || !file.has("base64Data")) {
                    ret.put("result", false);
                    ret.put("message", "Missing arguments in files");
                    call.resolve(ret);
                    return;
                }
                String filename = file.getString("filename");
                String base64Data = file.getString("base64Data");
                if(filename == "null" || filename.isEmpty()) {
                    ret.put("result", false);
                    ret.put("message", "Missing argument filename");
                    call.resolve(ret);
                    return;
                }
                if(base64Data == "null" || base64Data.isEmpty()) {
                    ret.put("result", false);
                    ret.put("message", "Missing argument filename");
                    call.resolve(ret);
                    return;
                }
                File cachedFile = new File(cachePath, filename);
                try {
                    boolean fileWrite = implementation.writeFile(cachedFile, base64Data);
                    if(!fileWrite) {
                        ret.put("result", false);
                        ret.put("message", "Unable to write file");
                        Log.d(getLogTag(), "Unable to write file");
                        call.resolve(ret);
                        return;
                    }
                } catch (Exception ex) {
                    Log.e(getLogTag(), ex.getLocalizedMessage());
                    ret.put("result", false);
                    ret.put("message", "Unable to write file");
                    Log.d(getLogTag(), "Unable to write file");
                    call.resolve(ret);
                    return;
                }
                Uri fileUri;
                try {
                    fileUri = FileProvider.getUriForFile(getContext(), fileProviderName, cachedFile);
                } catch (IllegalArgumentException ex) {
                    Log.e(getLogTag(), ex.getLocalizedMessage());
                    ret.put("result", false);
                    ret.put("message", "Unable to get file reference: " + ex.getLocalizedMessage());
                    call.resolve(ret);
                    return;
                }
                fileUris.add(fileUri);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Log.e(getLogTag(), ex.getLocalizedMessage());
                ret.put("result", false);
                ret.put("message", "Unable to get process file: " + ex.getLocalizedMessage());
                call.resolve(ret);
                return;
            }
        }
        if(fileUris.size() > 0) {
            final Intent multiShareIntent = implementation.createShareMultipleIntent(fileUris);
            Intent chooser = Intent.createChooser(multiShareIntent, header);
            startActivityForResult(call, chooser, "handleFileShare");
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
            // else statement redundant
            ret.put("result", false);
        }
        ret.put("message", "Share Sheet Opened with Code: " + String.valueOf(grc));
        call.resolve(ret);
    }
}
