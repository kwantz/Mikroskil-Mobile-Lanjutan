package com.example.you.dummyonlineshop.edititemcart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.post.CartBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditItemCartActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView btnSave;
    private ImageView btnRemove;
    private TextView itemName;
    private TextView itemPrice;
    private EditText itemNote;
    private EditText itemQty;
    private TextView totalPrice;
    private ImageView itemPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_cart);

        int itemId = getIntent().getIntExtra("item_id", 0);
        int itemPriceData = getIntent().getIntExtra("item_price", 0);
        String itemNameData = getIntent().getStringExtra("item_name");
        String itemPictureData = getIntent().getStringExtra("item_picture");
        String qtyData = getIntent().getStringExtra("item_qty");
        String noteData = getIntent().getStringExtra("item_note");

        Session session = new Session(getBaseContext());

        View loadingView = getLayoutInflater().inflate(R.layout.alert_loading, null);
        View removeItemView = getLayoutInflater().inflate(R.layout.alert_remove_item, null);

        AlertDialog loadingAlertDialog = new AlertDialog.Builder(this)
            .setView(loadingView)
            .create();

        AlertDialog removeItemAlertDialog = new AlertDialog.Builder(this)
            .setView(removeItemView)
            .create();

        btnBack = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.btn_save);
        btnRemove = findViewById(R.id.btn_remove);
        itemNote = findViewById(R.id.input_note);
        itemQty = findViewById(R.id.input_qty);
        totalPrice = findViewById(R.id.price_total);
        itemName = findViewById(R.id.item_name);
        itemPrice = findViewById(R.id.item_price);
        itemPicture = findViewById(R.id.item_picture);

        Retrofit retrofit = RetrofitLibrary.getRetrofit();
        OnlineShopService service = retrofit.create(OnlineShopService.class);

        itemQty.setText(qtyData);
        itemNote.setText(noteData);

        itemName.setText(itemNameData);
        itemPrice.setText("Rp." + itemPriceData + ",-");
        totalPrice.setText("Rp." + (itemPriceData * Integer.parseInt(qtyData)) + ",-");

        Picasso.with(this)
            .load(itemPictureData)
            .error(R.drawable.ic_do_not_disturb_alt_24dp)
            .placeholder(R.drawable.ic_autorenew_24dp)
            .into(itemPicture);

        btnBack.setOnClickListener((View v) -> onBackPressed());
        btnSave.setOnClickListener((View v) -> {
            CartBody cartBody = new CartBody();
            cartBody.setQty(Integer.parseInt(itemQty.getText().toString()));
            cartBody.setNote(itemNote.getText().toString());
            cartBody.setItem(itemId);

            loadingAlertDialog.show();
            service.updateItemInCart("Bearer " + session.getToken(), cartBody).enqueue(new Callback<Base>() {
                @Override
                public void onResponse(Call<Base> call, Response<Base> response) {
                    if (response.isSuccessful()){
                        loadingAlertDialog.cancel();
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Base> call, Throwable t) {
                    Toast.makeText(getBaseContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        });

        btnRemove.setOnClickListener((View v) -> {
            removeItemAlertDialog.show();

            TextView confirm = removeItemView.findViewById(R.id.btn_confirm);
            TextView cancel = removeItemView.findViewById(R.id.btn_cancel);

            confirm.setOnClickListener((View view) -> {
                removeItemAlertDialog.cancel();
                loadingAlertDialog.show();
                service.deleteItemInCart("Bearer " + session.getToken(), itemId).enqueue(new Callback<Base>() {
                    @Override
                    public void onResponse(Call<Base> call, Response<Base> response) {
                        loadingAlertDialog.cancel();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<Base> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            });

            cancel.setOnClickListener((View view) -> removeItemAlertDialog.cancel());
        });
    }

}