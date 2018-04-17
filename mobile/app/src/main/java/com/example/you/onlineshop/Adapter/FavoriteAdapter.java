package com.example.you.onlineshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private View view;
    private List<Barang> listBarang;

    public FavoriteAdapter(List<Barang> listBarang) {
        this.listBarang = listBarang;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView barang, hargaSetelahDiskon, hargaSebelumDiskon, diskon;
        private ImageView gambar;

        public ViewHolder(View view) {
            super(view);
            this.gambar = view.findViewById(R.id.gambar);
            this.barang = view.findViewById(R.id.barang);
            this.diskon = view.findViewById(R.id.diskon);
            this.hargaSebelumDiskon = view.findViewById(R.id.harga_sebelum_diskon);
            this.hargaSetelahDiskon = view.findViewById(R.id.harga_sesudah_diskon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_favorite, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Barang barang = this.listBarang.get(position);
        if (barang.getDiskon() == 0) {
            holder.diskon.setVisibility(View.INVISIBLE);
            holder.hargaSebelumDiskon.setVisibility(View.INVISIBLE);
            holder.hargaSetelahDiskon.setText("Rp." + barang.getHarga() + ",-");
        }
        else {
            int hargaDiskon = barang.getHarga() * barang.getDiskon() / 100;
            holder.diskon.setText("-" + barang.getDiskon() + "%");
            holder.hargaSebelumDiskon.setText("Rp." + barang.getHarga() + ",-");
            holder.hargaSetelahDiskon.setText("Rp." + (barang.getHarga() - hargaDiskon) + ",-");
        }

        holder.barang.setText(barang.getNama());
        Picasso.with(this.view.getContext())
                .load(barang.getLokasiFoto())
                .error(R.drawable.ic_arrow_back_black_24dp)
                .placeholder(R.drawable.ic_dashboard_black_24dp)
                .into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return this.listBarang.size();
    }
}
