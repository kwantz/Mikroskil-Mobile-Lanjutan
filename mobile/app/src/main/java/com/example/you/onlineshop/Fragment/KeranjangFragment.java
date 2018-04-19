package com.example.you.onlineshop.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.you.onlineshop.Adapter.KeranjangAdapter;
import com.example.you.onlineshop.Adapter.NewItemAdapter;
import com.example.you.onlineshop.Library.OnlineShopService;
import com.example.you.onlineshop.Library.RetrofitBaseUrl;
import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.Model.Keranjang;
import com.example.you.onlineshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KeranjangFragment extends Fragment {

    private int VIEW_SUCCESS = 1;
    private int VIEW_PENDING = 0;
    private int VIEW_ERROR = -1;

    private int isKeranjangFinished = VIEW_PENDING;

    private View view;
    private RecyclerView rvKeranjang;
    private LinearLayout viewContent, viewLoading, viewError, contentKeranjangList, contentKeranjangEmpty;

    public KeranjangFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        this.view = inflater.inflate(R.layout.fragment_keranjang, container, false);
        this.rvKeranjang = view.findViewById(R.id.rv_keranjang);
        this.viewContent = view.findViewById(R.id.content_success);
        this.viewLoading = view.findViewById(R.id.content_loading);
        this.viewError = view.findViewById(R.id.content_error);
        this.contentKeranjangList = view.findViewById(R.id.keranjang_list);
        this.contentKeranjangEmpty = view.findViewById(R.id.keranjang_empty);

        this.loadView();
        this.setRecycleViewKeranjang();

        return this.view;
    }

    private void setRecycleViewKeranjang() {
        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Keranjang>> call = service.getListKeranjang();
        call.enqueue(new Callback<List<Keranjang>>() {
            @Override
            public void onResponse(Call<List<Keranjang>> call, Response<List<Keranjang>> response) {
                if (response.isSuccessful()) {
                    List<Keranjang> data = response.body();

                    if (data.size() != 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView.Adapter adapter = new KeranjangAdapter(data);

                        rvKeranjang.setLayoutManager(layoutManager);
                        rvKeranjang.setAdapter(adapter);

                        contentKeranjangList.setVisibility(View.VISIBLE);
                        contentKeranjangEmpty.setVisibility(View.GONE);
                    }
                    else {
                        contentKeranjangList.setVisibility(View.GONE);
                        contentKeranjangEmpty.setVisibility(View.VISIBLE);
                    }

                    isKeranjangFinished = VIEW_SUCCESS;
                    loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Keranjang>> call, Throwable t) {
                isKeranjangFinished = VIEW_ERROR;
                loadView();
            }
        });
    }

    private void loadView() {
        if (isKeranjangFinished == VIEW_ERROR) {
            this.viewError.setVisibility(View.VISIBLE);
            this.viewContent.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else if (isKeranjangFinished == VIEW_SUCCESS) {
            this.viewError.setVisibility(View.GONE);
            this.viewContent.setVisibility(View.VISIBLE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else {
            this.viewError.setVisibility(View.GONE);
            this.viewContent.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
