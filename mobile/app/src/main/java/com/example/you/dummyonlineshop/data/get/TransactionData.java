package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Profile;
import com.example.you.dummyonlineshop.data.Transaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionData extends Base {

    @SerializedName("results")
    @Expose
    private List<Transaction> results;

    public List<Transaction> getResults() {
        return results;
    }
}
