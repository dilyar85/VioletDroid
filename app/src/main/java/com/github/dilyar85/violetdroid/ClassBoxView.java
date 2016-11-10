package com.github.dilyar85.violetdroid;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by joyyan on 11/9/16.
 */

public class ClassBoxView extends EditText implements View.OnTouchListener {
    private Context mContext;

    private int screenWidth, screenHeight;
    private void getDisplayMetrics() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
    }

    private int lastX, lastY;

    private int downX, downY;
    private int upX, upY; //
    private int rangeDifferenceX, rangeDifferenceY;
    private int mDistance = 10;
    private int mL,mB,mR,mT;

    public ClassBoxView(Context context) {
        super(context);
        mContext = context;
        getDisplayMetrics();
        setOnTouchListener(this);
    }

    public ClassBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getDisplayMetrics();
        setOnTouchListener(this);
    }

    public ClassBoxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        getDisplayMetrics();
        setOnTouchListener(this);
    }

    public static void hideSoftInput(Activity mContext, View view) {
        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showSoftInput(Activity mContext, View view) {
        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.showSoftInput(view, 0);
        }
    }

    public interface IOnKeyboardStateChangedListener{
        public void openKeyboard();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                mL = v.getLeft() + dx;
                mB = v.getBottom() + dy;
                mR = v.getRight() + dx;
                mT = v.getTop() + dy;

                if (mL < 0) {
                    mL = 0;
                    mR = mL + v.getWidth();
                }

                if (mT < 0) {
                    mT = 0;
                    mB = mT + v.getHeight();
                }

                if (mR > screenWidth) {
                    mR = screenWidth;
                    mL = mR - v.getWidth();
                }

                if (mB > screenHeight) {
                    mB = screenHeight;
                    mT = mB - v.getHeight();
                }
                v.layout(mL, mT, mR, mB);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                v.postInvalidate();

                v.setFocusable(false);
                v.setFocusableInTouchMode(false);
                hideSoftInput((Activity)mContext, v);
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) event.getRawX();
                upY = (int) event.getRawY();

                rangeDifferenceX = upX - downX;
                rangeDifferenceY = upY - downY;
                if (rangeDifferenceX > 0 && rangeDifferenceX <= mDistance) {
                    if (rangeDifferenceY >= 0 && rangeDifferenceY <= mDistance) {
                        v.setFocusable(true);
                        v.setFocusableInTouchMode(true);
                    } else {
                        if (rangeDifferenceY <= 0 && rangeDifferenceY >= -mDistance) {
                            v.setFocusable(true);
                            v.setFocusableInTouchMode(true);


                        } else {
                            v.setFocusable(false);
                            v.setFocusableInTouchMode(false);

                        }
                    }
                } else {
                    if (rangeDifferenceX <= 0 && rangeDifferenceX >= -mDistance) {
                        v.setFocusable(true);
                        v.setFocusableInTouchMode(true);


                    } else {
                        v.setFocusable(false);
                        v.setFocusableInTouchMode(false);

                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

}
