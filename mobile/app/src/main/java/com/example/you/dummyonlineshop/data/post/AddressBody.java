package com.example.you.dummyonlineshop.data.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressBody {
    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("postcode")
    @Expose
    private String postcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
