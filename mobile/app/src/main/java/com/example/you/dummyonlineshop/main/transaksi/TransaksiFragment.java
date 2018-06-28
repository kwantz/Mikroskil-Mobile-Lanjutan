package com.example.you.dummyonlineshop.main.transaksi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.TransactionAdapter;
import com.example.you.dummyonlineshop.data.get.TransactionData;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransaksiFragment extends Fragment {

    private View view;
    private Session session;
    private OnlineShopService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        View viewError = view.findViewById(R.id.content_error);
        View viewLoading = view.findViewById(R.id.content_loading);
        View viewEmpty = view.findViewById(R.id.empty);
        RecyclerView rvTransaction = view.findViewById(R.id.rv_transaction);

        session = new Session(getContext());
        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        service = retrofit.create(OnlineShopService.class);

        viewLoading.setVisibility(View.VISIBLE);
        viewError.setVisibility(View.GONE);
        rvTransaction.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.GONE);

        service.getTransaction("Bearer " + session.getToken()).enqueue(new Callback<TransactionData>() {
            @Override
            public void onResponse(Call<TransactionData> call, Response<TransactionData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResults().size() != 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView.Adapter adapter = new TransactionAdapter(response.body().getResults());

                        rvTransaction.setLayoutManager(layoutManager);
                        rvTransaction.setAdapter(adapter);

                        rvTransaction.setVisibility(View.VISIBLE);
                        viewLoading.setVisibility(View.GONE);
                        viewError.setVisibility(View.GONE);
                        viewEmpty.setVisibility(View.GONE);
                    } else {
                        rvTransaction.setVisibility(View.GONE);
                        viewLoading.setVisibility(View.GONE);
                        viewError.setVisibility(View.GONE);
                        viewEmpty.setVisibility(View.VISIBLE);
                    }
                } else {
                    rvTransaction.setVisibility(View.GONE);
                    viewLoading.setVisibility(View.GONE);
                    viewError.setVisibility(View.VISIBLE);
                    viewEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TransactionData> call, Throwable t) {
                Log.e("Error", t.toString());
                rvTransaction.setVisibility(View.GONE);
                viewLoading.setVisibility(View.GONE);
                viewError.setVisibility(View.VISIBLE);
                viewEmpty.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
