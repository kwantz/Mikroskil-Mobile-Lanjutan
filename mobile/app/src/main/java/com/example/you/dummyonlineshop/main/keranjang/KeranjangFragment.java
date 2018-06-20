package com.example.you.dummyonlineshop.main.keranjang;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.adapter.KeranjangAdapter;
import com.example.you.dummyonlineshop.data.Cart;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.library.DisplayContentView;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class KeranjangFragment extends Fragment implements KeranjangContract.View {

    private View view;
    private RecyclerView rvKeranjang;
    private ScrollView contentKeranjangList;
    private LinearLayout contentKeranjangEmpty;
    private TextView totalHarga;

    private DisplayContentView displayView;

    private KeranjangContract.Presenter mPresenter;

    public KeranjangFragment() {}

    public void setPresenter(@NonNull KeranjangContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        totalHarga = view.findViewById(R.id.total_harga);
        rvKeranjang = view.findViewById(R.id.rv_keranjang);
        contentKeranjangList = view.findViewById(R.id.keranjang_list);
        contentKeranjangEmpty = view.findViewById(R.id.keranjang_empty);

        setupDisplayView();
        mPresenter.setupKeranjang();

        setHasOptionsMenu(true);
        return view;
    }

    private void setupDisplayView() {
        displayView = new DisplayContentView(view);
        displayView.addRequirement(KeranjangViewType.KERANJANG_VIEW);
        displayView.loadView();
    }

    public void showRecycleViewKeranjang(List<Cart> data) {
        if (data != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            RecyclerView.Adapter adapter = new KeranjangAdapter(data);

            rvKeranjang.setLayoutManager(layoutManager);
            rvKeranjang.setAdapter(adapter);

            contentKeranjangList.setVisibility(View.VISIBLE);
            contentKeranjangEmpty.setVisibility(View.GONE);

            int total = 0;
            for (Cart cart : data) {
                total += cart.getQuantity() * cart.getItem().getPrice();
            }
            totalHarga.setText("Rp." + total + ",-");
        }
        else {
            contentKeranjangList.setVisibility(View.GONE);
            contentKeranjangEmpty.setVisibility(View.VISIBLE);
        }

        displayView.successRequirement(KeranjangViewType.KERANJANG_VIEW);
        displayView.loadView();
    }

    public void showErrorConnection(String tag) {
        displayView.errorRequirement(tag);
        displayView.loadView();
    }
}
