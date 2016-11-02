package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * MainActivity class
 */
public class MainActivity extends AppCompatActivity {

    private BoxView box1;
    private ShapeDragListener shapeDragListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shapeDragListener = new ShapeDragListener();


        Toast.makeText(this,"Hang tight, VioletDroid will become better soon!",Toast.LENGTH_LONG).show();

        box1 = (BoxView) findViewById(R.id.box1);
        box1.setGravity(Gravity.CENTER);


        box1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                //if (currentState != State.EDIT_MOVE) return false;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                if (view.getId() != R.id.box1) return false;

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        box1.setEditable();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        params.topMargin = (int) event.getRawY() - view.getHeight();
                        params.leftMargin = (int) event.getRawX() - (view.getWidth() / 2);
                        view.setLayoutParams(params);
                        break;
                }

                return true;
            }
        });

    }




}

