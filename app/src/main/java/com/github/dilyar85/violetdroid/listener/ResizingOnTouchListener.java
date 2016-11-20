package com.github.dilyar85.violetdroid.listener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

/**
 * OnTouchListener for resizing button
 */

public class ResizingOnTouchListener implements View.OnTouchListener {

    private float lastX, lastY;
    private View centerView;
    private ViewGroup.LayoutParams params;



    /**
     * Construct the listener by given center view
     * @param centerView centerView to be resized
     */
    public ResizingOnTouchListener(View centerView) {

        this.centerView = centerView;
        this.params = centerView.getLayoutParams();
    }



    @Override
    public boolean onTouch(View view, MotionEvent event) {

        //We have two indicator buttons, therefore the size of center view cannot be smaller
        //than button's size * 2.
        int minSize = 2 * Math.max(view.getHeight(), view.getWidth());

        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case ACTION_DOWN:
                lastX = eventX;
                lastY = eventY;
                break;

            case ACTION_MOVE:
                //Resize button is now on left bottom corner
                float movedDistance = Math.max((lastX - eventX), eventY - lastY);

                int newWidth = params.width += movedDistance;
                int newHeight = params.height += movedDistance;

                params.width = newWidth > minSize ? newWidth : minSize;
                params.height = newHeight > minSize ? newHeight : minSize;

                centerView.requestLayout();

                lastX = eventX;
                lastY = eventY;

                break;

            default:
                break;

        }
        return true;
    }


}
