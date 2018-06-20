package com.example.you.dummyonlineshop.profile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.post.ProfileBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfilePresenter implements ProfileContract.Presenter {

    private Session session;
    private Call<Base> callAddProfile;
    private OnlineShopService service;
    private final ProfileContract.View mView;

    public ProfilePresenter(@NonNull ProfileContract.View mainView, Context context) {
        mView = checkNotNull(mainView, "MainView cannot be null!");
        mView.setPresenter(this);
        session = new Session(context);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);
    }

    public void submit(String name, String number) {
        ProfileBody loginBody = new ProfileBody();
        loginBody.setName(name);
        loginBody.setNumber(number);

        callAddProfile = service.addProfile("Bearer " + session.getToken(), loginBody);
        callAddProfile.enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                if (response.isSuccessful()) {
                    Base data = response.body();
                    boolean status = (data.getCode() == 200);
                    mView.response(status);
                }
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                mView.response(false);
            }
        });
    }
}
