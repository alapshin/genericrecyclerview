package com.alapshin.genericrecyclerview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SampleAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new SampleAdapter();
        List<SampleItem> items = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            items.add(i, new SampleItem(i % 2, "Item " + (i + 1)));
        }
        recyclerAdapter.setItems(items);

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
