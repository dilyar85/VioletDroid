package customView;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom canvas layout who can have diagram elements inside
 */

public class CustomCanvasLayout extends ViewGroup {

    final static String LOG_TAG = CustomCanvasLayout.class.getSimpleName();

    List<ImageView> imageViews;

    int deviceWidth;



    /**
     * Init CustomCanvasLayout by getting the deviceWidth
     *
     * @param context context
     */
    private void init(Context context) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        deviceWidth = displaymetrics.widthPixels;

    }



    /**
     * Construct a CustomCanvasLayout by given context
     *
     * @param context context
     */

    public CustomCanvasLayout(Context context) {

        super(context);
        init(context);
        imageViews = new ArrayList<>();
    }



    /**
     * Construct a CustomCanvasLayout by given context and attribute set
     *
     * @param context context
     * @param attrs   attrs set in xml
     */
    public CustomCanvasLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(context);

    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();

        final int childWidth = this.getMeasuredWidth() - this.getPaddingRight() - this.getPaddingLeft();

        final int childHeight = childBottom - childTop;
        Log.e(LOG_TAG, "childWidth: " + this.getMeasuredWidth());

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = deviceWidth / 5;
            Log.e(LOG_TAG, "curWidth: " + curWidth);
            curHeight = 100;
            //wrap is reach to the end
            if (curLeft + curWidth >= childRight) {
                curLeft = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //do the layout
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            //store the max height
            if (maxHeight < curHeight)
                maxHeight = curHeight;
            curLeft += curWidth;
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int count = getChildCount();
//        // Measurement will ultimately be computing these values.
//        int maxHeight = 0;
//        int maxWidth = 0;
//        int childState = 0;
//        int mLeftWidth = 0;
//        int rowCount = 0;
//
//        // Iterate through all children, measuring them and computing our dimensions
//        // from their size.
//        for (int i = 0; i < count; i++) {
//            final View child = getChildAt(i);
//
//            if (child.getVisibility() == GONE)
//                continue;
//
//            // Measure the child.
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
//            mLeftWidth += child.getMeasuredWidth();
//
//            if ((mLeftWidth / deviceWidth) > rowCount) {
//                maxHeight += child.getMeasuredHeight();
//                rowCount++;
//            } else {
//                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
//            }
//            childState = combineMeasuredStates(childState, child.getMeasuredState());
//        }
//
//        // Check against our minimum height and width
//        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
//        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
//
//        // Report our final dimensions.
//        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
//                resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//
//        Log.e(LOG_TAG, "x: " + event.getX());
//        Log.e(LOG_TAG, "y: " + event.getY());
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                setX(event.getX());
//                setY(event.getY());
//                break;
//
//            case MotionEvent.ACTION_UP:
////                params.topMargin = (int) event.getRawY() - getHeight();
////                params.leftMargin = (int) event.getRawX() - (getWidth() / 2);
////                setLayoutParams(params);
//                break;
//
//            case MotionEvent.ACTION_DOWN:
////                setLayoutParams(params);
//                break;
//        }
//
//        return true;
//
//    }
}
