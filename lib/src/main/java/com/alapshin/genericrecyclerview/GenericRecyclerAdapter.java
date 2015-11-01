package com.alapshin.genericrecyclerview;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter parametrized with item type
 * @param <T> type of item for binding
 *
 * @author alapshin
 * @since 2015-06-11
 */


public abstract class GenericRecyclerAdapter<T extends GenericItem, V extends View,
        VH extends GenericViewHolder<T, V>>
        extends RecyclerView.Adapter<VH> {
    public enum ChoiceMode {
        NONE,
        SINGLE,
        MULTIPLE,
    }

    protected List<T> items = new ArrayList<>();
    protected ChoiceMode choiceMode = ChoiceMode.NONE;
    protected SparseBooleanArray selectedItems = new SparseBooleanArray();
    protected OnItemClickListener onItemClickListener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    @CallSuper
    public void onBindViewHolder(VH holder, int position) {
        T item = items.get(position);
        if (item != null) {
            holder.bindItem(item);
        }
        if (onItemClickListener != null) {
            holder.setOnItemClickListener(onItemClickListener);
        }
    }

    public T getItem(int position) {
        return (0 <= position && position < items.size()) ? items.get(position) : null;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setItems(List<T> items) {
        this.items = items;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void setChoiceMode(ChoiceMode mode) {
        choiceMode = mode;
    }

    public void setSelection(int position, boolean selected) {
        if (position < 0 || position > items.size()) {
            throw new IllegalArgumentException();
        }

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
                if (oldPosition != -1) {
                    getItem(oldPosition).setSelected(false);
                }
                notifyItemChanged(oldPosition);
            }
        }

        boolean oldSelected = selectedItems.get(position, false);
        if (oldSelected != selected) {
            selectedItems.append(position, selected);
            getItem(position) .setSelected(selected);
            notifyItemChanged(position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
