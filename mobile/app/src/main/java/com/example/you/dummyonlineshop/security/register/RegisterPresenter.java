package com.example.you.dummyonlineshop.security.register;

import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.get.LoginData;
import com.example.you.dummyonlineshop.data.post.LoginBody;
import com.example.you.dummyonlineshop.data.post.RegisterBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegisterPresenter implements RegisterContract.Presenter {

    private Call<Base> callRegister;
    private Call<LoginData> callLogin;
    private OnlineShopService service;
    private final RegisterContract.View mView;

    public RegisterPresenter(@NonNull RegisterContract.View mainView) {
        mView = checkNotNull(mainView, "MainView cannot be null!");

        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void submit(String email, String username, String password) {
        RegisterBody registerBody = new RegisterBody();
        registerBody.setUsername(username);
        registerBody.setPassword(password);
        registerBody.setEmail(email);

        callRegister = service.register(registerBody);
        callRegister.enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                if (response.isSuccessful()) {
                    Base data = response.body();
                    if (data.getCode() == 200)
                        getToken(username, password);
                    else
                        mView.registerResponse(data.getMessage(), "");
                }
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                mView.registerResponse("Terjadi kesalahan pada server", "");
            }
        });
    }

    private void getToken(String username, String password) {
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

                    mView.registerResponse(data.getMessage(), token);
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                mView.registerResponse("Terjadi kesalahan pada server", "");
            }
        });
    }
}
