package com.interapptive.filesharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileSharer {
    private static final String TAG = "--- IA FILESHARE PLUGIN";
    private Context context;

    public FileSharer(Context context) {
        this.context = context;
    }

    public boolean writeFile(File cachedFile, String base64Data) {
        try (FileOutputStream outputStream = new FileOutputStream(cachedFile)) {
            byte[] decodeBase64 = Base64.decode(base64Data, Base64.DEFAULT);
            outputStream.write(decodeBase64);
            outputStream.flush();
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

    public Intent createShareMultipleIntent(ArrayList<Uri> files) {
        Intent shareMultipleIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareMultipleIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        shareMultipleIntent.setType("*/*");
        shareMultipleIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareMultipleIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareMultipleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareMultipleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return shareMultipleIntent;
    }

    public File getDirectory(String directory) {
        File fileDirectory = new File(context.getFilesDir(), directory);
        // Create the folder if it hasn't been created before
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        } else {
            // Otherwise clean the directory in preparation of new file
            for (File file : fileDirectory.listFiles()) {
                file.delete();
            }
        }
        return fileDirectory;
    }
}
