package com.alapshin.genericrecyclerview;

/**
 * Interface for adapter items
 *
 * @author alapshin
 * @since 2015-06-10
 */
public abstract class GenericItem {
    private boolean selected_;

    public abstract int id();
    public abstract int type();

    public boolean selected() {
        return selected_;
    }

    public void setSelected(boolean selected) {
        this.selected_ = selected;
    }

}
