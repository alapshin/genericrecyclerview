package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

public class DefaultSelectionManager implements SelectionManager {
    private ChoiceMode choiceMode = ChoiceMode.NONE;
    private RecyclerView.Adapter adapter;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();


    @Override
    public void clear() {
        selectedItems.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setChoiceMode(ChoiceMode mode) {
        choiceMode = mode;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setSelection(int position, boolean selected) {
        if (choiceMode == ChoiceMode.NONE) {
            return;
        }

        if (choiceMode == ChoiceMode.SINGLE) {
            // Find previously selected item
            int oldPosition = -1;
            if (selectedItems.size() == 1) {
                oldPosition = selectedItems.keyAt(0);
            }

            if (oldPosition != position) {
                selectedItems.clear();
                if (adapter != null) {
                    adapter.notifyItemChanged(oldPosition);
                }
            }
        }

        boolean oldSelected = selectedItems.get(position, false);
        if (oldSelected != selected) {
            selectedItems.append(position, selected);
            if (adapter != null) {
                adapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    public boolean isSelected(int position) {
        switch (choiceMode) {
            case NONE:
                return false;
            case SINGLE:
                return selectedItems.size() == 1
                        && selectedItems.keyAt(0) == position
                        && selectedItems.valueAt(0);
            case MULTI:
                return selectedItems.get(position, false);
            default:
                return false;
        }
    }

    @Override
    public void toggleSelection(int position) {
        setSelection(position, !isSelected(position));
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }
}
