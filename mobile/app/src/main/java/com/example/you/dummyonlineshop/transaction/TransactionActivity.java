package com.example.you.dummyonlineshop.transaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.OrderAdapter;
import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.Transaction;
import com.example.you.dummyonlineshop.data.get.TransactionDetailData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransactionActivity extends AppCompatActivity {

    private Session session;
    private OnlineShopService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        View viewError = findViewById(R.id.content_error);
        View viewLoading = findViewById(R.id.content_loading);
        View viewSuccess = findViewById(R.id.content_success);

        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);

        session = new Session(this);

        viewError.setVisibility(View.GONE);
        viewLoading.setVisibility(View.VISIBLE);
        viewSuccess.setVisibility(View.GONE);

        service.getTransactionDetail("Bearer " + session.getToken(), getIntent().getIntExtra("transaction_id", 0)).enqueue(new Callback<TransactionDetailData>() {
            @Override
            public void onResponse(Call<TransactionDetailData> call, Response<TransactionDetailData> response) {
                if (response.isSuccessful()) {
                    Transaction transaction = response.body().getResults();
                    TextView transactionInvoice = findViewById(R.id.transaction_invoice);
                    TextView nomorResi = findViewById(R.id.nomor_resi);
                    TextView status = findViewById(R.id.status);
                    TextView address = findViewById(R.id.address);
                    TextView number = findViewById(R.id.number);
                    TextView name = findViewById(R.id.name);
                    TextView city = findViewById(R.id.city);
                    TextView poscode = findViewById(R.id.poscode);
                    TextView totalPrice = findViewById(R.id.total_price);
                    RecyclerView rvOrder = findViewById(R.id.rv_order);

                    transactionInvoice.setText(transaction.getInvoice());
                    nomorResi.setText(transaction.getReceiptNumber());
                    status.setText(transaction.getPaymentStatus());
                    address.setText(transaction.getAddress().getName());
                    number.setText(transaction.getAddress().getUserPhone());
                    name.setText(transaction.getAddress().getUserName());
                    city.setText(transaction.getAddress().getCity());
                    poscode.setText(transaction.getAddress().getPostcode());

                    int temp = 0;
                    for (Cart cart : transaction.getCart()) {
                        temp += (cart.getQuantity() * cart.getItem().getPrice());
                    }

                    totalPrice.setText("Rp." + temp + ",-");

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                    RecyclerView.Adapter adapter = new OrderAdapter(transaction.getCart());

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
            public void onFailure(Call<TransactionDetailData> call, Throwable t) {
                Log.e("Error", t.toString());
                viewError.setVisibility(View.VISIBLE);
                viewLoading.setVisibility(View.GONE);
                viewSuccess.setVisibility(View.GONE);
            }
        });

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener((View v) -> onBackPressed());
    }
}
