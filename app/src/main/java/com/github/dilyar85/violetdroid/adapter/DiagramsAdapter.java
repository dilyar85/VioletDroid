package com.github.dilyar85.violetdroid.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.github.dilyar85.imageloader.ImageLoader;
import com.github.dilyar85.violetdroid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An Adapter class for gridview in DiagramCollectionsFragment
 */

public class DiagramsAdapter extends BaseAdapter {

    final static String LOG_TAG = DiagramsAdapter.class.getSimpleName();
    private List<AVFile> mDiagramFiles;
    private LayoutInflater mInflater;



    /**
     * Construct the adapter with given context and list of diagrams files (AVFile)
     *
     * @param context       given context
     * @param mDiagramFiles given diagram files
     */
    public DiagramsAdapter(Context context, List<AVFile> mDiagramFiles) {

        this.mDiagramFiles = mDiagramFiles;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {

        return mDiagramFiles.size();
    }



    @Override
    public Object getItem(int i) {

        return mDiagramFiles.get(i);
    }



    @Override
    public long getItemId(int i) {

        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        DiagramHolder diagramHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_saved_diagram, viewGroup, false);
            diagramHolder = new DiagramHolder(view);
            view.setTag(diagramHolder);
        } else {
            diagramHolder = (DiagramHolder) view.getTag();
        }

        //Reset status
        diagramHolder.mImageView.setImageResource(R.drawable.diagram_default);

        //Load image using ImageLoader.
        AVFile diagramFile = mDiagramFiles.get(i);

        if (diagramFile != null)
            ImageLoader.getInstance().loadImageWithUrl(diagramFile.getUrl(), diagramHolder.mImageView);

        diagramHolder.mTextView.setText(mDiagramFiles.get(i).getName());

        return view;

    }



    /**
     * Holder class for the adapter
     */
    static class DiagramHolder {

        @BindView(R.id.diagram_imageview)
        ImageView mImageView;

        @BindView(R.id.diagram_name_textview)
        TextView mTextView;



        /**
         * Construct the class with given view
         *
         * @param view the item view
         */
        DiagramHolder(View view) {

            ButterKnife.bind(this, view);
        }
    }
}
