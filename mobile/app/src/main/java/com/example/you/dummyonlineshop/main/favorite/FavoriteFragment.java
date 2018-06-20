package com.example.you.dummyonlineshop.main.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.FavoriteAdapter;
import com.example.you.dummyonlineshop.adapter.TopItemAdapter;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.library.DisplayContentView;
import com.example.you.dummyonlineshop.notification.NotificationActivity;
import com.example.you.dummyonlineshop.search.SearchActivity;
import com.example.you.dummyonlineshop.security.SecurityActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FavoriteFragment extends Fragment implements FavoriteContract.View {

    private View view;
    private RecyclerView rvTopItem;
    private RecyclerView rvFavoriteItem;
    private LinearLayout contentFavoriteList;
    private ScrollView contentFavoriteEmpty;
    private DisplayContentView displayView;
    private ImageView btnSignIn;
    private ImageView btnNotification;
    private LinearLayout btnSearch;

    private FavoriteContract.Presenter mPresenter;

    public FavoriteFragment() {}

    public void setPresenter(@NonNull FavoriteContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_favorite, menu);
        menu.findItem(R.id.item_search).getIcon().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Session session = new Session(getContext());
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvTopItem = view.findViewById(R.id.rv_top_item);
        rvFavoriteItem = view.findViewById(R.id.rv_favorite_item);
        contentFavoriteList = view.findViewById(R.id.content_favorite_list);
        contentFavoriteEmpty = view.findViewById(R.id.content_favorite_empty);

        setupDisplayView();
        mPresenter.setupTopItem();
        mPresenter.setupFavoriteItem();

        btnSearch = view.findViewById(R.id.btn_search);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        btnNotification = view.findViewById(R.id.btn_notification);
        if (session.getToken().equals("")) {
            btnNotification.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.GONE);
            btnNotification.setVisibility(View.VISIBLE);
        }

        setupButtonEvent();

        setHasOptionsMenu(true);
        return view;
    }

    private void setupDisplayView() {
        displayView = new DisplayContentView(view);
        displayView.addRequirement(FavoriteViewType.TOP_ITEM_VIEW);
        displayView.addRequirement(FavoriteViewType.FAVORITE_VIEW);
        displayView.loadView();
    }

    public void showRecycleViewTopItem(List<Item> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new TopItemAdapter(data);

        rvTopItem.setLayoutManager(layoutManager);
        rvTopItem.setAdapter(adapter);

        displayView.successRequirement(FavoriteViewType.TOP_ITEM_VIEW);
        displayView.loadView();
    }

    public void showRecycleViewFavoriteItem(List<Item> data) {
        if (data != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            RecyclerView.Adapter adapter = new FavoriteAdapter(data);

            rvFavoriteItem.setLayoutManager(layoutManager);
            rvFavoriteItem.setAdapter(adapter);
            rvFavoriteItem.setNestedScrollingEnabled(false);

            contentFavoriteEmpty.setVisibility(View.GONE);
            contentFavoriteList.setVisibility(View.VISIBLE);
        }
        else {
            contentFavoriteEmpty.setVisibility(View.VISIBLE);
            contentFavoriteList.setVisibility(View.GONE);
        }

        displayView.successRequirement(FavoriteViewType.FAVORITE_VIEW);
        displayView.loadView();
    }

    public void showErrorConnection(String tag) {
        displayView.errorRequirement(tag);
        displayView.loadView();
    }

    public void setupButtonEvent() {
        btnSearch.setOnClickListener((View view) -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra("SEARCH_NAME", "");
            startActivity(intent);
        });

        btnSignIn.setOnClickListener((View view) -> {
            Intent intent = new Intent(getContext(), SecurityActivity.class);
            startActivity(intent);
        });

        btnNotification.setOnClickListener((View view) -> {
            Intent intent = new Intent(getContext(), NotificationActivity.class);
            startActivity(intent);
        });
    }
}
