package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public interface ItemProvider<T> {
    /**
     * Get item count in provider
     * @return item count
     */
    int getItemCount();

    /**
     * Add item to the provider
     * @param item item
     */
    void addItem(T item);

    /**
     * Add items to provider
     * @param items new items
     */
    void addItems(List<T> items);

    /**
     * Get item at position
     * @param position item position in the provider
     * @return item
     */
    T getItem(int position);

    /**
     * Returns provider data items as {@link java.util.List<T>}
     * @return items
     */
    List<T> getItems();

    /**
     * Set item at position
     * @param position item position in the provider
     * @param item item
     */
    void setItem(int position, T item);

    /**
     * Set provider items
     * @param items {@link java.util.List<T>} of items
     */
    void setItems(List<T> items);

    /**
     * Move item fromPosition one position toPosition another
     * @param fromPosition current item position
     * @param toPosition new item position
     */
    void moveItem(int fromPosition, int toPosition);

    /**
     * Remove item at position from provider
     * @param position item position in the provider
     */
    void removeItem(int position);

    /**
     * Remove all items from provider
     */
    void removeItems();

    void setAdapter(RecyclerView.Adapter adapter);
}
