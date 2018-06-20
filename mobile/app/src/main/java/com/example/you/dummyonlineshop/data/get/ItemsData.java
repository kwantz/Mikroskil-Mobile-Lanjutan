package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsData extends Base {

    @SerializedName("items")
    @Expose
    private List<Item> items;

    public List<Item> getItems() {
        return this.items;
    }
}
