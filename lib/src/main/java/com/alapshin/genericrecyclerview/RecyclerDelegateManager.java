/*
 * Copyright (c) 2015 Hannes Dorfmann.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alapshin.genericrecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * This class is the element that ties {@link RecyclerView.Adapter} together with {@link
 * RecyclerDelegate}.
 * <p>
 * So you have to add / register your {@link RecyclerDelegate}s to this manager by calling {@link
 * #addDelegate(RecyclerDelegate)}
 * </p>
 *
 * <p>
 * Next you have to add this RecyclerDelegateManager to the {@link RecyclerView.Adapter} by calling
 * corresponding methods:
 * <ul>
 * <li> {@link #getItemViewType(T)}: Must be called from {@link
 * RecyclerView.Adapter#getItemViewType(int)}</li>
 * <li> {@link #onCreateViewHolder(ViewGroup, int)}: Must be called from {@link
 * RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}</li>
 * <li> {@link #onBindViewHolder(RecyclerViewHolder, T)}: Must be called from {@link
 * RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}</li>
 * </ul>
 *
 * You can also set a fallback {@link RecyclerDelegate} by using {@link
 * #setFallbackDelegate(RecyclerDelegate)} that will be used if no {@link RecyclerDelegate} is
 * responsible to handle a certain view type. If no fallback is specified, an Exception will be
 * thrown if no {@link RecyclerDelegate} is responsible to handle a certain view type
 * </p>
 *
 * @param <T> The type of items in the datasource of the adapter
 * @author Hannes Dorfmann
 * @author Andrei Lapshin
 */
public class RecyclerDelegateManager<T extends RecyclerItem> {

    private RecyclerDelegate fallbackDelegate;
    /**
     * Map for ViewType to RecyclerDelegate
     */
    SparseArrayCompat<RecyclerDelegate> delegates = new SparseArrayCompat();

    /**
     * Adds an {@link RecyclerDelegate}. Internally calls {@link #addDelegate(RecyclerDelegate,
     * boolean)} with false as parameter.
     *
     * @param delegate the delegate to add
     * @return self
     * @throws IllegalArgumentException if an {@link RecyclerDelegate} is already added (registered)
     * with the same ViewType {@link RecyclerDelegate#getItemViewType()}.
     * @see #addDelegate(RecyclerDelegate, boolean)
     */
    public RecyclerDelegateManager<T> addDelegate(@NonNull RecyclerDelegate delegate) {
        return addDelegate(delegate, false);
    }

    /**
     * Adds an {@link RecyclerDelegate}.
     *
     * @param delegate The delegate to add
     * @param allowReplacingDelegate if true, you allow to replacing the given delegate any previous
     * delegate for the same view type. if false, you disallow and a {@link IllegalArgumentException}
     * will be thrown if you try to replace an already registered {@link RecyclerDelegate} for the
     * same
     * view type.
     * @throws IllegalArgumentException if <b>allowReplacingDelegate</b>  is false and an {@link
     * RecyclerDelegate} is already added (registered)
     * with the same ViewType {@link RecyclerDelegate#getItemViewType()}.
     * @throws IllegalArgumentException if the {@link RecyclerDelegate#getItemViewType()} is the same
     * as fallback RecyclerDelegate one.
     * @see #setFallbackDelegate(RecyclerDelegate)
     */
    public RecyclerDelegateManager<T> addDelegate(@NonNull RecyclerDelegate delegate,
                                                  boolean allowReplacingDelegate) {

        if (delegate == null) {
            throw new NullPointerException("RecyclerDelegate is null!");
        }

        int viewType = delegate.getItemViewType();

        if (fallbackDelegate != null && fallbackDelegate.getItemViewType() == viewType) {
            throw new IllegalArgumentException(
                    "Conflict: the passed RecyclerDelegate has the same ViewType integer (value = " + viewType
                            + ") as the fallback RecyclerDelegate");
        }
        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An RecyclerDelegate is already registered for the viewType = " + viewType
                            + ". Already registered RecyclerDelegate is " + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    /**
     * Removes a previously registered delegate if and only if the passed delegate is registered
     * (checks the reference of the object). This will not remove any other delegate for the same
     * viewType (if there is any).
     *
     * @param delegate The delegate to remove
     * @return self
     */
    public RecyclerDelegateManager<T> removeDelegate(@NonNull RecyclerDelegate delegate) {

        if (delegate == null) {
            throw new NullPointerException("RecyclerDelegate is null");
        }

        RecyclerDelegate queried = delegates.get(delegate.getItemViewType());
        if (queried != null && queried == delegate) {
            delegates.remove(delegate.getItemViewType());
        }
        return this;
    }

    /**
     * Removes the RecyclerDelegate for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    public RecyclerDelegateManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#getItemViewType(int)}. Internally it scans all
     * the registered {@link RecyclerDelegate} and picks the right one to return the ViewType integer.
     *
     * @param item Item from adapter's data source
     * @return the ViewType (integer)
     * @throws IllegalArgumentException if no {@link RecyclerDelegate} has been found that is
     * responsible for the given data element in data set (No {@link RecyclerDelegate} for the given
     * ViewType)
     * @throws NullPointerException if items is null
     */
    public int getItemViewType(@NonNull T item) {

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            RecyclerDelegate delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item)) {
                return delegate.getItemViewType();
            }
        }

        if (fallbackDelegate != null) {
            return fallbackDelegate.getItemViewType();
        }

        throw new IllegalArgumentException(
                "No RecyclerDelegate added that matches item in data source");
    }

    /**
     * This method must be called in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     *
     * @param parent the parent
     * @param viewType the view type
     * @return The new created ViewHolder
     * @throws NullPointerException if no RecyclerDelegate has been registered for ViewHolders
     * viewType
     */
    @NonNull public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerDelegate delegate = delegates.get(viewType);
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw new NullPointerException("No RecyclerDelegate added for ViewType " + viewType);
            } else {
                delegate = fallbackDelegate;
            }
        }

        RecyclerViewHolder vh = delegate.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException(
                    "ViewHolder returned from RecyclerDelegate " + delegate + " for ViewType =" + viewType
                            + " is null!");
        }
        return vh;
    }

    /**
     * Must be called from{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     *
     * @param viewHolder the ViewHolder to bind
     * @param item the item from the data source
     * @throws NullPointerException if no RecyclerDelegate has been registered for ViewHolders
     * viewType
     */
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, T item) {

        RecyclerDelegate delegate = delegates.get(viewHolder.getItemViewType());
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw new NullPointerException(
                        "No RecyclerDelegate added for ViewType " + viewHolder.getItemViewType());
            } else {
                delegate = fallbackDelegate;
            }
        }

        delegate.onBindViewHolder(viewHolder, item);
    }

    /**
     * Set a fallback delegate that should be used if no {@link RecyclerDelegate} has been found that
     * can handle a certain view type.
     *
     * @param fallbackDelegate The {@link RecyclerDelegate} that should be used as fallback if no
     * other
     * RecyclerDelegate has handled a certain view type. <code>null</code> you can set this to null if
     * you want to remove a previously set fallback RecyclerDelegate
     * @throws IllegalArgumentException If passed Fallback
     */
    public RecyclerDelegateManager<T> setFallbackDelegate(
            @Nullable RecyclerDelegate fallbackDelegate) {

        if (fallbackDelegate != null) {
            // Setting a new fallback delegate
            int delegatesCount = delegates.size();
            int fallbackViewType = fallbackDelegate.getItemViewType();
            for (int i = 0; i < delegatesCount; i++) {
                RecyclerDelegate delegate = delegates.valueAt(i);
                if (delegate.getItemViewType() == fallbackViewType) {
                    throw new IllegalArgumentException(
                            "Conflict: The given fallback - delegate has the same ViewType integer (value = "
                                    + fallbackViewType + ")  as an already assigned RecyclerDelegate "
                                    + delegate.getClass().getName());
                }
            }
        }
        this.fallbackDelegate = fallbackDelegate;

        return this;
    }
}
