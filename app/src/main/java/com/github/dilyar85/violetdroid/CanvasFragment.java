package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;
import com.github.dilyar85.violetdroid.customView.CanvasLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A canvas fragment from MainActivity to allow users to draw diagrams
 */

public class CanvasFragment extends Fragment implements RecyclerAdapter.ElementViewListener {

    @BindView(R.id.canvas_layout)
    CanvasLayout mCanvasLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        RecyclerAdapter.setElementViewListener(this);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return view;

    }


    @Override
    public void viewDoubleTapped(View view) {

        int tag = (int) view.getTag(R.id.view_resource_key);
        View testLayout = getActivity().getLayoutInflater().inflate(R.layout.indicator_layout, mCanvasLayout, false);
        ImageView targetView = (ImageView) testLayout.findViewById(R.id.center_image_view);
        targetView.setImageResource(tag);
        testLayout.setTag(view.getTag(R.id.view_resource_key));

        mCanvasLayout.addView(testLayout);

    }
}