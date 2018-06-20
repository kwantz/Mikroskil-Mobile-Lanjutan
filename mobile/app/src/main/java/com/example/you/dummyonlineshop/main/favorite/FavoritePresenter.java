package com.example.you.dummyonlineshop.main.favorite;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.get.ItemsData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class FavoritePresenter implements FavoriteContract.Presenter {

    private Session session;
    private Call<ItemsData> callTopItem;
    private Call<ItemsData> callFavoriteItem;

    private final FavoriteContract.View mView;

    public FavoritePresenter(@NonNull FavoriteContract.View favoriteView, Context context) {
        session = new Session(context);
        mView = checkNotNull(favoriteView, "FavoriteView cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        callTopItem = service.getHotDealItems();
        callFavoriteItem = service.getFavorite("Bearer " + session.getToken());
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
                mView.showErrorConnection(FavoriteViewType.TOP_ITEM_VIEW);
            }
        });
    }

    public void setupFavoriteItem() {
        callFavoriteItem.enqueue(new Callback<ItemsData>() {
            @Override
            public void onResponse(Call<ItemsData> call, Response<ItemsData> response) {
                if (response.isSuccessful()) {
                    List<Item> data = response.body().getItems();
                    mView.showRecycleViewFavoriteItem(data);
                }
            }

            @Override
            public void onFailure(Call<ItemsData> call, Throwable t) {
                mView.showErrorConnection(FavoriteViewType.FAVORITE_VIEW);
            }
        });
    }
}
