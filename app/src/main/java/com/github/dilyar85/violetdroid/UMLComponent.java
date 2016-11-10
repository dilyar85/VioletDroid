package com.github.dilyar85.violetdroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewManager;
import android.widget.RelativeLayout;

/**
 * Created by joyyan on 11/10/16.
 */

public abstract class UMLComponent extends View {

    protected Context context;

    public UMLComponent(Context context) {
        super(context);
        this.context = context;
        setRemovalListener();
    }
    public UMLComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setRemovalListener();
    }

    public void addToCanvas() {

        RelativeLayout mRlayout = (RelativeLayout) ((AppCompatActivity) this.context).
                findViewById(R.id.canvas_layout);

        RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(mRparams);
        mRlayout.addView(this);
    }


    private void setRemovalListener () {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((ViewManager) getParent()).removeView(UMLComponent.this);
                return true;
            }
        });
    }

}
