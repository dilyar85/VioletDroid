package com.github.dilyar85.violetdroid;

import android.content.ClipData;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.app.Activity;


/**
 * MainActivity class
 */

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    final static String LOG_TAG = MainActivity.class.getSimpleName();

//    @BindView(R.id.pager)
//    ViewPager mViewPager;
    @BindView(R.id.element_recycler_view)
    RecyclerView mRecyclerView;

//    ViewPagerAdapter mPagerAdapter;
    RecyclerAdapter mRecyclerAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    /**
     * Init views
     */
    private void initView() {

        CanvasFragment fragment = new CanvasFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mRecyclerAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }






    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

}
