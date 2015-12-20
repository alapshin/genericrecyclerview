package com.alapshin.genericrecyclerview;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ViewHolder parametrized with item type and view type
 *
 * @param <T> data item type
 * @param <V> item view type
 *
 * @author Andrei Lapshin
 */
public abstract class RecyclerViewHolder<T extends RecyclerItem, V extends View>
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    protected T item;
    protected V view;
    protected OnItemClickListener onItemClickListener;

    public RecyclerViewHolder(V itemView) {
        super(itemView);
        this.view = itemView;
    }

    /**
     * Returns item view attached to view holder
     * @return {@link android.view.View} attached to view holder
     */
    public V getItemView() {
        return view;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Binds data item to item view
     * @param item data item
     */
    @CallSuper
    public void bindItem(T item) {
        this.item = item;
    };

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
