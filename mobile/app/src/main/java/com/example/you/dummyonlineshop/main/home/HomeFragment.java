package com.example.you.dummyonlineshop.main.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.CategoryAdapter;
import com.example.you.dummyonlineshop.adapter.ItemAdapter;
import com.example.you.dummyonlineshop.adapter.NewItemAdapter;
import com.example.you.dummyonlineshop.adapter.TopItemAdapter;
import com.example.you.dummyonlineshop.data.Category;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.Kategori;
import com.example.you.dummyonlineshop.library.DisplayContentView;
import com.example.you.dummyonlineshop.notification.NotificationActivity;
import com.example.you.dummyonlineshop.search.SearchActivity;
import com.example.you.dummyonlineshop.security.SecurityActivity;
import com.synnapps.carouselview.CarouselView;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeFragment extends Fragment implements HomeContract.View {

    private View view;
    private Session session;
    private CarouselView carousel;
    private ImageView btnSignIn;
    private ImageView btnNotification;
    private LinearLayout btnSearch;
    private LinearLayout viewNewItem;
    private LinearLayout viewRecommended;
    private RecyclerView rvCategory;
    private RecyclerView rvNewItem;
    private RecyclerView rvTopItem;
    private RecyclerView rvRecommended;
    private DisplayContentView displayView;
    private HomeContract.Presenter mPresenter;

    public HomeFragment() {}

    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getContext());
        view = inflater.inflate(R.layout.fragment_home, container, false);

        viewNewItem = view.findViewById(R.id.content_homepage_new_item);
        viewRecommended = view.findViewById(R.id.content_recommended);
        rvNewItem = view.findViewById(R.id.rv_new_item);
        rvTopItem = view.findViewById(R.id.rv_top_item);
        rvCategory = view.findViewById(R.id.rv_category);
        rvRecommended = view.findViewById(R.id.rv_recommended);
        carousel = view.findViewById(R.id.carousel);

        btnSearch = view.findViewById(R.id.btn_search);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        btnNotification = view.findViewById(R.id.btn_notification);

        setupCarousel();
        setupDisplayView();
        mPresenter.setupCategory();
        mPresenter.setupNewItem();
        mPresenter.setupTopItem();
        if (session.getToken().equals("")) {
            viewRecommended.setVisibility(View.GONE);
            btnNotification.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.VISIBLE);
        } else {
            mPresenter.setupRecommendedItem();
            btnSignIn.setVisibility(View.GONE);
            viewRecommended.setVisibility(View.VISIBLE);
            btnNotification.setVisibility(View.VISIBLE);
        }

        setupButtonEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void setupCarousel() {
        final int[] ads = { R.drawable.ad_1, R.drawable.ad_2, R.drawable.ad_3 };

        carousel.setPageCount(ads.length);
        carousel.setImageListener((int position, ImageView imageView) -> {
            imageView.setImageResource(ads[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        });
    }

    private void setupDisplayView() {
        displayView = new DisplayContentView(view);
        displayView.addRequirement(HomeViewType.CATEGORY_VIEW);
        displayView.addRequirement(HomeViewType.NEW_ITEM_VIEW);
        displayView.addRequirement(HomeViewType.TOP_ITEM_VIEW);
        if (!session.getToken().equals(""))
            displayView.addRequirement(HomeViewType.RECOMMENDED_ITEM_VIEW);

        displayView.loadView();
    }

    public void showRecycleViewCategory(List<Category> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        RecyclerView.Adapter adapter = new CategoryAdapter(data);

        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setNestedScrollingEnabled(false);
        rvCategory.setAdapter(adapter);
        displayView.successRequirement(HomeViewType.CATEGORY_VIEW);
        displayView.loadView();
    }

    public void showRecycleViewNewItem(List<Item> data) {
        if (data != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            RecyclerView.Adapter adapter = new NewItemAdapter(data);

            rvNewItem.setLayoutManager(layoutManager);
            rvNewItem.setAdapter(adapter);
            viewNewItem.setVisibility(View.VISIBLE);
        }
        else {
            viewNewItem.setVisibility(View.GONE);
        }

        displayView.successRequirement(HomeViewType.NEW_ITEM_VIEW);
        displayView.loadView();
    }

    public void showRecycleViewTopItem(List<Item> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new TopItemAdapter(data);

        rvTopItem.setLayoutManager(layoutManager);
        rvTopItem.setAdapter(adapter);
        displayView.successRequirement(HomeViewType.TOP_ITEM_VIEW);
        displayView.loadView();
    }

    public void showRecycleViewRecommendedItem(List<Item> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new ItemAdapter(data);

        rvRecommended.setLayoutManager(layoutManager);
        rvRecommended.setNestedScrollingEnabled(false);
        rvRecommended.setAdapter(adapter);
        displayView.successRequirement(HomeViewType.RECOMMENDED_ITEM_VIEW);
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
