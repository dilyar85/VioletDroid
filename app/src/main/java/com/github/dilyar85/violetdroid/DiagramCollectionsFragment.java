package com.github.dilyar85.violetdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.github.dilyar85.violetdroid.adapter.DiagramsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment class to display all saved diagrams of user in backend service
 */
public class DiagramCollectionsFragment extends Fragment {

    static final String LOG_TAG = DiagramCollectionsFragment.class.getSimpleName();

    private static final int DOWNLOAD_DIAGRAMS_DONE = 0;
    public static final String KEY_DIAGRAM_URL = "key_selected_diagram_url";

    private ProgressDialog mProgressDialog;
    private List<AVFile> mDiagramFiles;

    @BindView(R.id.show_all_image_gridView)
    GridView mGridView;
    @BindView(R.id.diagram_count_textView)
    TextView mCountTextView;
    @BindView(R.id.user_name_textview)
    TextView mUserNameTextView;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_diagram_collections, container, false);
        ButterKnife.bind(this, rootView);

        fetchDiagramsFromLeanCloud();
        setGridViewClickEvent();
        return rootView;
    }



    /**
     * Download all diagrams saved by current user
     */
    private void fetchDiagramsFromLeanCloud() {

        mProgressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_loading));

        AVQuery<AVObject> query = new AVQuery<>(MainFragment.LeanCloudConstant.CLASS_DIAGRAM);
        query.whereEqualTo(MainFragment.LeanCloudConstant.DIAGRAM_OBJECT_KEY_USERNAME, AVUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<AVObject>() {

            @Override
            public void done(List<AVObject> list, AVException e) {

                if (list != null) {
                    mDiagramFiles = new ArrayList<>();
                    for (AVObject avObject : list) mDiagramFiles.add((AVFile) avObject.get(MainFragment.LeanCloudConstant.DIAGRAM_OBJECT_KEY_FILE));
                }

                mHandler.sendEmptyMessage(DOWNLOAD_DIAGRAMS_DONE);

            }
        });

    }



    /**
     * A handler to handle when fetching all diagrams done
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == DOWNLOAD_DIAGRAMS_DONE) {
                mProgressDialog.dismiss();
                passDataToView();
            }
        }

    };

    /**
     * Pass the diagram's information to grid view
     */
    private void passDataToView() {

        if (mDiagramFiles == null) {
            Toast.makeText(getActivity(), R.string.toast_no_saved_diagram, Toast.LENGTH_SHORT).show();
        } else {
            DiagramsAdapter mDiagramsAdapter = new DiagramsAdapter(getActivity(), mDiagramFiles);
            mGridView.setAdapter(mDiagramsAdapter);
            mUserNameTextView.setText(AVUser.getCurrentUser().getUsername());
            String countText = mDiagramFiles.size() + " " + getString(R.string.saved_diagrams_count_total);
            mCountTextView.setText(countText);
        }

    }


    /**
     * Display selected diagram in diagram collections
     */
    private void setGridViewClickEvent() {

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String diagramUrl = mDiagramFiles.get(position).getUrl();

                DiagramDetailFragment diagramDetailFragment = new DiagramDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_DIAGRAM_URL, diagramUrl);
                diagramDetailFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, diagramDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }









}


