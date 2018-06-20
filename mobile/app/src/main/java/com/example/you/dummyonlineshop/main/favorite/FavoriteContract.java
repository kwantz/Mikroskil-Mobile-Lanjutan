package com.example.you.dummyonlineshop.main.favorite;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Item;

import java.util.List;

public interface FavoriteContract {

    interface View extends BaseView<Presenter> {

        void showRecycleViewTopItem(List<Item> data);

        void showRecycleViewFavoriteItem(List<Item> data);

        void showErrorConnection(String tag);
    }

    interface Presenter extends BasePresenter {

        void setupTopItem();

        void setupFavoriteItem();
    }
}
