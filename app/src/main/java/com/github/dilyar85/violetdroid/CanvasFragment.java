package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import customView.CustomCanvasLayout;

/**
 * A canvas fragment to allow user draw diagrams
 */

public class CanvasFragment extends Fragment implements RecyclerAdapter.ElementViewListener {

    @BindView(R.id.canvas_layout)
    CustomCanvasLayout mCustomCanvasLayout;

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
    public void viewAdded(View view) {

        int tag = (int) view.getTag(R.id.view_resource_key);
//        if (tag == R.drawable.rectangle) {
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            BoxClassView boxClassView = new BoxClassView(getActivity());
//            boxClassView.setLayoutParams(params);
//
//            boxClassView.setBackgroundResource(tag);
//            boxClassView.setTag(R.id.view_resource_key, tag);
//            mCustomCanvasLayout.addView(boxClassView);
//            return;
//
//
//        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(params);

        imageView.setImageResource(tag);
        imageView.setTag(R.id.view_resource_key, tag);

        mCustomCanvasLayout.addView(imageView);

    }
}