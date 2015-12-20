package com.alapshin.genericrecyclerview;

/**
 * Base class for adapter data items
 *
 * @author Andrei Lapshin
 */
public abstract class RecyclerItem {
    private boolean selected_;

    /**
     * Unique item id
     * {@link android.support.v7.widget.RecyclerView.Adapter#getItemId(int)}
     * @return integer representing item id
     */
    public abstract int id();

    /**
     * Item view type
     * {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     * @return integer representing item view type
     */
    public abstract int type();

    public boolean selected() {
        return selected_;
    }

    public void setSelected(boolean selected) {
        this.selected_ = selected;
    }
}
