package com.github.dilyar85.violetdroid.fragment;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.github.dilyar85.imageloader.ImageLoader;
import com.github.dilyar85.violetdroid.R;
import com.github.dilyar85.violetdroid.utility.Utility;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment class to display selected diagram's detail
 */
public class DiagramDetailFragment extends Fragment {

    private static final String LOG_TAG = DiagramDetailFragment.class.getSimpleName();
    @BindView(R.id.selected_imageView)
    ImageView mImageView;
    @BindView(R.id.detail_diagram_name_textview)
    TextView mTextView;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_fragment, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_share) shareDiagramFromDetailView();
        else if (id == R.id.menu_delete_diagram) showConfirmDialog();

        return true;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diagram_detail, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }



    /**
     * Init data of the fragment
     */
    private void initData() {

        Bundle bundle = this.getArguments();
        String diagramUrl = bundle.getString(DiagramCollectionsFragment.BUNDLE_KEY_DIAGRAM_URL);
        if (diagramUrl != null)
            ImageLoader.getInstance().loadImageWithPath(diagramUrl, mImageView);
        String diagramName = bundle.getString(DiagramCollectionsFragment.BUNDLE_KEY_DIAGRAM_NAME);
        if (diagramName != null)
            mTextView.setText(diagramName);


    }



    /**
     * Share the current diagram with any appropriate applications in the device
     */
    private void shareDiagramFromDetailView() {

        Intent intent = null;
        try {
            intent = Utility.createSharingIntent(getActivity(), mImageView);
        } catch (IOException e) {
            Toast.makeText(getActivity(), getString(R.string.toast_no_available_space), Toast.LENGTH_LONG).show();
        }
        if (intent != null)
            startActivity(intent);
        else
            Toast.makeText(getActivity(), getString(R.string.toast_no_intent_applications), Toast.LENGTH_LONG).show();

    }



    /**
     * Show an alert dialog to confirm deleting
     */
    private void showConfirmDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(R.string.dialog_title_delete_diagram);
        alert.setPositiveButton(R.string.dialog_delete_button, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                deleteDiagram();
            }
        });

        alert.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }



    /**
     * Delete current diagram from LeanCloud and go back to DiagramCollectionsFragment
     */
    private void deleteDiagram() {

        String objectId = getArguments().getString(DiagramCollectionsFragment.BUNDLE_KEY_DIAGRAM_OBJECT_ID);

        AVQuery<AVObject> query = new AVQuery<>(MainFragment.LeanCloudConstant.CLASS_DIAGRAM);
        query.whereEqualTo(MainFragment.LeanCloudConstant.DIAGRAM_OBJECT_KEY_ID, objectId);
        query.getFirstInBackground(new GetCallback<AVObject>() {

            @Override
            public void done(AVObject avObject, AVException e) {

                if (avObject != null) {
                    avObject.deleteInBackground();
                    Toast.makeText(getActivity(), R.string.delete_successfully, Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), R.string.delete_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}



