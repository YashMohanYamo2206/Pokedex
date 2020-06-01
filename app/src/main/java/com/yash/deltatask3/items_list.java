package com.yash.deltatask3;


import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class items_list extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private items_list_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Handler handler = new Handler();
    int item_id = 20;
    item items = new item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        mRecyclerView = findViewById(R.id.item_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addData(item_id);
        mRecyclerView.addOnScrollListener(new CustomOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(mRecyclerView, "Loading more items....", Snackbar.LENGTH_SHORT).show();
                        if (item_id <= 954) {
                            item_id = item_id + 20;
                            addData(item_id);
                        }
                    }
                }, 600);
            }

        });
        EditText search = findViewById(R.id.detailsearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serchaction(s.toString());
            }
        });
    }

    private void serchaction(String s) {
        List<Region> results = new ArrayList<>();
        if (!s.equals("")) {
            for (int i = 0; i < 954; i++) {
                if (items.getResults().get(i).getName().startsWith(s)) {
                    results.add(items.getResults().get(i));
                }
            }
            mAdapter.notifyDataSetChanged();
            mAdapter = new items_list_adapter(results,results.size());
            mRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
            mAdapter = new items_list_adapter(items.getResults(),item_id);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    private void addData(int item_id) {
        final int i = item_id;
        if (item_id <= 20) {
            PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
            service.get_item(954).enqueue(new Callback<item>() {
                @Override
                public void onResponse(Call<item> call, Response<item> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(items_list.this, "kuch to gadbad hai", Toast.LENGTH_SHORT).show();
                    }
                    items = response.body();
                    mAdapter = new items_list_adapter(items.getResults(), i);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<item> call, Throwable t) {

                }
            });
        } else {
            mAdapter.mCount = i;
            mAdapter.notifyDataSetChanged();
        }
    }
}
