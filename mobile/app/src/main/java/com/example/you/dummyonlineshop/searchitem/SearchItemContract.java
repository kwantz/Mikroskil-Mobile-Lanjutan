package com.example.you.dummyonlineshop.searchitem;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Item;

import java.util.List;
import java.util.Set;

public interface SearchItemContract {

    interface View extends BaseView<Presenter> {

        void showItems(List<Item> items);

        void showRecommendedItems(List<Item> items);

        void showErrorConnection(String tag);
    }

    interface Presenter extends BasePresenter {

        void getSearchItem(String search);

        void getRecommendedItem();
    }
}
