package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Address;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressData {

    @SerializedName("results")
    @Expose
    private List<Address> results;

    public List<Address> getResults() {
        return results;
    }
}
