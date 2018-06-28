package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Address;
import com.example.you.dummyonlineshop.data.Base;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDetailData extends Base {

    @SerializedName("results")
    @Expose
    private Address results;

    public Address getResults() {
        return results;
    }

}
