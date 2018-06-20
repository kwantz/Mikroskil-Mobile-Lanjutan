package com.example.you.dummyonlineshop.security.register;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void registerResponse(String message, String token);
    }

    interface Presenter extends BasePresenter {

        void submit(String email, String username, String password);
    }
}
