package com.example.you.onlineshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private View view;
    private List<String> listCategory;

    public CategoryAdapter(List<String> listCategory) {
        this.listCategory = listCategory;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;

        public ViewHolder(View view) {
            super(view);
            this.categoryName = view.findViewById(R.id.category_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name = this.listCategory.get(position);
        holder.categoryName.setText(name);
    }

    @Override
    public int getItemCount() {
        return this.listCategory.size();
    }
}
