package com.alapshin.genericrecyclerview;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DefaultItemProvider<T extends Item> implements ItemProvider<T> {
    private List<T> items = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemId(int position) {
        return items.get(position).id();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(T item) {
        items.add(item);
        if (adapter != null) {
            adapter.notifyItemInserted(getItemCount() - 1);
        }
    }

    @Override
    public void addItem(int position, T item) {
        items.add(position, item);
        if (adapter != null) {
            adapter.notifyItemInserted(position);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItems(List<T> items) {
        int size = getItemCount();
        this.items.addAll(items);
        if (adapter != null) {
            adapter.notifyItemRangeInserted(size, items.size());
        }
    }

    @Override
    public void addItems(int position, List<T> items) {
        this.items.addAll(position, items);
        if (adapter != null) {
            adapter.notifyItemRangeInserted(position, items.size());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getItems() {
        return items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(int position, T item) {
        items.set(position, item);
        if (adapter != null) {
            adapter.notifyItemChanged(position);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItems(List<T> items) {
        if (adapter != null) {
            DiffUtil.calculateDiff(new DiffUtilCallback(this.items, items)).dispatchUpdatesTo(adapter);
        }
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveItem(int fromPosition, int toPosition) {
        items.add(toPosition, items.remove(fromPosition));
        if (adapter != null) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeItem(int position) {
        items.remove(position);
        if (adapter != null) {
            adapter.notifyItemRemoved(position);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeItems() {
        int size = getItemCount();
        items.clear();
        if (adapter != null) {
            adapter.notifyItemRangeRemoved(0, size);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    class DiffUtilCallback extends DiffUtil.Callback {
        private final List<T> oldList;
        private final List<T> newList;

        DiffUtilCallback(List<T> oldList, List<T> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).id() == newList.get(newItemPosition).id();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}
