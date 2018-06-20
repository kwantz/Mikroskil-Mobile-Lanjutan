package com.example.you.dummyonlineshop.main.home;

import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.data.Category;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.get.CategoriesData;
import com.example.you.dummyonlineshop.data.get.ItemsData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private Call<CategoriesData> callCategory;
    private Call<ItemsData> callNewItem;
    private Call<ItemsData> callTopItem;
    private Call<ItemsData> callRecommendedItem;

    private final HomeContract.View mView;

    public HomePresenter(@NonNull HomeContract.View homeView) {
        mView = checkNotNull(homeView, "HomeView cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        callCategory = service.getCategories();
        callNewItem = service.getNewItems();
        callTopItem = service.getHotDealItems();
        callRecommendedItem = service.getRecommendedItems();
    }

    public void setupCategory() {
        callCategory.enqueue(new Callback<CategoriesData>() {
            @Override
            public void onResponse(Call<CategoriesData> call, Response<CategoriesData> response) {
                if (response.isSuccessful()) {
                    List<Category> data = response.body().getCategories();
                    mView.showRecycleViewCategory(data);
                }
            }

            @Override
            public void onFailure(Call<CategoriesData> call, Throwable t) {
                mView.showErrorConnection(HomeViewType.CATEGORY_VIEW);
            }
        });
    }

    public void setupNewItem() {
        callNewItem.enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                if (response.isSuccessful()) {
                    List<Item> data = response.body().getItems();
                    mView.showRecycleViewNewItem(data);
                }
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                mView.showErrorConnection(HomeViewType.NEW_ITEM_VIEW);
            }
        });
    }

    public void setupTopItem() {
        callTopItem.enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                if (response.isSuccessful()) {
                    List<Item> data = response.body().getItems();
                    mView.showRecycleViewTopItem(data);
                }
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                mView.showErrorConnection(HomeViewType.TOP_ITEM_VIEW);
            }
        });
    }

    public void setupRecommendedItem() {
        callRecommendedItem.enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                if (response.isSuccessful()) {
                    List<Item> data = response.body().getItems();
                    mView.showRecycleViewRecommendedItem(data);
                }
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                mView.showErrorConnection(HomeViewType.RECOMMENDED_ITEM_VIEW);
            }
        });
    }
}
