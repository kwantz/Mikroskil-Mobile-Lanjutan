package com.example.you.dummyonlineshop.data.get;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesData extends Base {

    @SerializedName("categories")
    @Expose
    private List<Category> categories;

    public List<Category> getCategories() {
        return this.categories;
    }
}
