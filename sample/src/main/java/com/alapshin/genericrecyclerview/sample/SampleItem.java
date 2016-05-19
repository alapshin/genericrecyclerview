package com.alapshin.genericrecyclerview.sample;

import com.alapshin.genericrecyclerview.Item;

/**
 * @author alapshin
 * @since 2015-12-18
 */
public class SampleItem implements Item {
    private int id;
    private int type;
    private String text;

    public int id() {
        return id;
    }

    public int type() {
        return type;
    }

    public String text() {
        return text;
    }

    public SampleItem(int id, int type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }
}
