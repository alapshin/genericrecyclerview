package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class DefaultAdapter<T extends Item, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ItemProvider<T> itemProvider;
    private SelectionManager selectionManager;
    private ViewHolderDelegateManager<T, VH> delegateManager;

    @Override
    public int getItemCount() {
        if (itemProvider == null) {
            throw new IllegalStateException("No ItemProvider added to adapter");
        }
        return itemProvider.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        if (itemProvider == null) {
            throw new IllegalStateException("No ItemProvider added to adapter");
        }
        return itemProvider.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (itemProvider == null) {
            throw new IllegalStateException("No ItemProvider added to adapter");
        }
        if (delegateManager == null) {
            throw new IllegalStateException("No ViewHolderDelegate manager added to adapter");
        }

        T item = itemProvider.getItem(position);
        return delegateManager.getItemViewType(item);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (itemProvider == null) {
            throw new IllegalStateException("No ItemProvider added to adapter");
        }
        if (delegateManager == null) {
            throw new IllegalStateException("No ViewHolderDelegate manager added to adapter");
        }

        T item = itemProvider.getItem(position);
        delegateManager.onBindViewHolder(holder, item);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (delegateManager == null) {
            throw new IllegalStateException("No ViewHolderDelegate manager added to adapter");
        }
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    public void setItemProvider(ItemProvider<T> itemProvider) {
        this.itemProvider = itemProvider;
    }

    public void setSelectionManager(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public void setViewHolderDelegateManager(ViewHolderDelegateManager<T, VH> delegateManager) {
        this.delegateManager = delegateManager;
    }
}
