package com.example.you.dummyonlineshop.splashscreen;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.post.TokenBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private Context context;
    private OnlineShopService service;
    private final SplashScreenContract.View mView;

    public SplashScreenPresenter(@NonNull SplashScreenContract.View mainView, Context baseContext) {
        mView = checkNotNull(mainView, "MainView cannot be null!");

        context = baseContext;
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void checkToken(String token) {
        TokenBody checkToken = new TokenBody();
        checkToken.setToken(token);
        service.checkToken(checkToken).enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                if (response.isSuccessful()) {
                    Base data = response.body();
                    if (data.getCode() == 200) {
                        mView.changeActivity(true);
                    } else {
                        mView.changeActivity(false);
                    }
                } else {
                    mView.changeActivity(false);
                }
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                mView.changeActivity(false);
            }
        });
    }
}
