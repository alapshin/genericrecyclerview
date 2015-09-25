package com.alapshin.genericrecyclerview;

/**
 * @author alapshin
 * @since 2015-06-11
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ExtendedViewHolder parametrized with item type and custom view type
 * @param <T>
 */
public abstract class GenericViewHolder<T extends GenericViewItem, V extends View>
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    protected T item;
    protected V view;
    protected OnItemClickListener onItemClickListener;

    public GenericViewHolder(V itemView) {
        super(itemView);
        this.view = itemView;
    }

    public V getItemView() {
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Subclasses must implement this method to bind data to UI
     * @param item
     */
    public void bindItem(T item) {
        this.item = item;
    };

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
