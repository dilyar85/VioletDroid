package com.github.dilyar85.violetdroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity class
 */

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.element_recycler_view)
    RecyclerView mRecyclerView;

    RecyclerAdapter mRecyclerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * Init views
     */
    private void initView() {

        CanvasFragment fragment = new CanvasFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_share) shareCurrentDiagram();
        if(id == R.id.menu_save) saveDiagram();

        return true;

    }



    private void saveDiagram() {

        String diagramPth = getCurrentDiagram();
        if(diagramPth == null) return;
        try {
            AVFile file = AVFile.withAbsoluteLocalPath("LeanCloud.png", diagramPth);
            file.saveInBackground(new SaveCallback() {

                @Override
                public void done(AVException e) {

                    Log.e(LOG_TAG, "Done!");
                }
            }, new ProgressCallback() {

                @Override
                public void done(Integer integer) {

                    Log.e(LOG_TAG, "Progress: " + integer);
                }
            });

        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

    }



    private String getCurrentDiagram() {

        ViewGroup canvasView = (ViewGroup) findViewById(R.id.fragment_container);
        canvasView.setDrawingCacheEnabled(true);
        Bitmap sharedBitmap = canvasView.getDrawingCache();
        try {
            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
            File file = new File(getCacheDir(), now + ".png");
            Log.e(LOG_TAG, "Path: " + file.getAbsolutePath());
            FileOutputStream fOut = new FileOutputStream(file);
            sharedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            canvasView.setDrawingCacheEnabled(false);
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            Toast.makeText(this, getString(R.string.toast_no_available_space), Toast.LENGTH_LONG).show();
            return null;
        }
    }



    /**
     * Share the current diagram with any appropriate applications in the device
     */
    private void shareCurrentDiagram() {

        String diagramPath = getCurrentDiagram();
        if(diagramPath == null) return;

        //Create a sharing intent and start it.
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(diagramPath)));
        intent.setType("image/*");
        PackageManager pm = getPackageManager();
        if (intent.resolveActivity(pm) != null) startActivity(intent);
        else
            Toast.makeText(this, getString(R.string.toast_no_intent_applications), Toast.LENGTH_LONG).show();

    }

}

