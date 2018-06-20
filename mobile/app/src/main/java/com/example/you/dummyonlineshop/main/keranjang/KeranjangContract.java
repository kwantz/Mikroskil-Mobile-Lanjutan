package com.example.you.dummyonlineshop.main.keranjang;

import com.example.you.dummyonlineshop.BasePresenter;
import com.example.you.dummyonlineshop.BaseView;
import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.Item;

import java.util.List;

public interface KeranjangContract {

    interface View extends BaseView<Presenter> {

        void showRecycleViewKeranjang(List<Cart> data);

        void showErrorConnection(String tag);
    }

    interface Presenter extends BasePresenter {

        void setupKeranjang();
    }
}
