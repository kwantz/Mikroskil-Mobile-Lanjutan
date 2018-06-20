package com.example.you.dummyonlineshop.security.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.get.LoginData;
import com.example.you.dummyonlineshop.data.post.LoginBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private Call<LoginData> callLogin;
    private OnlineShopService service;
    private final LoginContract.View mView;

    public LoginPresenter(@NonNull LoginContract.View mainView) {
        mView = checkNotNull(mainView, "MainView cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void submit(String username, String password) {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername(username);
        loginBody.setPassword(password);

        callLogin = service.login(loginBody);
        callLogin.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData data = response.body();
                    String token = (data.getCode() == 200) ? data.getToken() : "";

                    mView.loginResponse(data.getMessage(), token);
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                mView.loginResponse("Terjadi kesalahan pada server", "");
            }
        });
    }
}
