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
        elementDescription = new String[elementSize];
        elementImageIds = new int[elementSize];

        elementDescription[rectangle] = mContext.getString(R.string.class_rectangle);
        elementDescription[dependency] = mContext.getString(R.string.dependency_line);
        elementDescription[aggregation] = mContext.getString(R.string.aggregation_line);
        elementDescription[inheritance] = mContext.getString(R.string.inheritance_line);
        elementDescription[active_period] = mContext.getString(R.string.sequence_rectangle);
        elementDescription[method_line] = mContext.getString(R.string.sequence_line);

        elementImageIds[rectangle] = R.drawable.rectangle;
        elementImageIds[dependency] = R.drawable.dependency;
        elementImageIds[aggregation] = R.drawable.aggregation;
        elementImageIds[inheritance] = R.drawable.inheritance;
        elementImageIds[active_period] = R.drawable.association;
        elementImageIds[method_line] = R.drawable.directed_association;

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

        return elementSize;
    }

}