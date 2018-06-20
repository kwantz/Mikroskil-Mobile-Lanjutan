package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Profile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileData extends Base{

    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }
}
