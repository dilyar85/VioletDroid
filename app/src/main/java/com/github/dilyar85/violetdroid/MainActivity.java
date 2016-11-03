package com.github.dilyar85.violetdroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * MainActivity class
 */
public class MainActivity extends AppCompatActivity {

    private int boxCount;
    private BoxView box1;
    private Button boxButton;
    private ShapeDragListener shapeDragListener;
    private ArrayList<BoxView> boxes;
    private View.OnClickListener dragListner;

   // final static String LOG_TAG = MainActivity.class.getSimpleName();
//
//    @BindView(R.id.pager)
//    ViewPager mViewPager;
//    @BindView(R.id.element_recycler_view)
//    RecyclerView mRecyclerView;
//
//    ViewPagerAdapter mPagerAdapter;
//    RecyclerAdapter mRecyclerAdapter;

    final static String LOG_TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ButterKnife.bind(this);

        initView();

    }


    private void addBoxComponent() {
        RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.main_layout);
        RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        BoxView newBox = new BoxView(this);
        newBox.setLayoutParams(mRparams);
        newBox.setGravity(Gravity.CENTER);
        newBox.setEditable();
        newBox.setCursorVisible(true);
        newBox.setOnTouchListener(shapeDragListener);
        newBox.setHeight(300);
        newBox.setWidth(300);
        newBox.setBackgroundResource(R.drawable.box_bg);
        mRlayout.addView(newBox);
    }


    /**
     * Init views
     */
    private void initView() {

//        mPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
//        mViewPager.setAdapter(mPagerAdapter);
//
//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//
//
//        mRecyclerAdapter = new RecyclerAdapter(this);
//        mRecyclerView.setAdapter(mRecyclerAdapter);


        boxes = new ArrayList<BoxView>();

        shapeDragListener = new ShapeDragListener();


        Toast.makeText(this,"Hang tight, VioletDroid will become better soon!",Toast.LENGTH_LONG).show();

        //box1 = (BoxView) findViewById(R.id.box1);
        boxButton = (Button) findViewById(R.id.boxButton);

        boxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBoxComponent();
            }
        });

        //box1.setOnTouchListener(shapeDragListener);



    }


    private class ShapeDragListener implements View.OnTouchListener {


        @Override
        public boolean onTouch(View view, MotionEvent event) {


            if (!(view instanceof BoxView)) return false;
            BoxView bView = (BoxView) view;


            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN: {
                    bView.setEditable();
                    if (bView.requestFocus()) {
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                    bView.cancelEditable();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(bView.getWindowToken(), 0);
                    params.topMargin = (int) event.getRawY() - bView.getHeight();
                    params.leftMargin = (int) event.getRawX() - (bView.getWidth() / 2);
                    bView.setLayoutParams(params);
                    break;
            }

            return true;
        }
    }

}

