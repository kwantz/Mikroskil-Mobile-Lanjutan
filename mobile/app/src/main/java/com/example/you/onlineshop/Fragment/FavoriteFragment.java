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

import com.example.you.onlineshop.Adapter.FavoriteAdapter;
import com.example.you.onlineshop.Adapter.TopItemAdapter;
import com.example.you.onlineshop.Library.OnlineShopService;
import com.example.you.onlineshop.Library.RetrofitBaseUrl;
import com.example.you.onlineshop.MainActivity;
import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoriteFragment extends Fragment {

    private int VIEW_SUCCESS = 1;
    private int VIEW_PENDING = 0;
    private int VIEW_ERROR = -1;

    private int isFavoriteFinished = VIEW_PENDING;
    private int isTopItemFinished = VIEW_PENDING;

    private View view;
    private RecyclerView rvTopItem, rvFavoriteItem;
    private LinearLayout viewContent, viewLoading, viewError, contentFavoriteList, contentFavoriteEmpty;

    public FavoriteFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        this.view = inflater.inflate(R.layout.fragment_favorite, container, false);
        this.rvTopItem = this.view.findViewById(R.id.rv_top_item);
        this.rvFavoriteItem = this.view.findViewById(R.id.rv_favorite_item);
        this.viewContent = this.view.findViewById(R.id.content_success);
        this.viewLoading = this.view.findViewById(R.id.content_loading);
        this.viewError = this.view.findViewById(R.id.content_error);
        this.contentFavoriteList = this.view.findViewById(R.id.content_favorite_list);
        this.contentFavoriteEmpty = this.view.findViewById(R.id.content_favorite_empty);

        this.loadView();
        this.setRecycleViewFavoriteItem();
        this.setRecycleViewTopItem();
        return this.view;
    }

    private void setRecycleViewFavoriteItem() {
        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Barang>> call = service.getListBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()) {
                    List<Barang> data = response.body();

                    if (data.size() != 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView.Adapter adapter = new FavoriteAdapter(data);

                        rvFavoriteItem.setLayoutManager(layoutManager);
                        rvFavoriteItem.setAdapter(adapter);
                        rvFavoriteItem.setNestedScrollingEnabled(false);

                        contentFavoriteEmpty.setVisibility(View.GONE);
                        contentFavoriteList.setVisibility(View.VISIBLE);
                    }
                    else {
                        contentFavoriteEmpty.setVisibility(View.VISIBLE);
                        contentFavoriteList.setVisibility(View.GONE);
                    }

                    isFavoriteFinished = VIEW_SUCCESS;
                    loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                isFavoriteFinished = VIEW_ERROR;
                loadView();
            }
        });
    }

    private void setRecycleViewTopItem() {
        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Barang>> call = service.getListBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()) {
                    List<Barang> data = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    RecyclerView.Adapter adapter = new TopItemAdapter(data);

                    rvTopItem.setLayoutManager(layoutManager);
                    rvTopItem.setAdapter(adapter);

                    isTopItemFinished = VIEW_SUCCESS;
                    loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                isTopItemFinished = VIEW_ERROR;
                loadView();
            }
        });
    }

    private void loadView() {
        if (isFavoriteFinished == VIEW_ERROR || isTopItemFinished == VIEW_ERROR) {
            this.viewError.setVisibility(View.VISIBLE);
            this.viewContent.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else if (isFavoriteFinished == VIEW_SUCCESS && isTopItemFinished == VIEW_SUCCESS) {
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
        inflater.inflate(R.menu.toolbar_search, menu);
        ((MainActivity)getActivity()).testing(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
