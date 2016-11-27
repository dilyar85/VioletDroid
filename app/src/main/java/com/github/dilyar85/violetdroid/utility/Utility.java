package com.github.dilyar85.violetdroid.utility;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.github.dilyar85.violetdroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static com.avos.avoscloud.AVPersistenceUtils.getCacheDir;
import static com.avos.avoscloud.AVUser.LOG_TAG;

/**
 * A Utility class to avoid duplicated codes
 */

public class Utility {

    /**
     * Utility method to get picture under the given view
     *
     * @param view                given view
     * @param savedWithBackground whether to save the picture with grid background
     * @return internal cache path of this picture
     */
    public static String getCurrentDiagramAsPicture(View view, boolean savedWithBackground) throws IOException {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File file = new File(getCacheDir(), now + ".png");
        Log.e(LOG_TAG, "Path: " + file.getAbsolutePath());
        FileOutputStream fOut = new FileOutputStream(file);
        if (!savedWithBackground) view.setBackgroundResource(0);
        view.setDrawingCacheEnabled(true);
        Bitmap sharedBitmap = view.getDrawingCache();
        sharedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        file.setReadable(true, false);
        view.setDrawingCacheEnabled(false);
        view.setBackgroundResource(R.drawable.main_background);
        return file.getAbsolutePath();
    }



    /**
     * Create a sharing intent which first get the diagram from given view and
     * @param context activity context
     * @param view the view which has the diagram
     * @return the intent if there is application handling sharing intent, otherwise return null
     */
    public static Intent createSharingIntent(Context context, View view) throws IOException {

        String picturePath = Utility.getCurrentDiagramAsPicture(view, true);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(picturePath)));
        intent.setType("image/*");
        PackageManager pm = context.getPackageManager();
        return intent.resolveActivity(pm) != null ? intent : null;
    }
}

