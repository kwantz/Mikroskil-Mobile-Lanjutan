package com.example.you.dummyonlineshop.data.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartBody {

    @SerializedName("item")
    @Expose
    private int item;

    @SerializedName("qty")
    @Expose
    private int qty;

    @SerializedName("note")
    @Expose
    private String note;

    public void setItem(int item) {
        this.item = item;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
