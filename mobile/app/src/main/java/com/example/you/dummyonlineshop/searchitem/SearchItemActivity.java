package com.example.you.dummyonlineshop.searchitem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.adapter.HistorySearchAdapter;
import com.example.you.dummyonlineshop.adapter.ItemAdapter;
import com.example.you.dummyonlineshop.adapter.SearchItemAdapter;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.get.ItemsData;
import com.example.you.dummyonlineshop.library.DisplayContentView;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.main.home.HomeViewType;
import com.example.you.dummyonlineshop.search.SearchActivity;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchItemActivity extends AppCompatActivity implements SearchItemContract.View {

    private SearchItemContract.Presenter mPresenter;

    private ImageView btnBack;
    private ImageView btnClear;
    private EditText inputSearch;
    private TextView searchResult;
    private RecyclerView rvItems;
    private RecyclerView rvRecommended;
    private ScrollView contentNotFound;
    private DisplayContentView displayView;

    public void setPresenter(SearchItemContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        new SearchItemPresenter(this);

        rvItems = findViewById(R.id.rv_items);
        btnBack = findViewById(R.id.btn_back);
        btnClear = findViewById(R.id.btn_clear);
        inputSearch = findViewById(R.id.input_search);
        searchResult = findViewById(R.id.search_result);
        contentNotFound = findViewById(R.id.content_not_found);
        rvRecommended = findViewById(R.id.rv_recommended);

        setupDisplayView();
        setupSearchInput();
        setupSearchResult();
        setupClearButton();
        setupBackButton();
    }

    private void setupDisplayView() {
        displayView = new DisplayContentView(findViewById(R.id.content_search_item));
        displayView.addRequirement(SearchItemViewType.SEARCH_ITEM_VIEW);
        displayView.addRequirement(SearchItemViewType.RECOMMENDED_ITEM_VIEW);
        displayView.loadView();
    }

    private void setupBackButton() {
        btnBack.setOnClickListener((View view) ->  onBackPressed());
    }

    private void setupClearButton() {
        btnClear.setOnClickListener((View view) -> {
            Intent intent = new Intent(getBaseContext(), SearchActivity.class);
            intent.putExtra("SEARCH_NAME", "");
            startActivity(intent);
        });
    }

    private void setupSearchInput() {
        inputSearch.setFocusable(false);
        inputSearch.setText(getIntent().getStringExtra("SEARCH_NAME"));
        inputSearch.setOnClickListener((View view) -> {
            Intent intent = new Intent(getBaseContext(), SearchActivity.class);
            intent.putExtra("SEARCH_NAME", getIntent().getStringExtra("SEARCH_NAME"));
            startActivity(intent);
        });
    }

    private void setupSearchResult() {
        searchResult.setText(getIntent().getStringExtra("SEARCH_NAME"));
        mPresenter.getSearchItem(getIntent().getStringExtra("SEARCH_NAME"));
    }

    public void showItems(List<Item> items) {
        if (items != null) {
            displayView.successRequirement(SearchItemViewType.SEARCH_ITEM_VIEW);
            displayView.successRequirement(SearchItemViewType.RECOMMENDED_ITEM_VIEW);
            displayView.loadView();

            rvItems.setVisibility(View.VISIBLE);
            contentNotFound.setVisibility(View.GONE);
            GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
            RecyclerView.Adapter adapter = new SearchItemAdapter(items);

            rvItems.setLayoutManager(layoutManager);
            rvItems.setAdapter(adapter);
        } else {
            displayView.successRequirement(SearchItemViewType.SEARCH_ITEM_VIEW);
            displayView.loadView();

            rvItems.setVisibility(View.GONE);
            contentNotFound.setVisibility(View.VISIBLE);
            mPresenter.getRecommendedItem();
        }
    }

    public void showRecommendedItems(List<Item> items) {
        displayView.successRequirement(SearchItemViewType.RECOMMENDED_ITEM_VIEW);
        displayView.loadView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new ItemAdapter(items);

        rvRecommended.setLayoutManager(layoutManager);
        rvRecommended.setAdapter(adapter);
    }

    public void showErrorConnection(String tag) {
        displayView.errorRequirement(tag);
        displayView.loadView();
    }
}
