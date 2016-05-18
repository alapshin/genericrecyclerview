package com.alapshin.genericrecyclerview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alapshin.genericrecyclerview.DefaultItemProvider;
import com.alapshin.genericrecyclerview.DefaultSelectionManager;
import com.alapshin.genericrecyclerview.ItemProvider;
import com.alapshin.genericrecyclerview.SelectionManager;
import com.alapshin.genericrecyclerview.ViewHolderDelegateManager;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SampleAdapter recyclerAdapter;
    SelectionManager selectionManager = new DefaultSelectionManager();
    ItemProvider<SampleItem> itemProvider = new DefaultItemProvider<>();;
    ViewHolderDelegateManager<SampleItem, SampleAdapter.SampleHolder> delegateManager =
            new ViewHolderDelegateManager<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new SampleAdapter();
        recyclerAdapter.setOnItemSelectedListener(new SampleAdapter.OnItemSelectedListener<SampleItem>() {
            @Override
            public void onItemSelected(int position, SampleItem item) {
                selectionManager.toggleSelection(position);
            }
        });

        recyclerAdapter.setRecyclerDelegateManager(delegateManager);
        delegateManager.addDelegate(new SampleAdapter.SampleDelegateRed());
        delegateManager.addDelegate(new SampleAdapter.SampleDelegateBlue());

        recyclerAdapter.setSelectionManager(selectionManager);
        selectionManager.setAdapter(recyclerAdapter);
        selectionManager.setChoiceMode(SelectionManager.ChoiceMode.SINGLE);

        itemProvider.setAdapter(recyclerAdapter);
        recyclerAdapter.setItemProvider(itemProvider);
        List<SampleItem> items = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            items.add(i, new SampleItem(i, i % 2, "Item " + (i + 1)));
        }
        itemProvider.setItems(items);

        setContentView(R.layout.activity_sample);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        recyclerView = (RecyclerView) findViewById(R.id.activity_sample_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }
}
