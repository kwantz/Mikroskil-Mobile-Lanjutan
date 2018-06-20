package com.example.you.dummyonlineshop.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.category.CategoryActivity;
import com.example.you.dummyonlineshop.data.Category;
import com.example.you.dummyonlineshop.data.Kategori;
import com.example.you.dummyonlineshop.searchcategory.SearchCategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private View view;
    private List<Category> listCategory;

    public CategoryAdapter(List<Category> listCategory) {
        int step = 0;
        this.listCategory = new ArrayList<>();
        for (Category category: listCategory) {
            if (step == 7) break;
            this.listCategory.add(category);
            step++;
        }

        Category more = new Category();
        more.setName("More");
        more.setIcon("ic_apps_black_24dp");
        this.listCategory.add(more);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView categoryIcon;

        public ViewHolder(View view) {
            super(view);
            this.categoryName = view.findViewById(R.id.category_name);
            this.categoryIcon = view.findViewById(R.id.category_icon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Category kategori = this.listCategory.get(position);

        int id = view.getContext()
            .getResources()
            .getIdentifier(kategori.getIcon(), "drawable", view.getContext().getPackageName());

        holder.categoryName.setText(kategori.getName());
        holder.categoryIcon.setImageResource(id);

        if (kategori.getName().equals("More")) {
            view.setOnClickListener((View view) -> {
                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                view.getContext().startActivity(intent);
            });
        } else {
            view.setOnClickListener((View v) -> {
                Intent intent = new Intent(view.getContext(), SearchCategoryActivity.class);
                intent.putExtra("category_name", kategori.getName());
                intent.putExtra("category_id", Integer.toString(kategori.getId()));
                view.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.listCategory.size();
    }
}
