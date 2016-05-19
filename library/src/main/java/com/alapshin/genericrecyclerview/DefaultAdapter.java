package com.alapshin.genericrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class DefaultAdapter<T extends Item, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    protected ItemProvider<T> itemProvider;
    protected SelectionManager selectionManager;
    protected ViewHolderDelegateManager<T, VH> delegateManager;

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

    public ItemProvider<T> getItemProvider() {
        return itemProvider;
    }

    public void setItemProvider(ItemProvider<T> itemProvider) {
        this.itemProvider = itemProvider;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    public void setSelectionManager(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public ViewHolderDelegateManager<T, VH> getViewHolderDelegateManager() {
        return delegateManager;
    }

    public void setViewHolderDelegateManager(ViewHolderDelegateManager<T, VH> delegateManager) {
        this.delegateManager = delegateManager;
    }
}
