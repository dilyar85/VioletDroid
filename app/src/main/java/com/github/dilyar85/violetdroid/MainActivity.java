package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.github.dilyar85.violetdroid.adapter.RecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MainActivity class
 */

public class MainActivity extends AppCompatActivity {


    final static String LOG_TAG = MainActivity.class.getSimpleName();

//    @BindView(R.id.pager)
//    ViewPager mViewPager;
    @BindView(R.id.element_recycler_view)
    RecyclerView mRecyclerView;

//    ViewPagerAdapter mPagerAdapter;
    RecyclerAdapter mRecyclerAdapter;

    private Button mButton;



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







}
