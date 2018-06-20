package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteData extends Base {

    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    public boolean getFavorite() {
        return this.favorite;
    }
}
