package com.alapshin.genericrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * This delegate provides methods to create and bind {@link RecyclerView.ViewHolder} from
 * {@link RecyclerView.Adapter}.
 * To call this methods you should use {@link ViewHolderDelegateManager}.
 *
 * @param <T> item type
 * @author Andrei Lapshin
 */
public interface ViewHolderDelegate<T, VH extends RecyclerView.ViewHolder> {
    /**
     * Get the view type.
     *
     * @return the integer representing the view type
     */
    int getItemViewType();

    /**
     * Called to determine whether this ViewHolderDelegate is the responsible for the given item.
     *
     * @param item The item from data source of the adapter
     * @return true, if this item is handled by this delegate, otherwise false
     */
    boolean isForViewType(@NonNull T item);

    /**
     * Creates the  {@link RecyclerView.ViewHolder} for the given item
     *
     * @param parent The ViewGroup parent
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    @NonNull
    VH onCreateViewHolder(ViewGroup parent);

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the item
     *
     * @param holder The {@link RecyclerView.ViewHolder} to bind
     * @param item The item from the datasource
     */
    void onBindViewHolder(@NonNull VH holder, T item);
}
