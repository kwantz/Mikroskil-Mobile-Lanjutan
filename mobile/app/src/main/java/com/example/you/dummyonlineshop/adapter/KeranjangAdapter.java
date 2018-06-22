package com.example.you.dummyonlineshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.post.CartBody;
import com.example.you.dummyonlineshop.edititemcart.EditItemCartActivity;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    private View view;
    private List<Cart> listKeranjang;

    public KeranjangAdapter(List<Cart> listKeranjang) {
        this.listKeranjang = listKeranjang;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView qty;
        private TextView barang;
        private TextView harga;
        private TextView hargaTotal;
        private TextView catatan;
        private ImageView gambar;
        private ImageView btnEdit;

        public ViewHolder(View view) {
            super(view);
            this.qty = view.findViewById(R.id.input_qty);
            this.harga = view.findViewById(R.id.item_price);
            this.gambar = view.findViewById(R.id.item_picture);
            this.barang = view.findViewById(R.id.item_name);
            this.catatan = view.findViewById(R.id.input_note);
            this.hargaTotal = view.findViewById(R.id.price_total);
            this.btnEdit = view.findViewById(R.id.btn_edit);
        }
    }

    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_keranjang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KeranjangAdapter.ViewHolder holder, final int position) {
        Cart keranjang = this.listKeranjang.get(position);
        Item barang = keranjang.getItem();

        String harga = "Rp." + barang.getPrice() + ",-";
        String hargaTotal = "Rp." + barang.getPrice() * keranjang.getQuantity() + ",-";
        String qty = Integer.toString(keranjang.getQuantity());

        holder.qty.setText(qty);
        holder.harga.setText(harga);
        holder.hargaTotal.setText(hargaTotal);
        holder.barang.setText(barang.getName());
        holder.catatan.setText(keranjang.getNote());

        Picasso.with(this.view.getContext())
            .load(barang.getPicture())
            .error(R.drawable.ic_do_not_disturb_alt_24dp)
            .placeholder(R.drawable.ic_autorenew_24dp)
            .into(holder.gambar);

        holder.btnEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(view.getContext(), EditItemCartActivity.class);
            intent.putExtra("item_id", barang.getId());
            intent.putExtra("item_price", barang.getPrice());
            intent.putExtra("item_name", barang.getName());
            intent.putExtra("item_picture", barang.getPicture());
            intent.putExtra("item_qty", qty);
            intent.putExtra("item_note", keranjang.getNote());

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.listKeranjang.size();
    }
}
