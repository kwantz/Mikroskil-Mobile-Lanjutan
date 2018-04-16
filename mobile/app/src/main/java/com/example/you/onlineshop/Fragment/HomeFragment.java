package com.example.you.onlineshop.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.you.onlineshop.Adapter.HomeAdapter;
import com.example.you.onlineshop.Adapter.TopAdapter;
import com.example.you.onlineshop.Library.OnlineShopService;
import com.example.you.onlineshop.Library.RetrofitBaseUrl;
import com.example.you.onlineshop.Library.ToolbarSearch;
import com.example.you.onlineshop.MainActivity;
import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private View view;
    private int[] ads = { R.drawable.ad_1, R.drawable.ad_2, R.drawable.ad_3 };


    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        this.view = inflater.inflate(R.layout.fragment_home, container, false);

        this.setCarousel();
        this.setRecycleViewTopList();
        this.setRecycleViewRecommendList();
        return this.view;
    }

    private void setCarousel() {
        final CarouselView carouselView = view.findViewById(R.id.carousel);
        carouselView.setPageCount(ads.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(ads[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });
    }

    private void setRecycleViewTopList() {
        final RecyclerView recyclerView = view.findViewById(R.id.recycleview_top_list);

        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Barang>> call = service.getListBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()) {
                    List<Barang> data = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    RecyclerView.Adapter adapter = new TopAdapter(data);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) { }
        });
    }

    private void setRecycleViewRecommendList() {
        final RecyclerView recyclerView = view.findViewById(R.id.recycleview_recommend_list);

        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Barang>> call = service.getListBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()) {
                    List<Barang> data = response.body();

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    RecyclerView.Adapter adapter = new HomeAdapter(data);

                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) { }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search, menu);
        ((MainActivity)getActivity()).testing(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
