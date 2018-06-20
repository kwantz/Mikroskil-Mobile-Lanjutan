package com.example.you.dummyonlineshop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.searchitem.SearchItemActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder> {

    private View view;
    private List<String> listHistorySearch;

    public HistorySearchAdapter(Set<String> listHistorySearch) {
        this.listHistorySearch = new ArrayList<>(listHistorySearch);
        Log.e("Disini", Integer.toString(this.listHistorySearch.size()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textSearch;
        private LinearLayout searchView;

        public ViewHolder(View view) {
            super(view);
            this.textSearch = view.findViewById(R.id.text_search);
            this.searchView = view.findViewById(R.id.search_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_history_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String search = this.listHistorySearch.get(position);
        holder.textSearch.setText(search);

        holder.searchView.setOnClickListener((View view) -> {
            Intent intent = new Intent(view.getContext(), SearchItemActivity.class);
            intent.putExtra("SEARCH_NAME", search);
            view.getContext().startActivity(intent);
            ((Activity) view.getContext()).finish();
        });

        Log.e("Posisi ke-", Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return this.listHistorySearch.size();
    }

}
