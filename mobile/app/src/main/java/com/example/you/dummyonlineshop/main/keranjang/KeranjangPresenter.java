package com.example.you.dummyonlineshop.main.keranjang;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.get.CartData;
import com.example.you.dummyonlineshop.data.get.ItemsData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class KeranjangPresenter implements KeranjangContract.Presenter {

    private Call<CartData> callKeranjang;

    private final KeranjangContract.View mView;
    private Session session;

    public KeranjangPresenter(@NonNull KeranjangContract.View favoriteView, Context context) {
        session = new Session(context);
        mView = checkNotNull(favoriteView, "KeranjangView cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        callKeranjang = service.getAllItemInCart("Bearer " + session.getToken());
    }

    public void setupKeranjang() {
        callKeranjang.enqueue(new Callback<CartData>() {
            @Override
            public void onResponse(Call<CartData> call, Response<CartData> response) {
                if (response.isSuccessful()) {
                    mView.showRecycleViewKeranjang(response.body().getCarts());
                }
            }

            @Override
            public void onFailure(Call<CartData> call, Throwable t) {
                mView.showErrorConnection(KeranjangViewType.KERANJANG_VIEW);
            }
        });
    }
}
