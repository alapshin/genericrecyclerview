package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DefaultItemProvider<T> implements ItemProvider<T> {
    private List<T> items = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void addItem(T item) {
        items.add(item);
        if (adapter != null) {
            adapter.notifyItemInserted(getItemCount() - 1);
        }
    }

    @Override
    public void addItems(List<T> items) {
        int size = getItemCount();
        int sizeToInsert = items.size();
        items.addAll(items);
        if (adapter != null) {
            adapter.notifyItemRangeInserted(size, sizeToInsert);
        }
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    @Override
    public void setItem(int position, T item) {
        items.set(position, item);
        if (adapter != null) {
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void setItems(List<T> items) {
        this.items = items;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        items.add(toPosition, items.remove(fromPosition));
        if (adapter != null) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void removeItem(int position) {
        items.remove(position);
        if (adapter != null) {
            adapter.notifyItemRemoved(position);
        }
    }

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
}
