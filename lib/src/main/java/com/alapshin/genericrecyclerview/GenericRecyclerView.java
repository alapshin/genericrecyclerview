package com.alapshin.genericrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Custom RecyclerView
 *
 * @author alapshin
 * @since 2015-05-16
 */
public class GenericRecyclerView extends RecyclerView {
    public GenericRecyclerView(Context context) {
        this(context, null);
    }

    public GenericRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenericRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
