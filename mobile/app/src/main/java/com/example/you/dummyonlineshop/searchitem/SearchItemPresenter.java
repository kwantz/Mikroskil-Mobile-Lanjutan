package com.example.you.dummyonlineshop.searchitem;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.get.ItemsData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchItemPresenter implements SearchItemContract.Presenter {

    private OnlineShopService service;
    private SearchItemContract.View mView;

    public SearchItemPresenter(@NonNull SearchItemContract.View searchItemView) {
        mView = checkNotNull(searchItemView, "SearchItemActivity cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void getSearchItem(String search) {
        service.getSearchItems(search).enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                mView.showItems(response.body().getItems());
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                Log.e("Error", t.toString());
                mView.showErrorConnection(SearchItemViewType.SEARCH_ITEM_VIEW);
            }
        });
    }

    public void getRecommendedItem() {
        service.getRecommendedItems().enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                mView.showRecommendedItems(response.body().getItems());
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                Log.e("Error", t.toString());
                mView.showErrorConnection(SearchItemViewType.RECOMMENDED_ITEM_VIEW);
            }
        });
    }
}
