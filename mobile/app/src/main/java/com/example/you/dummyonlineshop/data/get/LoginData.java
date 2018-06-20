package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData extends Base {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }
}
