package com.example.you.dummyonlineshop.splashscreen;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;

public interface SplashScreenContract {

    interface View extends BaseView<Presenter> {

        void changeActivity(boolean status);
    }

    interface Presenter extends BasePresenter {

        void checkToken(String token);
    }
}
