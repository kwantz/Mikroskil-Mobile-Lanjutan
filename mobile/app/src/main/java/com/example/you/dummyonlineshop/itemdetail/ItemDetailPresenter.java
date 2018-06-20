package com.example.you.dummyonlineshop.itemdetail;

import android.content.Context;
import android.util.Log;

import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.get.FavoriteData;
import com.example.you.dummyonlineshop.data.get.ItemData;
import com.example.you.dummyonlineshop.data.post.CartBody;
import com.example.you.dummyonlineshop.data.post.FavoriteBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemDetailPresenter implements ItemDetailContract.Presenter {

    private final ItemDetailContract.View mView;
    private OnlineShopService service;
    private Session session;

    public ItemDetailPresenter(ItemDetailContract.View mainView, Context context) {
        session = new Session(context);
        mView = mainView;
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void getItem(int itemId) {
        service.getDetailItem(itemId).enqueue(new Callback<ItemData>() {
            @Override
            public void onResponse(Call<ItemData> call, Response<ItemData> response) {
                if (response.isSuccessful()) {
                    ItemData data = response.body();
                    mView.showItem(data.getItem());
                } else {
                    mView.showErrorConnection(ItemDetailViewType.ITEM_VIEW);
                }
            }

            @Override
            public void onFailure(Call<ItemData> call, Throwable t) {
                mView.showErrorConnection(ItemDetailViewType.ITEM_VIEW);
            }
        });
    }

    public void haveFavorite(int itemId) {
        service.haveFavorite("Bearer " + session.getToken(), itemId).enqueue(new Callback<FavoriteData>() {
            @Override
            public void onResponse(Call<FavoriteData> call, Response<FavoriteData> response) {
                Log.e("Message", response.body().getMessage());
                if (response.isSuccessful()) {
                    mView.setupBtnFavorite(response.body().getFavorite());
                } else {
                    mView.setupBtnFavorite(false);
                }
            }

            @Override
            public void onFailure(Call<FavoriteData> call, Throwable t) {
                mView.setupBtnFavorite(false);
            }
        });
    }

    public void deleteFavorite(int itemId) {
        FavoriteBody favoriteBody = new FavoriteBody();
        favoriteBody.setItem(itemId);
        service.deleteFavorite("Bearer " + session.getToken(), favoriteBody).enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                mView.setupFavorite();
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                mView.setupFavorite();
            }
        });
    }

    public void addFavorite(int itemId) {
        FavoriteBody favoriteBody = new FavoriteBody();
        favoriteBody.setItem(itemId);
        service.addFavorite("Bearer " + session.getToken(), favoriteBody).enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                mView.setupFavorite();
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                mView.setupFavorite();
            }
        });
    }

    public void addItemToCart(int item, String qty, String note) {
        CartBody cartBody = new CartBody();
        cartBody.setItem(item);
        cartBody.setQty(Integer.parseInt(qty));
        cartBody.setNote(note);
        service.addToCart("Bearer " + session.getToken(), cartBody).enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                if (response.isSuccessful()) {
                    Log.e("Message", response.body().getMessage());
                    if (response.body().getCode() == 200) {
                        mView.setupAddCart();
                    }
                }
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    public void haveItemInCart(Item item) {
        service.haveItemInCart("Bearer " + session.getToken(), item.getId()).enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        mView.setupCart(false, item);
                    } else {
                        mView.setupCart(true, item);
                    }
                } else {
                    mView.setupCart(false, item);
                }
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                mView.setupCart(false, item);
            }
        });
    }
}
