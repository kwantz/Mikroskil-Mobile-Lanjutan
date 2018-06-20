package com.example.you.dummyonlineshop.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.itemdetail.ItemDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewItemAdapter extends RecyclerView.Adapter<NewItemAdapter.ViewHolder> {

    private View view;
    private List<Item> listBarang;

    public NewItemAdapter(List<Item> listBarang) {
        this.listBarang = listBarang;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView gambar;
        private TextView barang;
        private TextView diskon;
        private TextView hargaSetelahDiskon;
        private TextView hargaSebelumDiskon;

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
    public NewItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_new_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewItemAdapter.ViewHolder holder, final int position) {
        final Item barang = this.listBarang.get(position);

        if (barang.getDiscount() == 0) {
            String hargaSetelahDiskon = "Rp." + barang.getPrice() + ",-";

            holder.diskon.setVisibility(View.INVISIBLE);
            holder.hargaSebelumDiskon.setVisibility(View.INVISIBLE);
            holder.hargaSetelahDiskon.setText(hargaSetelahDiskon);
        }
        else {
            int hargaDiskon = barang.getPrice() * barang.getDiscount() / 100;

            String diskon = "-" + barang.getDiscount() + "%";
            String hargaSebelumDiskon = "Rp." + barang.getPrice() + ",-";
            String hargaSetelahDiskon = "Rp." + (barang.getPrice() - hargaDiskon) + ",-";

            holder.diskon.setText(diskon);
            holder.hargaSebelumDiskon.setText(hargaSebelumDiskon);
            holder.hargaSetelahDiskon.setText(hargaSetelahDiskon);
        }

        holder.barang.setText(barang.getName());
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
    }

    @Override
    public int getItemCount() {
        return this.listBarang.size();
    }
}
