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


public abstract class GenericRecyclerAdapter<T extends GenericItem>
        extends RecyclerView.Adapter<GenericViewHolder<T, ? extends View>> {
    public enum ChoiceMode {
        NONE,
        SINGLE,
        MULTI,
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
    public void onBindViewHolder(GenericViewHolder<T, ? extends View> holder, int position) {
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

    public void setItems(List<T> items) {
        this.items = items;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void setChoiceMode(ChoiceMode mode) {
        choiceMode = mode;
    }

    public void selectItem(int position) {
        if (choiceMode == ChoiceMode.NONE) {
            return;
        }

        if (choiceMode == ChoiceMode.SINGLE) {
            int previousPosition = -1;

            if (selectedItems.size() == 1) {
                previousPosition = selectedItems.keyAt(0);
            }
            if (previousPosition == position) {
                return;
            }
            if (previousPosition != -1) {
                T prevItem = getItem(previousPosition);
                prevItem.setSelected(false);
                notifyItemChanged(previousPosition);
            }
            selectedItems.clear();
        }
        T item = getItem(position);
        item.setSelected(true);
        selectedItems.put(position, true);
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
