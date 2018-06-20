package com.example.you.dummyonlineshop.data.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenBody {

    @SerializedName("token")
    @Expose
    private String token;

    public void setToken(String token) {
        this.token = token;
    }
}
