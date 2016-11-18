package com.github.dilyar85.violetdroid.customView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.animation.RotateAnimation;

import com.github.dilyar85.violetdroid.R;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

/**
 * A custom canvas layout who can have diagram elements inside
 */

public class CanvasLayout extends RelativeLayout {

    final static String LOG_TAG = CanvasLayout.class.getSimpleName();

    private View selectedChild;

    private GestureDetector mGestureDetector;



    /**
     * Init CustomCanvasLayout
     */
    private void init() {

        mGestureDetector = new GestureDetector(getContext(), new GestureTap());

    }



    /**
     * Construct a CustomCanvasLayout by given context
     *
     * @param context context
     */

    public CanvasLayout(Context context) {

        super(context);
        init();

    }



    /**
     * Construct a CustomCanvasLayout by given context and attribute set
     *
     * @param context context
     * @param attrs   attrs set in xml
     */
    public CanvasLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);
        return true;

    }



    /**
     * An inner class to detect user's gesture and implement changes on canvas
     */
    private class GestureTap extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            //TODO: Make rectangle diagram editable
            return super.onDoubleTap(e);
        }



        @Override
        public boolean onDown(MotionEvent e) {

            selectChild(e.getX(), e.getY());
            return false;
        }



        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (selectedChild != null) {
                float[] validLocation = getValidLocations(-distanceX, -distanceY);
                selectedChild.setX(validLocation[0]);
                selectedChild.setY(validLocation[1]);
            }

            return false;
        }



        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            if (selectedChild != null) showAdjustIndicator(true);
            return true;
        }



        @Override
        public void onLongPress(MotionEvent e) {

            if (selectedChild != null) removeView(selectedChild);

        }



        @Override
        public void onShowPress(MotionEvent e) {

        }



        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return false;
        }
    }



    /**
     * Helper method to set selectedChild based on eventX and eventY
     *
     * @param eventX eventX
     * @param eventY eventY
     */
    private void selectChild(float eventX, float eventY) {

        if (selectedChild != null) showAdjustIndicator(false);

        for (int i = getChildCount() - 1; i >= 0; i--) {

            View child = getChildAt(i);
            if (eventX > child.getX() && eventX < child.getX() + child.getWidth()
                    && eventY > child.getY() && eventY < child.getY() + child.getHeight()) {
                selectedChild = child;
                return;
            }
        }

        selectedChild = null;

    }



    /**
     * Helper method to check if moving distance of the view exceeds the bound of canvas layout
     *
     * @param distanceX moved distance in x direction
     * @param distanceY moved distance in y direction
     * @return the valid x and y of selected view
     */
    private float[] getValidLocations(float distanceX, float distanceY) {

        float paddingRight = getPaddingRight();
        float paddingLeft = getPaddingLeft();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();
        float canvasHeight = getHeight();
        float canvasWidth = getWidth();

        float newX = selectedChild.getX() + distanceX;
        if (newX <= paddingLeft)

            newX = paddingLeft;
        else if (newX + selectedChild.getWidth() >= canvasWidth - paddingRight)
            newX = canvasWidth - paddingRight - selectedChild.getWidth();

        float newY = selectedChild.getY() + distanceY;
        if (newY <= paddingTop)
            newY = paddingTop;
        else if (newY + selectedChild.getHeight() >= canvasHeight - paddingBottom)
            newY = canvasHeight - paddingBottom - selectedChild.getHeight();

        return new float[]{newX, newY};
    }



    /**
     * Add adjusting indicator on selectedView
     *
     * @param show boolean value to imply whether to add indicator
     */
    private void showAdjustIndicator(boolean show) {

        //Show or hide border
        ImageView centerView = (ImageView) selectedChild.findViewById(R.id.center_image_view);
        centerView.setBackgroundResource(show ? R.drawable.custom_border : 0);
        //Show or hide indicator
        View indicatorView = selectedChild.findViewById(R.id.indicator);
        indicatorView.setVisibility(show ? VISIBLE : INVISIBLE);
        //Add or remove indicator
        addIndicatorListener(show);

    }



    /**
     * Unfinished method. Need to implement rotate feature
     *
     * @param add boolean value to tell if needs to add indicator
     */
    private void addIndicatorListener(boolean add) {

        final Button resizeButton = (Button) selectedChild.findViewById(R.id.resize_button);

        resizeButton.setOnTouchListener(!add ? null : new OnTouchListener() {

            View centerView = selectedChild.findViewById(R.id.center_image_view);
            ViewGroup.LayoutParams params = centerView.getLayoutParams();

            float lastX, lastY;
            //We have two indicator buttons, therefore the size of center view cannot be smaller
            //than button's size * 2.
            int minSize = 2 * Math.max(resizeButton.getHeight(), resizeButton.getWidth());



            @Override
            public boolean onTouch(View v, MotionEvent event) {

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


                final float x = event.getX();
                final float y = event.getY();

                final float xc = selectedChild.getWidth()/2;
                final float yc = selectedChild.getHeight()/2;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewRotation = selectedChild.getRotation();
                        fingerRotation = Math.toDegrees(Math.atan2(x - xc, yc - y));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        newFingerRotation = Math.toDegrees(Math.atan2(x - xc, yc - y));
                        selectedChild.setRotation((float)(viewRotation + newFingerRotation - fingerRotation));
                        break;
                    case MotionEvent.ACTION_UP:
                        fingerRotation = newFingerRotation = 0.0f;
                        break;
                }
                return true;

            }
        });


    }


    float viewRotation;
    double fingerRotation;
    double newFingerRotation;
}
