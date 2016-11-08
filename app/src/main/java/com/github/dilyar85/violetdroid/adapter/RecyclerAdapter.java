package com.github.dilyar85.violetdroid.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dilyar85.violetdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dilyar on 11/1/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private String[] elementDescription;
    private int[] elementImageIds;

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
         * @param itemView the given itemview
         */
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



    /**
     * Construct a RecyclerAdapter
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
        R.drawable.aggregation,R.drawable.inheritance,R.drawable.sequenc_rectangle_call,R.drawable.sequence_line};
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_element, parent, false);



        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imageView.setImageResource(elementImageIds[position]);
        holder.textView.setText(elementDescription[position]);



    }



    @Override
    public int getItemCount() {

        return elementDescription.length;
    }




}