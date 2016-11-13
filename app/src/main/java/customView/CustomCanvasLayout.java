package customView;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.dilyar85.violetdroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom canvas layout who can have diagram elements inside
 */

public class CustomCanvasLayout extends ViewGroup {

    final static String LOG_TAG = CustomCanvasLayout.class.getSimpleName();

    List<ImageView> imageViews;

    int deviceWidth;

    View selectedChild;

    private Context mContext;


    private float lastX, lastY;




    /**
     * Init CustomCanvasLayout by getting the deviceWidth
     *
     * @param context context
     */
    private void init(Context context) {

        mContext = context;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        deviceWidth = displaymetrics.widthPixels;
        imageViews = new ArrayList<>();

    }



    /**
     * Construct a CustomCanvasLayout by given context
     *
     * @param context context
     */

    public CustomCanvasLayout(Context context) {

        super(context);
        init(context);

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
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int layoutWidth = this.getMeasuredWidth() - this.getPaddingRight() - this.getPaddingLeft();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childTop = this.getPaddingTop();
        final int layoutHeight = childBottom - childTop;

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            curWidth = deviceWidth / 5;
            curHeight = curWidth;

//            int[] sizes = getViewSizes(child);

            if (child.getVisibility() == GONE)
                return;

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

//    private int[] getViewSizes(View view) {
//
//
////        //Get the maximum size of the child
////        view.measure(MeasureSpec.makeMeasureSpec(layoutWidth, MeasureSpec.AT_MOST),
////                MeasureSpec.makeMeasureSpec(layoutHeight, MeasureSpec.AT_MOST));
//        int[] sizes = new int[2];
//        int width = deviceWidth / 4;
//
//        if (view.getTag() == R.drawable.rectangle) {
//            sizes[1] =
//        }
//        Log.e(LOG_TAG, "curWidth: " + curWidth);
//        curHeight = 100;
//
//
//    }

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



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                selectChild(eventX, eventY);
                break;

            case MotionEvent.ACTION_MOVE:
                if (selectedChild != null) moveChild(eventX, eventY);
                break;

            case MotionEvent.ACTION_UP:
                if (selectedChild != null) selectedChild.setBackgroundResource(0);
                break;

        }

        return true;

    }



    /**
     * Helper method to set selectedChild based on eventX and eventY
     * @param eventX eventX
     * @param eventY eventY
     */
    private void selectChild(float eventX, float eventY) {

        for (int i = getChildCount() - 1; i >= 0; i--) {

            View child = getChildAt(i);
            if (eventX > child.getX() && eventX < child.getX() + child.getWidth()
                    && eventY > child.getY() && eventY < child.getY() + child.getHeight()) {

                child.setBackgroundResource(R.drawable.custom_border);
                lastX = eventX;
                lastY = eventY;
                selectedChild = child;
                return;
            }
        }
        selectedChild = null;
    }



    /**
     * move child view when the touch event move happened
     * @param eventX eventX
     * @param eventY eventY
     */
    private void moveChild(float eventX, float eventY) {

        float distanceX = eventX - lastX;
        float distanceY = eventY - lastY;

        float[] validDistance = checkMoveDistance(distanceX, distanceY);
        selectedChild.setX(validDistance[0]);
        selectedChild.setY(validDistance[1]);

        lastX = eventX;
        lastY = eventY;
    }

    /**
     * Helper method to check if moving distance exceeds the bound of canvas
     * @param distanceX moved distance in x direction
     * @param distanceY moved distance in y direction
     * @return the valid x and y of selected view
     */
    private float[] checkMoveDistance(float distanceX, float distanceY) {

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

}