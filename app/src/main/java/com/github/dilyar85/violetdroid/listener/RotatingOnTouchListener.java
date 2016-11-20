package com.github.dilyar85.violetdroid.listener;
import android.view.MotionEvent;
import android.view.View;

/**
 * OnTouchListener for rotating button
 */

public class RotatingOnTouchListener implements View.OnTouchListener {

    private View selectedChild;
    private float xc, yc;



    /**
     * Construct the listener by given selectedChild view
     * @param selectedChild view to be rotated
     */
    public RotatingOnTouchListener(View selectedChild) {

        this.selectedChild = selectedChild;

        int[] viewAbsCords = new int[2];
        selectedChild.getLocationOnScreen(viewAbsCords);
        xc = viewAbsCords[0] + selectedChild.getWidth() / 2;
        yc = viewAbsCords[1] + selectedChild.getHeight() / 2;
    }



    @Override
    public boolean onTouch(View view, MotionEvent event) {

        float eventX = event.getRawX();
        float eventY = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                //rotate button is now on left bottom corner
                float curDegrees = (float) Math.toDegrees(Math.atan2((eventY - yc), eventX - xc));
                selectedChild.setRotation((-45 + curDegrees));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;

    }
}
