package com.alapshin.genericrecyclerview.sample;

import com.alapshin.genericrecyclerview.RecyclerItem;

/**
 * @author alapshin
 * @since 2015-12-18
 */
public class SampleItem implements RecyclerItem {
    private int type;
    private String text;

    public int id() {
        return -1;
    }

    public int type() {
        return type;
    }

    public String text() {
        return text;
    }

    public SampleItem(int type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public boolean selected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
    }
}
