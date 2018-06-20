package com.example.you.dummyonlineshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.post.FavoriteBody;
import com.example.you.dummyonlineshop.itemdetail.ItemDetailActivity;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private View view;
    private List<Item> listBarang;

    public FavoriteAdapter(List<Item> listBarang) {
        this.listBarang = listBarang;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView gambar;
        private TextView barang;
        private TextView harga;
        private TextView kategori;
        private ImageView btnFavorite;

        public ViewHolder(View view) {
            super(view);
            this.gambar = view.findViewById(R.id.item_picture);
            this.barang = view.findViewById(R.id.item_name);
            this.harga = view.findViewById(R.id.item_price);
            this.kategori = view.findViewById(R.id.category_name);
            this.btnFavorite = view.findViewById(R.id.btn_favorite);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item barang = this.listBarang.get(position);

        holder.harga.setText("Rp." + barang.getPrice() + ",-");
        holder.barang.setText(barang.getName());
        holder.kategori.setText(barang.getCategory().getName());
        Picasso.with(this.view.getContext())
            .load(barang.getPicture())
            .error(R.drawable.ic_do_not_disturb_alt_24dp)
            .placeholder(R.drawable.ic_autorenew_24dp)
            .into(holder.gambar);

        view.setOnClickListener((View v) -> {
            Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
            intent.putExtra("ITEM_ID", barang.getId());
            view.getContext().startActivity(intent);
        });

        holder.btnFavorite.setOnClickListener((View v) -> {
            Log.e("BTN Favorite", barang.getName());
            Session session = new Session(view.getContext());
            Retrofit retrofit = RetrofitLibrary.getRetrofit();
            OnlineShopService service = retrofit.create(OnlineShopService.class);

            View loadingView = ((Activity) view.getContext()).getLayoutInflater().inflate(R.layout.alert_loading, null);
            AlertDialog loadingAlertDialog = new AlertDialog.Builder(view.getContext())
                .setView(loadingView)
                .create();

            loadingAlertDialog.show();

            FavoriteBody favoriteBody = new FavoriteBody();
            favoriteBody.setItem(barang.getId());
            service.deleteFavorite("Bearer " + session.getToken(), favoriteBody).enqueue(new Callback<Base>() {
                @Override
                public void onResponse(Call<Base> call, Response<Base> response) {
                    Log.e("Message", response.body().getMessage());
                    listBarang.remove(position);
                    notifyDataSetChanged();
                    loadingAlertDialog.cancel();
                }

                @Override
                public void onFailure(Call<Base> call, Throwable t) {
                    loadingAlertDialog.cancel();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return this.listBarang.size();
    }
}
