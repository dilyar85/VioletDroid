package com.github.dilyar85.violetdroid;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;
import com.github.dilyar85.violetdroid.customView.CanvasLayout;
import com.github.dilyar85.violetdroid.utility.Utility;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    /**
     * Constants for backend database key names.
     */
    public class LeanCloudConstant {
        public static final String CLASS_DIAGRAM = "Diagrams";
        public static final String DIAGRAM_OBJECT_KEY_FILE = "file";
        public static final String DIAGRAM_OBJECT_KEY_USERNAME = "user";
        public static final String DIAGRAM_OBJECT_KEY_ID = "objectId";

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        RecyclerAdapter.setElementViewListener(this);
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_fragment, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_share) shareDiagramFromCanvasLayout();
        else if (id == R.id.menu_save) showSavingDiagramDialog();
        else if (id == R.id.menu_diagram_collections) displayDiagramCollections();
        else if (id == R.id.menu_sign_out) signOut();
        return true;
    }



    /**
     * Sign out and go back to log in activity
     */
    private void signOut() {

        AVUser.logOut();
        Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginActivity);
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
    public void elementToolDoubleTapped(View view) {

        int tag = (int) view.getTag(R.id.view_resource_key);
        View testLayout = getActivity().getLayoutInflater().inflate(R.layout.indicator_layout, mCanvasLayout, false);
        ImageView targetView = (ImageView) testLayout.findViewById(R.id.center_image_view);
        targetView.setImageResource(tag);
        targetView.setTag(tag);
        mCanvasLayout.addView(testLayout);

    }



    /**
     * An alert dialog to help user confirm to save the current diagram
     */
    public void showSavingDiagramDialog() {

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), R.string.toast_prompt_sign_in, Toast.LENGTH_SHORT).show();
            return;
        }
        final String userName = currentUser.getUsername();

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_save_diagram, null);
        alert.setView(view);
        final EditText editText = (EditText) view.findViewById(R.id.save_diagram_name_edittext);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.save_background_checkbox);

        alert.setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                String savedDiagramName = editText.getText().toString();
                boolean savedWithBackground = checkBox.isChecked();
                saveDiagramInLeanCloud(userName, savedDiagramName, savedWithBackground);
            }
        });

        alert.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                Toast.makeText(getActivity(), R.string.toast_diagram_not_saved, Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();

    }



    /**
     * Share the current diagram with any appropriate applications in the device
     */
    private void shareDiagramFromCanvasLayout() {

        Intent intent = null;
        try {
            intent = Utility.createSharingIntent(getActivity(), mCanvasLayout);
        } catch (IOException e) {
            Toast.makeText(getActivity(), getString(R.string.toast_no_available_space), Toast.LENGTH_LONG).show();
        }

        if (intent != null)
            startActivity(intent);
        else
            Toast.makeText(getActivity(), getString(R.string.toast_no_intent_applications), Toast.LENGTH_LONG).show();

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
    private void saveDiagramInLeanCloud(String userName, String givenName, boolean hasBackground) {

        mProgressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_saving_now));

        try {
            String diagramPth = Utility.getCurrentDiagramAsPicture(mCanvasLayout, hasBackground);
            String fileName = givenName == null || givenName.length() == 0 ? getString(R.string.default_diagram_name) : givenName;
            fileName += ".png";
            AVFile file = AVFile.withAbsoluteLocalPath(fileName, diagramPth);
            AVObject diagramObject = new AVObject(LeanCloudConstant.CLASS_DIAGRAM);
            diagramObject.put(LeanCloudConstant.DIAGRAM_OBJECT_KEY_FILE, file);
            diagramObject.put(LeanCloudConstant.DIAGRAM_OBJECT_KEY_USERNAME, userName);
            diagramObject.saveInBackground(new SaveCallback() {

                @Override
                public void done(AVException e) {

                    mProgressDialog.dismiss();
                    if(e == null)
                        Toast.makeText(getActivity(), R.string.toast_saved_diagram_successfully, Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getActivity(), R.string.save_failed, Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (IOException e) {
            Toast.makeText(getActivity(), getString(R.string.toast_no_available_space), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, e.getMessage());
        }
    }

}

