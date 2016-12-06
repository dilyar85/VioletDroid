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
 * Adapter class for RecyclerView
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String LOG_TAG = "RecyclerAdapter";

    private Context mContext;
    private String[] elementDescription;
    private int[] elementImageIds;

    private View selectedView;
    private long lastClickedTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 500;//milliseconds
    private static ElementViewListener mElementViewListener;

    /**
     * A listener interface to detect if view is double tapped in RecyclerView
     */
    public interface ElementViewListener {
        /**
         * Method to notify the view is double tapped
         *
         * @param view the double tapped view
         */
        void elementToolDoubleTapped(View view);
    }



    /**
     * A helper method to set ElementViewListener
     *
     * @param elementViewListener could be any class who implements this interface
     */
    public static void setElementViewListener(ElementViewListener elementViewListener) {

        mElementViewListener = elementViewListener;
    }



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

        elementDescription = new String[]{context.getString(R.string.class_rectangle),
                context.getString(R.string.dependency_line),
                context.getString(R.string.aggregation_line),
                context.getString(R.string.inheritance_line),
                context.getString(R.string.dash_bar),
                context.getString(R.string.method_line),
                context.getString(R.string.vertical_rectangle),
                context.getString(R.string.self_call_sequence)};

        elementImageIds = new int[]{R.drawable.rectangle_old, R.drawable.dependency,
                R.drawable.aggregation, R.drawable.inheritance, R.drawable.dashbar, R.drawable.sequence_line,
                R.drawable.verticalrectangle, R.drawable.sequenc_rectangle_call};
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_tool_element, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.imageView.setImageResource(elementImageIds[position]);
        holder.textView.setText(elementDescription[position]);
        holder.imageView.setTag(R.id.view_resource_key, elementImageIds[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addViewToCanvas(v);
                addBorder(v);

            }
        });

    }



    /**
     * Add view to canvas if it is double tapped
     *
     * @param v tapped view
     */
    private void addViewToCanvas(View v) {

        if (selectedView == v &&
                System.currentTimeMillis() - lastClickedTime <= DOUBLE_CLICK_TIME_DELTA) {

            mElementViewListener.elementToolDoubleTapped(v);
            lastClickedTime = 0;
        }

    }



    /**
     * Add border or remove it on the element image view
     *
     * @param v selected image view
     */
    private void addBorder(View v) {

        if (selectedView != null) selectedView.setBackgroundResource(0);
        v.setBackgroundResource(R.drawable.custom_border);
        selectedView = v;
        lastClickedTime = System.currentTimeMillis();

    }



    @Override
    public int getItemCount() {

        return elementImageIds.length;
    }

}