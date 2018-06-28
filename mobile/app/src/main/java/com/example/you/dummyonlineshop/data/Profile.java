package com.example.you.dummyonlineshop.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String number;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("current_address")
    @Expose
    private int currentAddress;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(int currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }
}
