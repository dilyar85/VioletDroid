package customView;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dilyar on 11/12/16.
 */

public class BoxClassView extends EditText implements GestureDetector.OnGestureListener {

    private GestureDetector mGestureDetector;

    private void init(Context context) {

        mGestureDetector = new GestureDetector(context, this);

    }

    public BoxClassView(Context context) {

        super(context);
        init(context);
    }







    public BoxClassView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }






    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }



    @Override
    public void onShowPress(MotionEvent e) {

    }



    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        return false;
    }



    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }



    @Override
    public void onLongPress(MotionEvent e) {

        Log.e("BoxClassView", "longPres.....");
        Toast.makeText(getContext(), "longPress....", Toast.LENGTH_SHORT).show();

        ((ViewGroup) getParent()).removeView(this);
    }



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }
}
