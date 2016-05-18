package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;

public interface SelectionManager {
    enum ChoiceMode {
        NONE,
        SINGLE,
        MULTI,
    }

    void clear();
    void setChoiceMode(ChoiceMode mode);
    void setSelection(int position, boolean selected);
    boolean isSelected(int position);
    void toggleSelection(int position);

    void setAdapter(RecyclerView.Adapter adapter);
}
