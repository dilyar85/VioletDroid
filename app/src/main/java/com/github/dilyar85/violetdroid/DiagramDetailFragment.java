package com.github.dilyar85.violetdroid;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.dilyar85.imageloader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dilyar on 11/23/16.
 */
public class DiagramDetailFragment extends Fragment {

    @BindView(R.id.selected_imageView)
    ImageView mImageView;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diagram_detail, container, false);
        ButterKnife.bind(this, view);

        initData();
        return view;
    }



    private void initData() {
        Bundle bundle = this.getArguments();
        String diagramUrl = bundle.getString(DiagramCollectionsFragment.KEY_DIAGRAM_URL);
        if (diagramUrl != null) ImageLoader.getInstance().loadImageWithPath(diagramUrl, mImageView);

    }

    @OnClick(R.id.confirm_button)
    public void clickButton() {
        getActivity().getFragmentManager().popBackStack();
    }

}
