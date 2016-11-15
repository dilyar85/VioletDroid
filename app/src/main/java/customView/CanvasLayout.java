package customView;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.dilyar85.violetdroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom canvas layout who can have diagram elements inside
 */

public class CanvasLayout extends RelativeLayout {

    final static String LOG_TAG = CanvasLayout.class.getSimpleName();

    List<ImageView> imageViews;

    int deviceWidth;

    View selectedChild;


    private GestureDetector mGestureDetector;



    /**
     * Init CustomCanvasLayout by getting the deviceWidth
     *
     * @param context context
     */
    private void init(Context context) {

        mGestureDetector = new GestureDetector(getContext(), new GestureTap());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        deviceWidth = displaymetrics.widthPixels;
        imageViews = new ArrayList<>();
        this.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if (selectedChild != null) {
                    removeView(selectedChild);
                    selectedChild = null;
                }
                return true;
            }
        });
    }



    /**
     * Construct a CustomCanvasLayout by given context
     *
     * @param context context
     */

    public CanvasLayout(Context context) {

        super(context);
        init(context);

    }



    /**
     * Construct a CustomCanvasLayout by given context and attribute set
     *
     * @param context context
     * @param attrs   attrs set in xml
     */
    public CanvasLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(context);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);

        return true;

    }



    /**
     * A listener class to detect user's gesture and implement changes on canvas
     */
    class GestureTap implements GestureDetector.OnGestureListener {

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
            return false;
        }



        @Override
        public void onLongPress(MotionEvent e) {

            removeView(null);

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
     * @param show boolean value to imply whether to add indicator
     */
    private void showAdjustIndicator(boolean show) {

        //Add border
        View centerView = selectedChild.findViewById(R.id.image);
        centerView.setBackgroundResource(show ? R.drawable.custom_border : 0);
        //Add indicator
        View indicatorView = selectedChild.findViewById(R.id.indicator);
        indicatorView.setVisibility(show ? VISIBLE : INVISIBLE);

    }

}
