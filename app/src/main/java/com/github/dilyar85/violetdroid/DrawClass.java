package com.github.dilyar85.violetdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by LanceB on 10/29/16.
 */

public class DrawClass extends View {
//    private ArrayList<Rect> rects;
    private Path rect;

    public DrawClass(Context context, AttributeSet attrs) {
        super(context, attrs);
//        rects = new ArrayList<>();
    }



    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
//        for (Rect r : rects)
//        canvas.drawRect
       canvas.drawLine(0, 0, 100, 100, paint);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        int x = Math.round(event.getX());
//        int y = Math.round(event.getY());
//
//        int action = event.getActionMasked();
//        if (action == MotionEvent.ACTION_DOWN) {
//            current = find(x, y);
//            if (current == null) {
//                current = new Rect(x, y, x + 20, y + 20);
//                rects.add(current);
//            }
//        } else if (action == MotionEvent.ACTION_MOVE) {
//            current.set(x - 10, y - 10, x + 10, y + 10);
//        }
//        postInvalidate();
//        return true;
//    }


//
//    private Rect find(int x, int y) {
//
//        for (Rect r : rects)
//            if (r.contains(x, y)) return r;
//        return null;
//    }
}
