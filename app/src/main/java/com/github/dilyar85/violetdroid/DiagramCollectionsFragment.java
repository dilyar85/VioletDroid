package com.github.dilyar85.violetdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.github.dilyar85.violetdroid.adapter.DiagramCollectionsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment class to display all saved diagrams of user in backend service
 */
public class DiagramCollectionsFragment extends Fragment {

    static final String LOG_TAG = DiagramCollectionsFragment.class.getSimpleName();

    public static final String KEY_DIAGRAM_URL = "key_selected_diagram_url";

    private List<AVFile> mDiagramFiles;

    @BindView(R.id.diagram_collections_gridView)
    GridView mGridView;
    @BindView(R.id.diagram_count_textView)
    TextView mCountTextView;
    @BindView(R.id.user_name_textview)
    TextView mUserNameTextView;
    @BindView(R.id.empty_view_in_gridview)
    TextView mEmptyTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_diagram_collections, container, false);
        ButterKnife.bind(this, rootView);

        mGridView.setEmptyView(mEmptyTextView);
        return rootView;
    }



    @Override
    public void onStart() {

        super.onStart();
        fetchDiagramsFromLeanCloud();
    }





    /**
     * Try to fetch diagrams saved by the current user
     */
    private void fetchDiagramsFromLeanCloud() {

        AVQuery<AVObject> query = new AVQuery<>(MainFragment.LeanCloudConstant.CLASS_DIAGRAM);
        query.whereEqualTo(MainFragment.LeanCloudConstant.DIAGRAM_OBJECT_KEY_USERNAME, AVUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<AVObject>() {

            @Override
            public void done(List<AVObject> list, AVException e) {

                if (list != null) {
                    mDiagramFiles = new ArrayList<>();
                    for (AVObject avObject : list)
                        mDiagramFiles.add((AVFile) avObject.get(MainFragment.LeanCloudConstant.DIAGRAM_OBJECT_KEY_FILE));
                    passDataToView();
                    setGridViewClickEvent();
                } else {
                    mEmptyTextView.setText(getString(R.string.search_diagram_failed));
                    mGridView.setEmptyView(mEmptyTextView);
                }

            }
        });

    }


    /**
     * Pass the diagram's information to grid view
     */
    private void passDataToView() {

        DiagramCollectionsAdapter mDiagramCollectionsAdapter = new DiagramCollectionsAdapter(getActivity(), mDiagramFiles);
        mGridView.setAdapter(mDiagramCollectionsAdapter);
        mUserNameTextView.setText(AVUser.getCurrentUser().getUsername());
        String countText = mDiagramFiles.size() + " " + getString(R.string.saved_diagrams_count_total);
        mCountTextView.setText(countText);

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


