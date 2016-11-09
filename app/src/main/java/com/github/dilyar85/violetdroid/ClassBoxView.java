package com.github.dilyar85.violetdroid;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by joyyan on 11/9/16.
 */

public class ClassBoxView extends EditText implements View.OnTouchListener {
    private int defaultSize;
    private Context mContext;
    private int screenX, screenY;

    public ClassBoxView(Context context) {
        super(context);
        mContext = context;
        setOnTouchListener(this);
    }

    public ClassBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnTouchListener(this);
        //cancelEditable();

        TypedArray arr =  context.obtainStyledAttributes(attrs, R.styleable.BoxView);
        defaultSize = arr.getDimensionPixelSize(R.styleable.BoxView_default_size, 100);
        arr.recycle();
    }

    private int getBoxSize(int defaultSize, int measureSpec) {
        int boxSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch(mode) {
            case MeasureSpec.UNSPECIFIED:
                boxSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                boxSize = size;
                break;
            case MeasureSpec.EXACTLY:
                boxSize = size;
                break;
        }
        return boxSize;
    }

    public void setEditable() {
        this.requestFocus();
        this.setFocusable(true);
        this.setClickable(true);
        this.setFocusableInTouchMode(true);
    }

    public void cancelEditable() {
        this.setFocusable(false);
        this.setClickable(false);
        this.setFocusableInTouchMode(false);
        this.clearFocus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMearureSpec) {
        super.onMeasure(widthMeasureSpec, heightMearureSpec);
        int width = getBoxSize(200, widthMeasureSpec);
        int height = getBoxSize(150, heightMearureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    /* hide soft keyboard */
    public static void hideSoftInput(Activity mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /* show soft keyboard*/
    public static void showSoftInput(Activity mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.showSoftInput(view, 0);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {


        if (!(view instanceof ClassBoxView)) return false;
        ClassBoxView bView = (ClassBoxView) view;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {
                showSoftInput((AppCompatActivity) mContext, this);
                bView.setEditable();

            }
            case MotionEvent.ACTION_MOVE: {
                bView.cancelEditable();
                hideSoftInput((AppCompatActivity) mContext, this);

                int lx = (int) event.getRawX();
                int ly = (int) event.getRawY();

                params.topMargin = (int) event.getRawY() - bView.getHeight()/2;
                params.leftMargin = (int) event.getRawX() - (bView.getWidth())/2;
                params.bottomMargin = (int) event.getRawY() + bView.getHeight()/2;
                params.rightMargin = (int) event.getRawX() + (bView.getWidth())/2;

                bView.setLayoutParams(params);
                break;
            }
//            case MotionEvent.ACTION_UP:
//                setEditable();
//                break;

        }

        return true;
    }



}

