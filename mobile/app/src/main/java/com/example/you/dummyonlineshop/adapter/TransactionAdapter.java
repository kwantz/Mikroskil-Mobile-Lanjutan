package com.example.you.dummyonlineshop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.data.Transaction;
import com.example.you.dummyonlineshop.itemdetail.ItemDetailActivity;
import com.example.you.dummyonlineshop.transaction.TransactionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    private View view;
    private List<Transaction> listTransaction;

    public TransactionAdapter(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView invoice;
        private TextView date;
        private TextView totalPrice;
        private TextView status;

        public ViewHolder(View view) {
            super(view);
            this.invoice = view.findViewById(R.id.invoice);
            this.date = view.findViewById(R.id.date);
            this.totalPrice = view.findViewById(R.id.total_price);
            this.status = view.findViewById(R.id.status);
        }
    }

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_transaction, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionAdapter.ViewHolder holder, final int position) {
        final Transaction transaction = this.listTransaction.get(position);

        holder.date.setText(transaction.getDate());
        holder.invoice.setText(transaction.getInvoice());
        holder.totalPrice.setText("Rp." + transaction.getTotal() + ",-");
        holder.status.setText(transaction.getPaymentStatus());

        view.setOnClickListener((View v) -> {
            Intent intent = new Intent(view.getContext(), TransactionActivity.class);
            intent.putExtra("transaction_id", transaction.getId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.listTransaction.size();
    }
}
