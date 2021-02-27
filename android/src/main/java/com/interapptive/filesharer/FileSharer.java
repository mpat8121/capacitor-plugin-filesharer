package com.interapptive.filesharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSharer {
    private static final String TAG = "--- IA FILESHARE PLUGIN";

    public boolean writeFile(File cachedFile, String base64Data) {
        try (FileOutputStream fos = new FileOutputStream(cachedFile)) {
            byte[] decodedData = Base64.decode(base64Data, Base64.DEFAULT);
            fos.write(decodedData);
            fos.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public Intent createShareIntent(Uri contentUri, String contentType) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setTypeAndNormalize(contentType);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return shareIntent;
    }

    public File getCacheDir(Context context, String directory) {
        File cacheDir = new File(context.getFilesDir(), directory);
        if (!cacheDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cacheDir.mkdirs();
        } else {
            for (File f : cacheDir.listFiles()) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }
        }
        return cacheDir;
    }
}
