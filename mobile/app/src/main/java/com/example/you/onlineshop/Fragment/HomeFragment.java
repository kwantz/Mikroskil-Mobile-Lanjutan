package com.example.you.onlineshop.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.you.onlineshop.Adapter.CategoryAdapter;
import com.example.you.onlineshop.Adapter.ItemAdapter;
import com.example.you.onlineshop.Adapter.NewItemAdapter;
import com.example.you.onlineshop.Adapter.TopItemAdapter;
import com.example.you.onlineshop.Library.OnlineShopService;
import com.example.you.onlineshop.Library.RetrofitBaseUrl;
import com.example.you.onlineshop.MainActivity;
import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private int VIEW_SUCCESS = 1;
    private int VIEW_PENDING = 0;
    private int VIEW_ERROR = -1;

    private int isCategoryFinished = VIEW_PENDING;
    private int isNewItemFinished = VIEW_PENDING;
    private int isTopItemFinished = VIEW_PENDING;
    private int isRecommendedFinished = VIEW_PENDING;

    private View view;
    private CarouselView carousel;
    private LinearLayout viewContent, viewLoading, viewError;
    private RecyclerView rvCategory, rvNewItem, rvRecommended, rvTopItem;

    private int[] ads = { R.drawable.ad_1, R.drawable.ad_2, R.drawable.ad_3 };

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        this.carousel = view.findViewById(R.id.carousel);;
        this.rvCategory = view.findViewById(R.id.rv_category);
        this.rvNewItem = view.findViewById(R.id.rv_new_item);
        this.rvTopItem = view.findViewById(R.id.rv_top_item);
        this.rvRecommended = view.findViewById(R.id.rv_recommended);
        this.viewContent = view.findViewById(R.id.content_success);
        this.viewLoading = view.findViewById(R.id.content_loading);
        this.viewError = view.findViewById(R.id.content_error);

        this.loadView();
        this.setCarousel();
        this.setRecycleViewCategory();
        this.setRecycleViewNewItem();
        this.setRecycleViewTopItem();
        this.setRecycleViewRecommendedList();
        return this.view;
    }

    private void setCarousel() {
        this.carousel.setPageCount(ads.length);
        this.carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(ads[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });
    }

    private void setRecycleViewCategory() {
        List<String> listCategory = new ArrayList<>();
        listCategory.add("Fashion Wanita");
        listCategory.add("Fashion Pria");
        listCategory.add("Fashion Muslim");
        listCategory.add("Fashion Anak");
        listCategory.add("Kecantikan");
        listCategory.add("Kesehatan");
        listCategory.add("Perawatan Tubuh");
        listCategory.add("Handphone & Tablet");
        listCategory.add("Laptop & Aksesoris");
        listCategory.add("Komputer & Aksesoris");
        listCategory.add("Rumah Tangga");
        listCategory.add("Makanan & Minuman");

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        RecyclerView.Adapter adapter = new CategoryAdapter(listCategory);

        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(adapter);
        rvCategory.setNestedScrollingEnabled(false);

        isCategoryFinished = VIEW_SUCCESS;
        loadView();

        // Siap sedia untuk API nanti
//        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
//        OnlineShopService service = retrofit.create(OnlineShopService.class);
//
//        Call<List<Barang>> call = service.getListBarang();
//        call.enqueue(new Callback<List<Barang>>() {
//            @Override
//            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
//                if (response.isSuccessful()) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Barang>> call, Throwable t) { }
//        });
    }

    private void setRecycleViewNewItem() {
        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Barang>> call = service.getListBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()) {
                    List<Barang> data = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    RecyclerView.Adapter adapter = new NewItemAdapter(data);

                    rvNewItem.setLayoutManager(layoutManager);
                    rvNewItem.setAdapter(adapter);

                    isNewItemFinished = VIEW_SUCCESS;
                    loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                isNewItemFinished = VIEW_ERROR;
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

    private void setRecycleViewRecommendedList() {
        Retrofit retrofit = new RetrofitBaseUrl().getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        Call<List<Barang>> call = service.getListBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()) {
                    List<Barang> data = response.body();

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    RecyclerView.Adapter adapter = new ItemAdapter(data);

                    rvRecommended.setLayoutManager(gridLayoutManager);
                    rvRecommended.setAdapter(adapter);
                    rvRecommended.setNestedScrollingEnabled(false);

                    isRecommendedFinished = VIEW_SUCCESS;
                    loadView();
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                isRecommendedFinished = VIEW_ERROR;
                loadView();
            }
        });
    }

    private void loadView() {
        if (isCategoryFinished == VIEW_ERROR || isNewItemFinished == VIEW_ERROR || isTopItemFinished == VIEW_ERROR || isRecommendedFinished == VIEW_ERROR) {
            this.viewError.setVisibility(View.VISIBLE);
            this.viewContent.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else if (isCategoryFinished == VIEW_SUCCESS && isNewItemFinished == VIEW_SUCCESS && isTopItemFinished == VIEW_SUCCESS && isRecommendedFinished == VIEW_SUCCESS) {
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
