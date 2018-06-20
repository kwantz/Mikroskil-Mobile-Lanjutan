package com.example.you.dummyonlineshop.profile;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void response(boolean status);
    }

    interface Presenter extends BasePresenter {

        void submit(String name, String number);
    }
}
