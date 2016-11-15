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
    private int elementSize = 6;

    private View selectedView;
    private long lastClickedTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 500;//milliseconds
    private static ElementViewListener mElementViewListener;

    public interface ElementViewListener {
        void viewAdded(View view);
    }



    public static void setElementViewListener(ElementViewListener elementViewListener) {

        mElementViewListener = elementViewListener;
    }




    public View getSelectedView() {
        return selectedView;
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

        mContext = context;

        elementDescription = new String[]{mContext.getString(R.string.class_rectangle),
                mContext.getString(R.string.dependency_line),
                mContext.getString(R.string.aggregation_line),
                mContext.getString(R.string.inheritance_line),
                mContext.getString(R.string.sequence_rectangle),
                mContext.getString(R.string.sequence_line)};
        elementImageIds = new int[]{R.drawable.rectangle_old, R.drawable.dependency,
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
     * @param v tapped view
     */
    private void addViewToCanvas(View v) {

        if (selectedView == v && System.currentTimeMillis() - lastClickedTime <= DOUBLE_CLICK_TIME_DELTA) {

            mElementViewListener.viewAdded(v);
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

//        String tag = (String) v.getTag();
//        if (tag.equals(mContext.getString(R.string.view_not_selected_tag))) {
//            v.setBackgroundResource(R.drawable.custom_border);
//            v.setTag(mContext.getString(R.string.view_selected_tag));
//        } else {
//            v.setBackgroundResource(0);
//            v.setTag(mContext.getString(R.string.view_not_selected_tag));
//        }



    }


//    private void addBoxComponent() {
//
//        RelativeLayout mRlayout = (RelativeLayout) ((AppCompatActivity) mContext).
//                findViewById(R.id.canvas_layout);
//
//        RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//        ClassBoxView newBox = new ClassBoxView(mContext);
//        newBox.setLayoutParams(mRparams);
//        newBox.setGravity(Gravity.CENTER);
//       // newBox.setEditable();
//        newBox.setCursorVisible(true);
//        newBox.setBackgroundResource(R.drawable.rectangle);
//        mRlayout.addView(newBox);
//    }






    @Override
    public int getItemCount() {

        return elementSize;
    }

}