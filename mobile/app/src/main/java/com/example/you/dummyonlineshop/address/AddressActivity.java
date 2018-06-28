package com.example.you.dummyonlineshop.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.AddressAdapter;
import com.example.you.dummyonlineshop.adapter.OrderAdapter;
import com.example.you.dummyonlineshop.addaddress.AddAddressActivity;
import com.example.you.dummyonlineshop.data.Address;
import com.example.you.dummyonlineshop.data.get.AddressData;
import com.example.you.dummyonlineshop.data.get.ProfileData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddressActivity extends AppCompatActivity {

    private Session session;
    private OnlineShopService service;
    private View viewError;
    private View viewLoading;
    private RecyclerView rvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        viewError = findViewById(R.id.content_error);
        viewLoading = findViewById(R.id.content_loading);
        rvAddress = findViewById(R.id.rv_list_address);

        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);

        session = new Session(this);

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener((View v) -> onBackPressed());

        ImageView btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener((View v) -> {
            Intent intent = new Intent(getBaseContext(), AddAddressActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected  void onResume() {
        super.onResume();

        viewLoading.setVisibility(View.VISIBLE);
        viewError.setVisibility(View.GONE);
        rvAddress.setVisibility(View.GONE);
        service.getAddress("Bearer " + session.getToken()).enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                if (response.isSuccessful()) {
                    List<Address> addressList = response.body().getResults();

                    if (addressList.size() == 0) {
                        Intent intent = new Intent(getBaseContext(), AddAddressActivity.class);
                        startActivity(intent);
                    } else {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView.Adapter adapter = new AddressAdapter(addressList, session);

                        rvAddress.setLayoutManager(layoutManager);
                        rvAddress.setAdapter(adapter);
                        viewLoading.setVisibility(View.GONE);
                        viewError.setVisibility(View.GONE);
                        rvAddress.setVisibility(View.VISIBLE);
                    }
                } else {
                    viewLoading.setVisibility(View.GONE);
                    viewError.setVisibility(View.VISIBLE);
                    rvAddress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Log.e("Error", t.toString());
                viewLoading.setVisibility(View.GONE);
                viewError.setVisibility(View.VISIBLE);
                rvAddress.setVisibility(View.GONE);
            }
        });
    }
}
