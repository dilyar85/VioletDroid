package com.github.dilyar85.violetdroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by joyyan on 11/2/16.
 */

public class BoxView extends EditText {
    private int defaultSize;

    public BoxView(Context context) {
        super(context);
    }

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //cancelEditable();

        TypedArray arr =  context.obtainStyledAttributes(attrs, R.styleable.BoxView);
        defaultSize = arr.getDimensionPixelSize(R.styleable.BoxView_default_size, 100);
        arr.recycle();
    }

    private int getBoxSize(int defaultSize, int measureSpec) {
        int boxSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch(mode) {
            case MeasureSpec.UNSPECIFIED:
                boxSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                boxSize = size;
                break;
            case MeasureSpec.EXACTLY:
                boxSize = size;
                break;
        }
        return boxSize;
    }

    public void setEditable() {
        this.setFocusable(true);
        this.requestFocus();
        this.setClickable(true);

    }

    public void cancelEditable() {
        this.setFocusable(false);
        this.setClickable(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMearureSpec) {
        super.onMeasure(widthMeasureSpec, heightMearureSpec);
        int width = getBoxSize(200, widthMeasureSpec);
        int height = getBoxSize(150, heightMearureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
