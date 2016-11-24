package com.github.dilyar85.violetdroid.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.dilyar85.violetdroid.MyApplication;
import com.github.dilyar85.violetdroid.R;
import com.github.dilyar85.violetdroid.listener.ResizingOnTouchListener;
import com.github.dilyar85.violetdroid.listener.RotatingOnTouchListener;

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

            setEditable();
            return true;
        }

        private void setEditable() {

            final EditText editText = (EditText) selectedChild.findViewById(R.id.center_edittext);
            if (editText == null) return;

            if (editText.getVisibility()!=VISIBLE) {
                editText.setVisibility(VISIBLE);

                final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
                    public boolean onDoubleTap(MotionEvent e) {
                        editText.setFocusable(true);
                        editText.setCursorVisible(true);
                        editText.requestFocus();
                        editText.requestFocusFromTouch();
                        editText.setSelectAllOnFocus(true);
                        showKeyBoard(editText);
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {

                    }
                });

                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return gestureDetector.onTouchEvent(motionEvent);
                    }

                });

            }


        }

        private void showKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }


        private void cancelEditable() {
            EditText editText = (EditText) selectedChild.findViewById(R.id.center_edittext);
        if (editText == null) return;
            closeKeyBoard(editText);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            editText.setCursorVisible(false);
        }


        private void closeKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }


        @Override
        public boolean onDown(MotionEvent e) {

            selectChild(e.getX(), e.getY());
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (selectedChild != null) {
                cancelEditable();
                float[] validLocation = getValidLocations(-distanceX, -distanceY);
                selectedChild.setX(validLocation[0]);
                selectedChild.setY(validLocation[1]);
            }

            return false;
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            if (selectedChild != null) {
                cancelEditable();
                showAdjustIndicator(true);
            }

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
     * Add listener for resizing and rotating buttons
     *
     * @param add boolean value to tell if needs to add indicator
     */
    private void addIndicatorListener(boolean add) {

        Button resizeButton = (Button) selectedChild.findViewById(R.id.resize_button);
        resizeButton.setOnTouchListener(!add ? null :
                new ResizingOnTouchListener(selectedChild.findViewById(R.id.center_image_view)));

        Button rotateButton = (Button) selectedChild.findViewById(R.id.rotate_button);
        rotateButton.setOnTouchListener(!add ? null :
                new RotatingOnTouchListener(selectedChild));

    }

}


