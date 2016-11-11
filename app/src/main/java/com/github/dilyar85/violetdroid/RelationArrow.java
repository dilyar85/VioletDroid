package com.github.dilyar85.violetdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.view.View;

/**
 * Created by joyyan on 11/10/16.
 */

public class RelationArrow extends UMLComponent {


    float x1 = 0;
    float y1 = 0;

    float x2 = 0;
    float y2 = 0;

    public RelationArrow(Context context, float x1, float y1, float x2, float y2) {
        super(context);


        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;
    }

    public RelationArrow(Context context, View startView, View endView) {
        super(context);


        int[] location = new int[2];
        startView.getLocationInWindow(location);

        x1 = location[0];
        y1 = location[1] + startView.getHeight() / 2;

        endView.getLocationInWindow(location);

        x2 = location[0] + endView.getWidth() / 2;
        y2 = location[1]  - 53;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(context.getResources().getColor(R.color.colorBoxed));
        p.setStrokeWidth(3.0f);
        p.setAntiAlias(true);

        PathEffect effects = new DashPathEffect(new float[]{5, 10}, 1);
        p.setPathEffect(effects);

        p.setStyle(Paint.Style.STROKE);
        Path path2 = new Path();
        path2.moveTo(x1, y1);

//        float quaX = x1 / 4;
//        float quaY = (y1 + y2) / 2;
//        if (y2 - y1 < 0) {
//            quaX = (x1 + x2) / 2;
//            quaY = y2 - 100;
//        }else if (y2 - y1 < 50){
//            quaX = (x1 + x2) / 2;
//            quaY = y1 - 50;
//        }
//        path2.quadTo(quaX, quaY, x2, y2);
        path2.lineTo(x2, y2);
        canvas.drawPath(path2, p);

        float length = 32;
        float x = x2 - length / 2;
        p.setPathEffect(null);
        p.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(x, y2);
        path.lineTo(x + length, y2);
        path.lineTo((x + x + length) / 2, y2 + 23);
        path.close();

        canvas.drawPath(path, p);
    }




}