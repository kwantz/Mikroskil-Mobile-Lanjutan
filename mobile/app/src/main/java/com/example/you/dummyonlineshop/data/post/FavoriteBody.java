package com.example.you.dummyonlineshop.data.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteBody {

    @SerializedName("item")
    @Expose
    private int item;

    public void setItem(int item) {
        this.item = item;
    }
}
