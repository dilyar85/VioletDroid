package com.github.dilyar85.violetdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;
import com.github.dilyar85.violetdroid.customView.CanvasLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.avos.avoscloud.AVPersistenceUtils.getCacheDir;

/**
 * A canvas fragment from MainActivity to allow users to draw diagrams
 */

public class MainFragment extends Fragment implements RecyclerAdapter.ElementViewListener {

    final static String LOG_TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.canvas_layout)
    CanvasLayout mCanvasLayout;


    @BindView(R.id.element_recycler_view)
    RecyclerView mRecyclerView;

    RecyclerAdapter mRecyclerAdapter;

    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        RecyclerAdapter.setElementViewListener(this);
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_share) shareCurrentDiagram();
        else if (id == R.id.menu_save) saveDiagram();
        else if(id == R.id.menu_diagram_collections) displayDiagramCollections();
        return true;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initView();

        return view;

    }



    /**
     * Init recycler view (the tool bar showing diagram element)
     */
    private void initView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerAdapter = new RecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }



    @Override
    public void toolElementDoubleTapped(View view) {

        int tag = (int) view.getTag(R.id.view_resource_key);
        View testLayout = getActivity().getLayoutInflater().inflate(R.layout.indicator_layout, mCanvasLayout, false);
        ImageView targetView = (ImageView) testLayout.findViewById(R.id.center_image_view);
        targetView.setImageResource(tag);
        mCanvasLayout.addView(testLayout);

    }



    /**
     * Display the diagram collections of  user
     */
    private void displayDiagramCollections() {
        Fragment diagramCollectionsFragment = new DiagramCollectionsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, diagramCollectionsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    /**
     * Save current diagram into backend service
     */
    private void saveDiagram() {

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "Please sign in to save the diagram", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog = ProgressDialog.show(getActivity(), null, "Saving now...");

        String diagramPth = getCurrentDiagramAsPicture();
        if (diagramPth != null)
            try {
                AVFile file = AVFile.withAbsoluteLocalPath("LeanCloud.png", diagramPth);
                AVObject diagramObject = new AVObject("Diagrams");
                diagramObject.put("file", file);
                diagramObject.put("user", currentUser.getUsername());
                diagramObject.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(AVException e) {

                        mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
                        Log.e(LOG_TAG, "Done!");
                    }
                });
            } catch (FileNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage());
            }

    }



    /**
     * Helper method to get the current diagram as picture
     * @return diagram picture in png format
     */
    private String getCurrentDiagramAsPicture() {


        mCanvasLayout.setDrawingCacheEnabled(true);
        Bitmap sharedBitmap = mCanvasLayout.getDrawingCache();
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
            mCanvasLayout.setDrawingCacheEnabled(false);
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            Toast.makeText(getActivity(), getString(R.string.toast_no_available_space), Toast.LENGTH_LONG).show();
            return null;
        }
    }



    /**
     * Share the current diagram with any appropriate applications in the device
     */
    private void shareCurrentDiagram() {

        String diagramPath = getCurrentDiagramAsPicture();
        if (diagramPath == null) return;

        //Create a sharing intent and start it.
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(diagramPath)));
        intent.setType("image/*");
        PackageManager pm = getActivity().getPackageManager();
        if (intent.resolveActivity(pm) != null) startActivity(intent);
        else
            Toast.makeText(getActivity(), getString(R.string.toast_no_intent_applications), Toast.LENGTH_LONG).show();

    }



}