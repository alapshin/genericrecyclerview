package com.alapshin.genericrecyclerview;

/**
 * @author alapshin
 * @since 2015-06-09
 */
public interface OnItemClickListener<T> {
    void onItemClick(int position, T item);
}
