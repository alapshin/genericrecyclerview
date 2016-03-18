package com.alapshin.genericrecyclerview;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter parametrized with item type
 * @param <T> type of the data item
 *
 * @author alapshin
 * @since 2015-06-11
 */
public class RecyclerAdapter<T extends RecyclerItem>
        extends RecyclerView.Adapter<RecyclerViewHolder> {
    public enum ChoiceMode {
        NONE,
        SINGLE,
    }

    protected List<T> items = new ArrayList<>();
    protected ChoiceMode choiceMode = ChoiceMode.NONE;
    protected SparseBooleanArray selectedItems = new SparseBooleanArray();
    protected OnItemClickListener<T> onItemClickListener;

    protected RecyclerDelegateManager<T> delegateManager = new RecyclerDelegateManager<>();

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    @CallSuper
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    @CallSuper
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        T item = items.get(position);
        delegateManager.onBindViewHolder(holder, item);
        if (onItemClickListener != null) {
            holder.setOnItemClickListener(onItemClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        T item = items.get(position);
        return delegateManager.getItemViewType(item);
    }

    /**
     * Add items to adapter.
     * @param items new items.
     */
    public void addItems(List<T> items) {
        int currentSize = this.items.size();
        int sizeToInsert = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(currentSize, sizeToInsert);
    }

    /**
     * Get data item at position
     * @param position data item position in the adapter
     * @return T
     */
    public T getItem(int position) {
        return (0 <= position && position < items.size()) ? items.get(position) : null;
    }

    /**
     * Set data item at position
     * @param position data item position in the adapter
     * @param item data item
     */
    public void setItem(int position, T item) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    /**
     * Returns adapter data items as {@link java.util.List<T>}
     * @return {@link java.util.List<T>}
     */
    public List<T> getItems() {
        return this.items;
    }

    /**
     * Set adapter data items
     * @param items {@link java.util.List<T>} of data items
     */
    public void setItems(List<T> items) {
        this.items = items;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * Remove data item at position from adapter
     * @param position position of data item in the adapter
     */
    public void removeItem(int position) {
        items.remove(position);
        selectedItems.delete(position);
        notifyItemRemoved(position);
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
