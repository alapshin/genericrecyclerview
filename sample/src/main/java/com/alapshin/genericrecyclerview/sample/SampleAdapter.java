package com.alapshin.genericrecyclerview.sample;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.alapshin.genericrecyclerview.ItemProvider;
import com.alapshin.genericrecyclerview.ViewHolderDelegate;
import com.alapshin.genericrecyclerview.DefaultViewHolder;
import com.alapshin.genericrecyclerview.ViewHolderDelegateManager;
import com.alapshin.genericrecyclerview.SelectionManager;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleHolder> {
    interface OnItemSelectedListener<T> {
        void onItemSelected(int position, T item);
    }

    private SelectionManager selectionManager;
    private ItemProvider<SampleItem> itemProvider;
    private OnItemSelectedListener<SampleItem> selectedListener;
    private ViewHolderDelegateManager<SampleItem, SampleHolder> delegateManager;

    @Override
    public long getItemId(int position) {
        return itemProvider.getItem(position).id();
    }

    @Override
    public int getItemCount() {
        return itemProvider.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return delegateManager.getItemViewType(itemProvider.getItem(position));
    }

    @Override
    public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(SampleHolder holder, int position) {
        holder.setSelection(selectionManager.isSelected(position));
        holder.setOnItemSelectedListener(selectedListener);
        delegateManager.onBindViewHolder(holder, itemProvider.getItem(position));
    }

    public ItemProvider<SampleItem> getItemProvider() {
        return itemProvider;
    }

    public void setItemProvider(ItemProvider<SampleItem> itemProvider) {
        this.itemProvider = itemProvider;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    public void setSelectionManager(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    ViewHolderDelegateManager getRecyclerDelegateManager() {
        return delegateManager;
    }

    public void setRecyclerDelegateManager(ViewHolderDelegateManager<SampleItem, SampleHolder> manager) {
        this.delegateManager = manager;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<SampleItem> selectedListener) {
        this.selectedListener = selectedListener;
    }

    static class SampleHolder extends DefaultViewHolder<SampleItem, CheckBox> {
        OnItemSelectedListener<SampleItem> selectedListener;

        SampleHolder(CheckBox itemView) {
            super(itemView);
        }

        public void setSelection(boolean selected) {
            getItemView().setChecked(selected);
        }

        public void setOnItemSelectedListener(OnItemSelectedListener<SampleItem> listener) {
            this.selectedListener = listener;
        }

        @Override
        public void onBindViewHolder(final SampleItem item) {
            super.onBindViewHolder(item);
            getItemView().setText(item.text());
            getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedListener.onItemSelected(getAdapterPosition(), item);
                }
            });
        }
    }

    static class SampleHolderRed extends SampleHolder {
        SampleHolderRed(CheckBox itemView) {
            super(itemView);
        }
    }

    static class SampleDelegateRed implements ViewHolderDelegate<SampleItem, SampleHolderRed> {
        @Override
        public boolean isForViewType(@NonNull SampleItem item) {
            return item.type() == 1;
        }

        @Override
        public void onBindViewHolder(@NonNull SampleHolderRed holder, SampleItem item) {
            holder.onBindViewHolder(item);
        }

        @NonNull
        @Override
        public SampleHolderRed onCreateViewHolder(ViewGroup parent) {
            CheckBox view = new CheckBox(parent.getContext());
            view.setTextColor(Color.RED);
            return new SampleHolderRed(view);
        }

        @Override
        public Class<SampleHolderRed> getViewHolderType() {
            return SampleHolderRed.class;
        }
    }

    static class SampleHolderBlue extends SampleHolder {
        SampleHolderBlue(CheckBox itemView) {
            super(itemView);
        }
    }

    static class SampleDelegateBlue implements ViewHolderDelegate<SampleItem, SampleHolderBlue> {
        @Override
        public boolean isForViewType(@NonNull SampleItem item) {
            return item.type() == 2;
        }

        @Override
        public void onBindViewHolder(@NonNull SampleHolderBlue holder, SampleItem item) {
            holder.onBindViewHolder(item);
        }

        @NonNull
        @Override
        public SampleHolderBlue onCreateViewHolder(ViewGroup parent) {
            CheckBox view = new CheckBox(parent.getContext());
            view.setTextColor(Color.BLUE);
            return new SampleHolderBlue(view);
        }

        @Override
        public Class<SampleHolderBlue> getViewHolderType() {
            return SampleHolderBlue.class;
        }
    }

}
