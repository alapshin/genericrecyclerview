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
public class DefaultViewHolder<T extends RecyclerItem, V extends View>
        extends RecyclerView.ViewHolder
        implements BindableViewHolder<T, V> {
    protected T item;
    protected V view;

    public DefaultViewHolder(V itemView) {
        super(itemView);
        this.view = itemView;
    }

    /**
     * {@inheritDoc}
     */
    public T getItem() {
        return item;
    }

    /**
     * {@inheritDoc}
     */
    public V getItemView() {
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @CallSuper
    public void onBindViewHolder(T item) {
        this.item = item;
    };
}
