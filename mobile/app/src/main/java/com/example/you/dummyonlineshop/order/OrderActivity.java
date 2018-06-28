package com.example.you.dummyonlineshop.order;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.NewItemAdapter;
import com.example.you.dummyonlineshop.adapter.OrderAdapter;
import com.example.you.dummyonlineshop.address.AddressActivity;
import com.example.you.dummyonlineshop.data.Address;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.get.AddressDetailData;
import com.example.you.dummyonlineshop.data.get.CartData;
import com.example.you.dummyonlineshop.data.get.ProfileData;
import com.example.you.dummyonlineshop.data.get.TransactionDetailData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;
import com.example.you.dummyonlineshop.transaction.TransactionActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderActivity extends AppCompatActivity {

    private Session session;
    private OnlineShopService service;
    private View viewError;
    private View viewLoading;
    private View viewSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        viewError = findViewById(R.id.content_error);
        viewLoading = findViewById(R.id.content_loading);
        viewSuccess = findViewById(R.id.content_success);

        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);

        session = new Session(this);


        View loadingView = getLayoutInflater().inflate(R.layout.alert_loading, null);
        AlertDialog loadingAlertDialog = new AlertDialog.Builder(this)
            .setView(loadingView)
            .create();

        Button btnTransaksi = findViewById(R.id.btn_transaksi);
        btnTransaksi.setOnClickListener((View v) -> {
            loadingAlertDialog.show();
            service.addTransaction("Bearer " + session.getToken()).enqueue(new Callback<TransactionDetailData>() {
                @Override
                public void onResponse(Call<TransactionDetailData> call, Response<TransactionDetailData> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(getBaseContext(), TransactionActivity.class);
                        intent.putExtra("transaction_id", response.body().getResults().getId());
                        startActivity(intent);
                        finish();
                    }
                    loadingAlertDialog.cancel();
                }

                @Override
                public void onFailure(Call<TransactionDetailData> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        });

        TextView btnAddres = findViewById(R.id.btn_address);
        btnAddres.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        });

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener((View v) -> onBackPressed());
    }

    @Override
    protected  void onResume() {
        super.onResume();

        viewError.setVisibility(View.GONE);
        viewLoading.setVisibility(View.VISIBLE);
        viewSuccess.setVisibility(View.GONE);

        service.detailProfile("Bearer " + session.getToken()).enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getProfile().getCurrentAddress() == 0) {
                        Intent intent = new Intent(getBaseContext(), AddressActivity.class);
                        startActivity(intent);
                    } else {
                        getAddress(response.body().getProfile().getCurrentAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });


        service.getAllItemInCart("Bearer " + session.getToken()).enqueue(new Callback<CartData>() {
            @Override
            public void onResponse(Call<CartData> call, Response<CartData> response) {
                if (response.isSuccessful()) {
                    List<Cart> carts = response.body().getCarts();

                    int temp = 0;
                    for (Cart cart : carts) {
                        temp += (cart.getQuantity() * cart.getItem().getPrice());
                    }

                    TextView totalPrice = findViewById(R.id.total_price);
                    totalPrice.setText("Rp." + temp + ",-");

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                    RecyclerView.Adapter adapter = new OrderAdapter(carts);

                    RecyclerView rvOrder = findViewById(R.id.rv_order);
                    rvOrder.setLayoutManager(layoutManager);
                    rvOrder.setAdapter(adapter);

                    viewError.setVisibility(View.GONE);
                    viewLoading.setVisibility(View.GONE);
                    viewSuccess.setVisibility(View.VISIBLE);
                } else {
                    viewError.setVisibility(View.VISIBLE);
                    viewLoading.setVisibility(View.GONE);
                    viewSuccess.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CartData> call, Throwable t) {
                Log.e("Error", t.toString());
                viewError.setVisibility(View.VISIBLE);
                viewLoading.setVisibility(View.GONE);
                viewSuccess.setVisibility(View.GONE);
            }
        });
    }

    private void getAddress(int address) {
        service.getAddressDetail("Bearer " + session.getToken(), address).enqueue(new Callback<AddressDetailData>() {
            @Override
            public void onResponse(Call<AddressDetailData> call, Response<AddressDetailData> response) {
                if (response.isSuccessful()) {
                    Address address1 = response.body().getResults();

                    TextView addressName = findViewById(R.id.address);
                    TextView profileName = findViewById(R.id.name);
                    TextView profilePhone = findViewById(R.id.number);
                    TextView addressCity = findViewById(R.id.city);
                    TextView addressPoscode = findViewById(R.id.poscode);

                    addressName.setText(address1.getName());
                    profileName.setText(address1.getUserName());
                    profilePhone.setText(address1.getUserPhone());
                    addressCity.setText(address1.getCity());
                    addressPoscode.setText(address1.getPostcode());

                    viewError.setVisibility(View.GONE);
                    viewLoading.setVisibility(View.GONE);
                    viewSuccess.setVisibility(View.VISIBLE);
                } else {
                    viewError.setVisibility(View.VISIBLE);
                    viewLoading.setVisibility(View.GONE);
                    viewSuccess.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddressDetailData> call, Throwable t) {
                Log.e("Error", t.toString());
                viewError.setVisibility(View.VISIBLE);
                viewLoading.setVisibility(View.GONE);
                viewSuccess.setVisibility(View.GONE);
            }
        });
    }
}