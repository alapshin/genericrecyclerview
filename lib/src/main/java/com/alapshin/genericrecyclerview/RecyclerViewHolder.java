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
     * @return {@see android.view.View} attached to ViewHolder
     */
    public V getItemView() {
        return view;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Subclasses must implement this method to bind data to UI
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
