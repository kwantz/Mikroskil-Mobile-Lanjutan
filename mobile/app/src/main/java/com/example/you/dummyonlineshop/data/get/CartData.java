package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartData {

    @SerializedName("carts")
    @Expose
    private List<Cart> carts;

    public List<Cart> getCarts() {
        return carts;
    }
}
