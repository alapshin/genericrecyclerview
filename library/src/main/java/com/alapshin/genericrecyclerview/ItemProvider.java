package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * This interface provides methods to store and receive adapter items.
 *
 * @param <T> item type
 */
public interface ItemProvider<T extends Item> {
    /**
     * Get number of items in provider
     * @return number of items in provider
     */
    int getItemCount();

    /**
     * Get id of item at position
     * @param position item position
     * @return item id
     */
    int getItemId(int position);

    /**
     * Add item to the provider
     * @param item item
     */
    void addItem(T item);

    /**
     * Add item to the provider at specified position
     * @param item item
     * @param position position
     */
    void addItem(int position, T item);

    /**
     * Add items to provider
     * @param items new items
     */
    void addItems(List<T> items);

    /**
     * Add items to the provider at specified position
     * @param items items to add
     * @param position position
     */
    void addItems(int position, List<T> items);

    /**
     * Get item at position
     * @param position item position in the provider
     * @return item
     */
    T getItem(int position);

    /**
     * Returns provider data items as {@link java.util.List}
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
     * @param items {@link java.util.List} of items
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
