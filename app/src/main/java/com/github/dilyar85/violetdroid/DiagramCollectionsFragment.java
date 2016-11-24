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
 * A fragment to display all saved diagrams of user in backend service
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

        //TODO: Need to handle permission request for API >= 23

        initData();
        setGridViewClickEvent();
        return rootView;
    }



    private void initData() {

        mProgressDialog = ProgressDialog.show(getActivity(), null, "Saving");

        AVQuery<AVObject> query = new AVQuery<>("Diagrams");
        query.whereEqualTo("user", AVUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<AVObject>() {

            @Override
            public void done(List<AVObject> list, AVException e) {

                if (list != null) {
                    mDiagramFiles = new ArrayList<>();
                    for (AVObject avObject : list) mDiagramFiles.add((AVFile) avObject.get("file"));
                    //Notify handler scanning images done
                }

                mHandler.sendEmptyMessage(DOWNLOAD_DIAGRAMS_DONE);

            }
        });

    }



    private void setGridViewClickEvent() {

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String diagramUrl = mDiagramFiles.get(position).getUrl();

                //Store the selected image byte
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



    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == DOWNLOAD_DIAGRAMS_DONE) {
                mProgressDialog.dismiss();
                passDataToView();
            }
        }

    };



    private void passDataToView() {

        if (mDiagramFiles == null) {
            Toast.makeText(getActivity(), "There is no any saved diagram", Toast.LENGTH_SHORT).show();
        } else {

            DiagramsAdapter mDiagramsAdapter = new DiagramsAdapter(getActivity(), mDiagramFiles);
            mGridView.setAdapter(mDiagramsAdapter);

            mUserNameTextView.setText(AVUser.getCurrentUser().getUsername());
            mCountTextView.setText(mDiagramFiles.size() + "total");

        }

    }

}


