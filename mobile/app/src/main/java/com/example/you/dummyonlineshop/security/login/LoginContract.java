package com.example.you.dummyonlineshop.security.login;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loginResponse(String message, String token);
    }

    interface Presenter extends BasePresenter {

        void submit(String username, String password);
    }
}
