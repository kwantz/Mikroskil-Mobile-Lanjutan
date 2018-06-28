package com.example.you.dummyonlineshop.service;

import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.get.AddressData;
import com.example.you.dummyonlineshop.data.get.AddressDetailData;
import com.example.you.dummyonlineshop.data.get.CartData;
import com.example.you.dummyonlineshop.data.get.FavoriteData;
import com.example.you.dummyonlineshop.data.get.ItemData;
import com.example.you.dummyonlineshop.data.get.LoginData;
import com.example.you.dummyonlineshop.data.get.ProfileData;
import com.example.you.dummyonlineshop.data.get.TransactionData;
import com.example.you.dummyonlineshop.data.get.TransactionDetailData;
import com.example.you.dummyonlineshop.data.post.AddressBody;
import com.example.you.dummyonlineshop.data.post.CartBody;
import com.example.you.dummyonlineshop.data.post.FavoriteBody;
import com.example.you.dummyonlineshop.data.post.LoginBody;
import com.example.you.dummyonlineshop.data.post.ProfileBody;
import com.example.you.dummyonlineshop.data.post.RegisterBody;
import com.example.you.dummyonlineshop.data.post.TokenBody;
import com.example.you.dummyonlineshop.data.get.CategoriesData;
import com.example.you.dummyonlineshop.data.get.ItemsData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OnlineShopService {

    /**
     * Item API Services
     */

    @GET("/item?title=new-items")
    Call<ItemsData> getNewItems();

    @GET("/item?title=hot-deal-items")
    Call<ItemsData> getHotDealItems();

    @GET("/item?title=recommended-items")
    Call<ItemsData> getRecommendedItems();

    @GET("/item")
    Call<ItemsData> getSearchItems(@Query("search") String search);

    @GET("/item")
    Call<ItemsData> getSearchCategoryItems(@Query("category") String search);

    @GET("/item/{id}")
    Call<ItemData> getDetailItem(@Path("id") int itemId);


    /**
     * Category API Services
     */

    @GET("/category")
    Call<CategoriesData> getCategories();


    /**
     * Favorite API Services
     */

    @GET("/favorite")
    Call<ItemsData> getFavorite(@Header("Authorization") String auth);

    @GET("/favorite/{item}")
    Call<FavoriteData> haveFavorite(@Header("Authorization") String auth, @Path("item") int itemId);

    @POST("/favorite")
    Call<Base> addFavorite(@Header("Authorization") String auth, @Body FavoriteBody favoriteBody);

    @HTTP(method = "DELETE", path = "/favorite", hasBody = true)
    Call<Base> deleteFavorite(@Header("Authorization") String auth, @Body FavoriteBody favoriteBody);


    /**
     *  Cart API Services
     */

    @GET("/cart")
    Call<CartData> getAllItemInCart(@Header("Authorization") String auth);

    @GET("/cart/{item}")
    Call<Base> haveItemInCart(@Header("Authorization") String auth, @Path("item") int itemId);

    @POST("/cart")
    Call<Base> addToCart(@Header("Authorization") String auth, @Body CartBody cartBody);

    @PUT("/cart")
    Call<Base> updateItemInCart(@Header("Authorization") String auth, @Body CartBody cartBody);

    @HTTP(method = "DELETE", path = "/cart/{item}", hasBody = true)
    Call<Base> deleteItemInCart(@Header("Authorization") String auth, @Path("item") int itemId);


    /**
     * Security API Services
     */

    @POST("/check")
    Call<Base> checkToken(@Body TokenBody tokenBody);

    @POST("/login")
    Call<LoginData> login(@Body LoginBody loginBody);

    @POST("/register")
    Call<Base> register(@Body RegisterBody registerBody);


    /**
     * Profile API Services
     */

    @POST("/profile")
    Call<Base> addProfile(@Header("Authorization") String auth, @Body ProfileBody profileBody);

    @GET("/profile")
    Call<ProfileData> detailProfile(@Header("Authorization") String auth);

    @GET("/profile/address/{address}")
    Call<Base> setCurrentAddress (@Header("Authorization") String auth, @Path("address") int addressId);


    /**
     * Address API Service
     */

    @POST("/address")
    Call<Base> addAddress(@Header("Authorization") String auth, @Body AddressBody addressBody);

    @GET("/address/{address}")
    Call<AddressDetailData> getAddressDetail(@Header("Authorization") String auth, @Path("address") int addressId);

    @GET("/address")
    Call<AddressData> getAddress(@Header("Authorization") String auth);


    /**
     * Transaction API Service
     */

    @GET("/transaction")
    Call<TransactionData> getTransaction(@Header("Authorization") String auth);

    @GET("/transaction/{transaction}")
    Call<TransactionDetailData> getTransactionDetail(@Header("Authorization") String auth, @Path("transaction") int transactionId);

    @POST("/transaction")
    Call<TransactionDetailData> addTransaction(@Header("Authorization") String auth);
}
