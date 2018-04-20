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
import com.example.you.onlineshop.Library.DisplayView;
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

    private final String TOP_ITEM_TAG = "topItem";
    private final String FAVORITE_TAG = "favorite";

    private View view;
    private RecyclerView rvTopItem, rvFavoriteItem;
    private LinearLayout contentFavoriteList, contentFavoriteEmpty;
    private DisplayView displayView;

    public FavoriteFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        initView(inflater, container);
        initDisplayView();

        setRecycleViewFavoriteItem();
        setRecycleViewTopItem();

        return view;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvTopItem = view.findViewById(R.id.rv_top_item);
        rvFavoriteItem = view.findViewById(R.id.rv_favorite_item);
        contentFavoriteList = view.findViewById(R.id.content_favorite_list);
        contentFavoriteEmpty = view.findViewById(R.id.content_favorite_empty);
    }

    private void initDisplayView() {
        displayView = new DisplayView(view);
        displayView.addRequirement(TOP_ITEM_TAG);
        displayView.addRequirement(FAVORITE_TAG);
        displayView.loadView();
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

                    displayView.successRequirement(FAVORITE_TAG);
                    displayView.loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                displayView.errorRequirement(FAVORITE_TAG);
                displayView.loadView();
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

                    displayView.successRequirement(TOP_ITEM_TAG);
                    displayView.loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                displayView.errorRequirement(TOP_ITEM_TAG);
                displayView.loadView();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search, menu);
        ((MainActivity)getActivity()).testing(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
