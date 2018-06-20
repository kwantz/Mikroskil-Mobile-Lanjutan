package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemData extends Base {

    @SerializedName("item")
    @Expose
    private Item item;

    public Item getItem() {
        return this.item;
    }
}
