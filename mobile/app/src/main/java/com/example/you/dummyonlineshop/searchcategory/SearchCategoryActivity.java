package com.example.you.dummyonlineshop.searchcategory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.adapter.SearchItemAdapter;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.library.DisplayContentView;
import com.example.you.dummyonlineshop.searchitem.SearchItemViewType;

import java.util.List;

public class SearchCategoryActivity extends AppCompatActivity implements SearchCategoryContract.View {

    private TextView categoryName;
    private RecyclerView rvItems;
    private ImageView btnBack;
    private DisplayContentView displayView;
    private SearchCategoryContract.Presenter mPresenter;

    public void setPresenter(SearchCategoryContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);

        new SearchCategoryPresenter(this);
        setupDisplayView();

        btnBack = findViewById(R.id.btn_back);
        rvItems = findViewById(R.id.rv_items);
        categoryName = findViewById(R.id.category_name);

        categoryName.setText(getIntent().getStringExtra("category_name"));
        mPresenter.getSearchItem(getIntent().getStringExtra("category_id"));

        btnBack.setOnClickListener((View v) -> onBackPressed());
    }

    private void setupDisplayView() {
        displayView = new DisplayContentView(findViewById(R.id.content_search_category));
        displayView.addRequirement(SearchCategoryViewType.CATEGORY_ITEM_VIEW);
        displayView.loadView();
    }

    public void showItems(List<Item> items) {
        displayView.successRequirement(SearchCategoryViewType.CATEGORY_ITEM_VIEW);
        displayView.loadView();

        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
        RecyclerView.Adapter adapter = new SearchItemAdapter(items);

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);
    }

    public void showErrorConnection(String tag) {
        displayView.errorRequirement(tag);
        displayView.loadView();
    }
}
