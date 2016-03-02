package com.alapshin.genericrecyclerview;

/**
 * Base class for adapter data items
 *
 * @author Andrei Lapshin
 */
public interface RecyclerItem {
    /**
     * Unique item id
     * {@link android.support.v7.widget.RecyclerView.Adapter#getItemId(int)}
     * @return integer representing item id
     */
    int id();

    /**
     * Item view type
     * {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     * @return integer representing item view type
     */
    int type();

    boolean selected();
    void setSelected(boolean selected);
}
