package com.example.you.dummyonlineshop.searchcategory;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Item;

import java.util.List;

public interface SearchCategoryContract {

    interface View extends BaseView<Presenter> {

        void showItems(List<Item> items);

        void showErrorConnection(String tag);
    }

    interface Presenter extends BasePresenter {

        void getSearchItem(String search);
    }
}
