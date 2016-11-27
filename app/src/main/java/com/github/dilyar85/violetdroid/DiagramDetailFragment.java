package com.github.dilyar85.violetdroid;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.dilyar85.imageloader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment class to display selected diagram's detail
 */
public class DiagramDetailFragment extends Fragment {

    @BindView(R.id.selected_imageView)
    ImageView mImageView;



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
//        if (id == R.id.menu_share) shareDiagramFromPNG();
        return true;
    }


//
//    /**
//     * Share the current diagram with any appropriate applications in the device
//     */
//    private void shareDiagramFromPNG() {
//
//        String diagramPath = getCurrentDiagramAsPicture(true);
//        if (diagramPath == null) return;
//        //Create a sharing intent and start it.
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(diagramPath)));
//        intent.setType("image/*");
//        PackageManager pm = getActivity().getPackageManager();
//
//        if (intent.resolveActivity(pm) != null) startActivity(intent);
//        else
//            Toast.makeText(getActivity(), getString(R.string.toast_no_intent_applications), Toast.LENGTH_LONG).show();
//
//    }



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
        String diagramUrl = bundle.getString(DiagramCollectionsFragment.KEY_DIAGRAM_URL);
        if (diagramUrl != null) ImageLoader.getInstance().loadImageWithPath(diagramUrl, mImageView);

    }

}
