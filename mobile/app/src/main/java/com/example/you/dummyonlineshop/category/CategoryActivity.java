package com.example.you.dummyonlineshop.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.AllCategoryAdapter;
import com.example.you.dummyonlineshop.data.get.CategoriesData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Session session = new Session(getBaseContext());

        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        service.getCategories().enqueue(new Callback<CategoriesData>() {
            @Override
            public void onResponse(Call<CategoriesData> call, Response<CategoriesData> response) {
                RecyclerView rvCategories = findViewById(R.id.categories);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                RecyclerView.Adapter adapter = new AllCategoryAdapter(response.body().getCategories());

                rvCategories.setLayoutManager(layoutManager);
                rvCategories.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CategoriesData> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener((View view) -> onBackPressed());
    }
}
