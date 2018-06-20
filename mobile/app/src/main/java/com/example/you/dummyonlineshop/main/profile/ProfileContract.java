package com.example.you.dummyonlineshop.main.profile;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Profile;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void setDisplay(Profile profile);
    }

    interface Presenter extends BasePresenter {

    }
}
