package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Transaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetailData {

    @SerializedName("results")
    @Expose
    private Transaction results;

    public Transaction getResults() {
        return results;
    }
}
