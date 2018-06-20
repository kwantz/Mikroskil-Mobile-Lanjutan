package com.example.you.dummyonlineshop.searchcategory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.you.dummyonlineshop.data.get.ItemsData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.searchitem.SearchItemContract;
import com.example.you.dummyonlineshop.searchitem.SearchItemViewType;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchCategoryPresenter implements SearchCategoryContract.Presenter {

    private OnlineShopService service;
    private SearchCategoryContract.View mView;

    public SearchCategoryPresenter(@NonNull SearchCategoryContract.View searchItemView) {
        mView = checkNotNull(searchItemView, "SearchItemActivity cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void getSearchItem(String search) {
        service.getSearchCategoryItems(search).enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                mView.showItems(response.body().getItems());
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                Log.e("Error", t.toString());
                mView.showErrorConnection(SearchCategoryViewType.CATEGORY_ITEM_VIEW);
            }
        });
    }
}
