package com.example.you.dummyonlineshop.main.home;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Category;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.Kategori;

import java.util.List;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showRecycleViewCategory(List<Category> data);

        void showRecycleViewNewItem(List<Item> data);

        void showRecycleViewTopItem(List<Item> data);

        void showRecycleViewRecommendedItem(List<Item> data);

        void showErrorConnection(String tag);
    }

    interface Presenter extends BasePresenter {

        void setupCategory();

        void setupNewItem();

        void setupTopItem();

        void setupRecommendedItem();
    }
}
