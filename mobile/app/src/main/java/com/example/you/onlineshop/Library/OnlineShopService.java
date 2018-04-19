package com.example.you.onlineshop.Library;

import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.Model.Keranjang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OnlineShopService {
    @GET("/kwantz/dummy/master/data.json")
    Call<List<Barang>> getListBarang();

    @GET("/kwantz/dummy/master/cart.json")
    Call<List<Keranjang>> getListKeranjang();
}
