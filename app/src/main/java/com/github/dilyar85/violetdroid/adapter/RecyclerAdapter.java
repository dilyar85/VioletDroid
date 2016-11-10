package com.github.dilyar85.violetdroid.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dilyar85.violetdroid.ClassBoxView;
import com.github.dilyar85.violetdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dilyar on 11/1/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String LOG_TAG = "RecyclerAdapter";

    private Context mContext;
    private String[] elementDescription;
    private int[] elementImageIds;
    private int elementSize = 6;

    private final static int rectangle = 0;
    private final static int dependency = 1;
    private final static int aggregation = 2;
    private final static int inheritance = 3;
    private final static int active_period = 4;
    private final static int method_line = 5;

    /**
     * An inner ViewHolder class for adapter
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.element_imageview)
        ImageView imageView;
        @BindView(R.id.element_desc_textview)
        TextView textView;


        /**
         * Construct a ViewHolder
         *
         * @param itemView the given itemview
         */
        ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    /**
     * Construct a RecyclerAdapter
     *
     * @param context context for this adapter
     */
    public RecyclerAdapter(Context context) {

        mContext = context;

        elementDescription = new String[]{mContext.getString(R.string.class_rectangle),
                mContext.getString(R.string.dependency_line),
                mContext.getString(R.string.aggregation_line),
                mContext.getString(R.string.inheritance_line),
                mContext.getString(R.string.sequence_rectangle),
                mContext.getString(R.string.sequence_line)};
        elementImageIds = new int[]{R.drawable.rectangle, R.drawable.dependency,
                R.drawable.aggregation, R.drawable.inheritance, R.drawable.sequenc_rectangle_call, R.drawable.sequence_line};
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_element, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.imageView.setImageResource(elementImageIds[position]);
        holder.textView.setText(elementDescription[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case rectangle:
                        Log.d(LOG_TAG, "Clicked -- " + elementDescription[0] );
                        Toast.makeText( mContext, "Wow!", Toast.LENGTH_LONG).show();
                        addBoxComponent();
                        break;
                }

            }
        });
    }


    private void addBoxComponent() {

        RelativeLayout mRlayout = (RelativeLayout) ((AppCompatActivity) mContext).
                findViewById(R.id.canvas_layout);

        RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        ClassBoxView newBox = new ClassBoxView(mContext);
        newBox.setLayoutParams(mRparams);
        newBox.setGravity(Gravity.CENTER);
       // newBox.setEditable();
        newBox.setCursorVisible(true);
        newBox.setBackgroundResource(R.drawable.box_bg);
        mRlayout.addView(newBox);
    }

    @Override
    public int getItemCount() {

        return elementSize;
    }






}