package com.alapshin.genericrecyclerview.sample;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alapshin.genericrecyclerview.RecyclerAdapter;
import com.alapshin.genericrecyclerview.RecyclerDelegate;
import com.alapshin.genericrecyclerview.RecyclerViewHolder;

/**
 * @author alapshin
 * @since 2015-12-18
 */
public class SampleAdapter extends RecyclerAdapter<SampleItem> {
    static class SampleHolderRed extends RecyclerViewHolder<SampleItem, TextView> {
        public SampleHolderRed(TextView itemView) {
            super(itemView);
        }
    }

    static class SampleDelegateRed implements RecyclerDelegate<SampleItem, TextView, SampleHolderRed> {
        private final int VIEW_TYPE = 0;

        @Override
        public int getItemViewType() {
            return VIEW_TYPE;
        }

        @Override
        public boolean isForViewType(@NonNull SampleItem item) {
            return item.type() == 0;
        }

        @Override
        public void onBindViewHolder(@NonNull SampleHolderRed holder, SampleItem item) {
            holder.getItemView().setText(item.text());
        }

        @NonNull
        @Override
        public SampleHolderRed onCreateViewHolder(ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextColor(Color.RED);
            return new SampleHolderRed(textView);
        }

    }

    static class SampleHolderBlue extends RecyclerViewHolder<SampleItem, TextView> {
        public SampleHolderBlue(TextView itemView) {
            super(itemView);
        }
    }

    static class SampleDelegateBlue implements RecyclerDelegate<SampleItem, TextView, SampleHolderBlue> {
        private final int VIEW_TYPE = 1;

        @Override
        public int getItemViewType() {
            return VIEW_TYPE;
        }

        @Override
        public boolean isForViewType(@NonNull SampleItem item) {
            return item.type() != 0;
        }

        @Override
        public void onBindViewHolder(@NonNull SampleHolderBlue holder, SampleItem item) {
            holder.getItemView().setText(item.text());
        }

        @NonNull
        @Override
        public SampleHolderBlue onCreateViewHolder(ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextColor(Color.BLUE);
            return new SampleHolderBlue(textView);
        }
    }

    public SampleAdapter() {
        super();

        delegateManager.addDelegate(new SampleDelegateRed());
        delegateManager.addDelegate(new SampleDelegateBlue());
    }
}
