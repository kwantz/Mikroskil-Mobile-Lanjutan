package com.example.you.dummyonlineshop.main.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.get.ProfileData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;


public class ProfilePresenter implements ProfileContract.Presenter {

    private Session session;
    private Call<ProfileData> callDetailProfile;
    private OnlineShopService service;
    private final ProfileContract.View mView;

    public ProfilePresenter(@NonNull ProfileContract.View mainView, Context context) {
        session = new Session(context);
        mView = checkNotNull(mainView, "MainView cannot be null!");
        mView.setPresenter(this);
    }

    public void start() {
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);

        callDetailProfile = service.detailProfile("Bearer " + session.getToken());
        callDetailProfile.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                Log.e("MEssage", response.body().getMessage());
                mView.setDisplay(response.body().getProfile());
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }
}
