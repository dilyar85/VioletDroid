package com.github.dilyar85.violetdroid.adapter.helperAdapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
/**
 * Created by weihang on 11/6/16.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

