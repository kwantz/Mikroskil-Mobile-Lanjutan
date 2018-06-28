package com.example.you.dummyonlineshop.adapter;

import android.app.Activity;
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
import com.example.you.dummyonlineshop.data.Address;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.Category;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.searchcategory.SearchCategoryActivity;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private View view;
    private Session session;
    private List<Address> listAddress;

    public AddressAdapter(List<Address> listAddress, Session session) {
        this.listAddress = listAddress;
        this.session = session;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address;
        private TextView name;
        private TextView number;
        private TextView city;
        private TextView poscode;
        private TextView btnHapus;
        private TextView btnEdit;
        private TextView btnUtamakan;

        public ViewHolder(View view) {
            super(view);

            this.address = view.findViewById(R.id.address);
            this.name = view.findViewById(R.id.name);
            this.number = view.findViewById(R.id.number);
            this.city = view.findViewById(R.id.city);
            this.poscode = view.findViewById(R.id.poscode);
            this.btnHapus = view.findViewById(R.id.btn_delete);
            this.btnEdit = view.findViewById(R.id.btn_edit);
            this.btnUtamakan = view.findViewById(R.id.btn_select);
        }
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_address, parent, false);
        return new AddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {
        Address address = this.listAddress.get(position);

        holder.address.setText(address.getName());
        holder.name.setText(address.getUserName());
        holder.number.setText(address.getUserPhone());
        holder.city.setText(address.getCity());
        holder.poscode.setText(address.getPostcode());

        holder.btnUtamakan.setOnClickListener((View v) -> {
            Retrofit retrofit = RetrofitLibrary.getRetrofit();
            OnlineShopService service = retrofit.create(OnlineShopService.class);

            service.setCurrentAddress("Bearer " + session.getToken(), address.getId()).enqueue(new Callback<Base>() {
                @Override
                public void onResponse(Call<Base> call, Response<Base> response) {
                    if (response.isSuccessful()) {
                        ((Activity)(view.getContext())).onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Base> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return this.listAddress.size();
    }
}
