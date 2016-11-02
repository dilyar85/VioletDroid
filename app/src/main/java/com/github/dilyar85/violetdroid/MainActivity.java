package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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

    final static String LOG_TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void createDragListener() {


    }



    private void addBoxComponent() {
        RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.main_layout);
        RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        BoxView newBox = new BoxView(this);
        newBox.setLayoutParams(mRparams);
        newBox.setGravity(Gravity.CENTER);
        newBox.setFocusable(true);
        newBox.setClickable(true);
        newBox.setOnTouchListener(shapeDragListener);
        newBox.setHeight(300);
        newBox.setWidth(300);
        newBox.setBackgroundResource(R.drawable.box_bg);
        mRlayout.addView(newBox);

    }




    private class ShapeDragListener implements View.OnTouchListener {


        @Override
        public boolean onTouch(View view, MotionEvent event) {


            if (!(view instanceof BoxView)) return false;
            BoxView bView = (BoxView) view;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:

                    bView.setEditable();
//                    InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(bView, 0);
                    break;
                case MotionEvent.ACTION_MOVE:
                    bView.cancelEditable();
                    params.topMargin = (int) event.getRawY() - bView.getHeight();
                    params.leftMargin = (int) event.getRawX() - (bView.getWidth() / 2);
                    bView.setLayoutParams(params);
                    break;
            }

            return true;
        }
    }

}

