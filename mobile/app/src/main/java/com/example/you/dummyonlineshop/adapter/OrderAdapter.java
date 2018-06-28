package com.example.you.dummyonlineshop.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.edititemcart.EditItemCartActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private View view;
    private List<Cart> listKeranjang;

    public OrderAdapter(List<Cart> listKeranjang) {
        this.listKeranjang = listKeranjang;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView qty;
        private TextView barang;
        private TextView harga;
        private TextView hargaTotal;
        private TextView catatan;
        private ImageView gambar;

        public ViewHolder(View view) {
            super(view);
            this.qty = view.findViewById(R.id.input_qty);
            this.harga = view.findViewById(R.id.item_price);
            this.gambar = view.findViewById(R.id.item_picture);
            this.barang = view.findViewById(R.id.item_name);
            this.catatan = view.findViewById(R.id.input_note);
            this.hargaTotal = view.findViewById(R.id.price_total);
        }
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_order, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, final int position) {
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
    }

    @Override
    public int getItemCount() {
        return this.listKeranjang.size();
    }
}
