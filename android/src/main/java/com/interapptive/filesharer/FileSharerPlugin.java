package com.interapptive.filesharer;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FileSharer")
public class FileSharerPlugin extends Plugin {

    private FileSharer implementation = new FileSharer();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void share(PluginCall call) {
        JSObject data = call.getData();
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

        File cachedFile = new File(getCacheDir(), filename);
        try (FileOutputStream fos = new FileOutputStream(cachedFile)) {
            byte[] decodedData = Base64.decode(base64Data, Base64.DEFAULT);
            fos.write(decodedData);
            fos.flush();
        } catch (IOException e) {
            Log.e(getLogTag(), e.getMessage());
            call.reject("Couldn't cache file");
            return;
        } catch (IllegalArgumentException e) {
            call.reject("Invalid file provided");
            return;
        }

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);

        Uri contentUri = FileSharerProvider.getUriForFile(getContext(), fileProviderName, cachedFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setTypeAndNormalize(contentType);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(call, Intent.createChooser(call, shareIntent, "handleFileShare");
    }

    @ActivityCallback
    private void handleFileShare(PluginCall call, ActivityResult result) {
        call.resolve();
    }
}
