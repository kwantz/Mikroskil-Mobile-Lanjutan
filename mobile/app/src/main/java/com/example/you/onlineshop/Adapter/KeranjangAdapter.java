package com.example.you.onlineshop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.onlineshop.Model.Barang;
import com.example.you.onlineshop.Model.Keranjang;
import com.example.you.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    private View view;
    private List<Keranjang> listKeranjang;

    public KeranjangAdapter(List<Keranjang> listKeranjang) {
        this.listKeranjang = listKeranjang;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView qty, barang, harga, hargaTotal, catatan;
        private ImageView gambar;

        public ViewHolder(View view) {
            super(view);
            this.qty = view.findViewById(R.id.qty);
            this.harga = view.findViewById(R.id.harga);
            this.gambar = view.findViewById(R.id.gambar);
            this.barang = view.findViewById(R.id.barang);
            this.catatan = view.findViewById(R.id.catatan);
            this.hargaTotal = view.findViewById(R.id.harga_total);
        }
    }

    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_keranjang, parent, false);
        KeranjangAdapter.ViewHolder viewHolder = new KeranjangAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KeranjangAdapter.ViewHolder holder, final int position) {
        Keranjang keranjang = this.listKeranjang.get(position);
        Barang barang = keranjang.getBarang();

        int hargaDiskon = barang.getHarga() * barang.getDiskon() / 100;

        holder.harga.setText("Rp." + ((hargaDiskon == 0) ? barang.getHarga() : (barang.getHarga() - hargaDiskon)) + ",-");
        holder.hargaTotal.setText("Rp." + ((barang.getHarga() - hargaDiskon) * keranjang.getJumlah()) + ",-");

        holder.qty.setText(keranjang.getJumlah().toString());
        holder.barang.setText(barang.getNama());
        holder.catatan.setText(keranjang.getCatatan());
        Picasso.with(this.view.getContext())
                .load(barang.getLokasiFoto())
                .error(R.drawable.ic_arrow_back_black_24dp)
                .placeholder(R.drawable.ic_dashboard_black_24dp)
                .into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return this.listKeranjang.size();
    }
}
