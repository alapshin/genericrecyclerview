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
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * This delegate provide method to hook in this delegate to {@link RecyclerView.Adapter} lifecycle.
 * This "hook in" mechanism is provided by {@link ViewHolderDelegateManager} and that is the
 * component you have to use.
 *
 * @param <T> The type of the data source
 * @author Hannes Dorfmann
 * @author Andrei Lapshin
 */
public interface ViewHolderDelegate<T, VH extends RecyclerView.ViewHolder> {
    /**
     * Get the view type integer. Must be unique within every Adapter
     *
     * @return the integer representing the view type
     */
    int getItemViewType();

    /**
     * Called to determine whether this ViewHolderDelegate is the responsible for the given data
     * element.
     *
     * @param item The item from data source of the Adapter
     * @return true, if this item is responsible,  otherwise false
     */
    boolean isForViewType(@NonNull T item);

    /**
     * Creates the  {@link RecyclerView.ViewHolder} for the given data source item
     *
     * @param parent The ViewGroup parent of the given datasource
     * @return The new instantiated {@link DefaultViewHolder}
     */
    @NonNull
    VH onCreateViewHolder(ViewGroup parent);

    /**
     * Called to bind the {@link DefaultViewHolder} to the item of the data source set
     *
     * @param holder The {@link DefaultViewHolder} to bind
     * @param item The item from the datasource
     */
    void onBindViewHolder(@NonNull VH holder, T item);
}
