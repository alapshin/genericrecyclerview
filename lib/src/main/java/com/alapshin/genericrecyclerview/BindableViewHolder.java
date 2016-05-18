package com.alapshin.genericrecyclerview;

import android.view.View;

/**
 * ViewHolder parametrized with item type and view type
 *
 * @param <T> item type
 * @param <V> view type
 *
 * @author Andrei Lapshin
 */
public interface BindableViewHolder<T, V extends View> {
    /**
     * Returns item attached to view holder
     * @return {@link View} attached to view holder
     */
    T getItem();

    /**
     * Returns view attached to view holder
     * @return {@link View} attached to view holder
     */
    V getItemView();

    /**
     * Binds item to view
     * @param item item
     */
    void bindItemToView(T item);
}
