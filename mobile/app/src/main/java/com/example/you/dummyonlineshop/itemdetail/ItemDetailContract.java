package com.example.you.dummyonlineshop.itemdetail;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Item;

public interface ItemDetailContract {

    interface View extends BaseView<Presenter> {

        void showItem(Item item);

        void showErrorConnection(String tag);

        void setupBtnFavorite(boolean status);

        void setupFavorite();

        void setupAddCart();

        void setupCart(boolean status, Item item);
    }

    interface Presenter extends BasePresenter {

        void haveFavorite(int itemId);

        void getItem(int itemId);

        void deleteFavorite(int itemId);

        void addFavorite(int itemId);

        void addItemToCart(int item, String qty, String note);

        void haveItemInCart(Item item);
    }
}
