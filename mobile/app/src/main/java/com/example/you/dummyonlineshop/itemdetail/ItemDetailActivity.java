package com.example.you.dummyonlineshop.itemdetail;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Item;
import com.example.you.dummyonlineshop.library.DisplayContentView;
import com.example.you.dummyonlineshop.security.SecurityActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ItemDetailActivity extends AppCompatActivity implements ItemDetailContract.View {

    private Session session;

    private int itemId;
    private Item currentItem;
    private DisplayContentView displayView;
    private ItemDetailContract.Presenter mPresenter;

    private TextView categoryName;
    private ImageView itemPicture;
    private TextView itemPrice;
    private TextView itemDiscount;
    private TextView itemDiscountPresentage;
    private TextView itemStock;
    private TextView itemName;
    private TextView itemDescription;
    private ImageView btnFavorite;
    private ImageView btnBack;
    private Button btnAddCart;

    private boolean isFavorite;
    private AlertDialog loadingAlertDialog, cartAlertDialog;

    private int priceAfterDiscount;

    public void setPresenter(ItemDetailContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
        mPresenter.getItem(itemId);
        mPresenter.haveFavorite(itemId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        session = new Session(getBaseContext());

        btnBack = findViewById(R.id.btn_back);
        btnAddCart = findViewById(R.id.btn_add_cart);
        btnFavorite = findViewById(R.id.btn_favorite);
        categoryName = findViewById(R.id.category_name);
        itemName = findViewById(R.id.item_name);
        itemStock = findViewById(R.id.item_stock);
        itemPrice = findViewById(R.id.item_price);
        itemPicture = findViewById(R.id.item_picture);
        itemDiscount = findViewById(R.id.item_discount);
        itemDescription = findViewById(R.id.item_description);
        itemDiscountPresentage = findViewById(R.id.item_discount_persentage);

        View loadingView = getLayoutInflater().inflate(R.layout.alert_loading, null);
        loadingAlertDialog = new AlertDialog.Builder(this)
            .setView(loadingView)
            .create();

        itemId = getIntent().getIntExtra("ITEM_ID", 0);
        new ItemDetailPresenter(this, getBaseContext());

        btnBack.setOnClickListener((View view) -> onBackPressed());

        btnFavorite.setOnClickListener((View view) -> {
            Log.e("Favorite", Boolean.toString(isFavorite));
            loadingAlertDialog.show();
            if (isFavorite) {
                btnFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                mPresenter.deleteFavorite(itemId);
                isFavorite = false;
            } else {
                btnFavorite.setImageResource(R.drawable.ic_favorite_24dp);
                mPresenter.addFavorite(itemId);
                isFavorite = true;
            }
        });
        setupDisplayView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnFavorite.setVisibility((session.getToken().equals("")) ? View.GONE : View.VISIBLE);
    }

    private void setupDisplayView() {
        displayView = new DisplayContentView(findViewById(R.id.content_item_detail));
        displayView.addRequirement(ItemDetailViewType.ITEM_VIEW);
        displayView.loadView();
    }

    public void showItem(Item item) {
        displayView.successRequirement(ItemDetailViewType.ITEM_VIEW);
        displayView.loadView();

        currentItem = item;
        categoryName.setText(item.getCategory().getName());
        itemName.setText(item.getName());
        itemStock.setText(Integer.toString(item.getStock()));
        itemDescription.setText(item.getDescription());

        if (item.getDiscount() == 0) {
            String hargaSetelahDiskon = "Rp." + item.getPrice() + ",-";

            itemDiscountPresentage.setVisibility(View.INVISIBLE);
            itemDiscount.setVisibility(View.INVISIBLE);
            itemPrice.setText(hargaSetelahDiskon);
            priceAfterDiscount = item.getPrice();
        }
        else {
            int hargaDiskon = item.getPrice() * item.getDiscount() / 100;

            String diskon = "-" + item.getDiscount() + "%";
            String hargaSebelumDiskon = "Rp." + item.getPrice() + ",-";
            String hargaSetelahDiskon = "Rp." + (item.getPrice() - hargaDiskon) + ",-";

            itemDiscountPresentage.setText(diskon);
            itemPrice.setText(hargaSebelumDiskon);
            itemPrice.setBackgroundResource(R.drawable.strikethrough);
            itemDiscount.setText(hargaSetelahDiskon);
            priceAfterDiscount = (item.getPrice() - hargaDiskon);
        }

        Picasso.with(getBaseContext())
            .load(item.getPicture())
            .error(R.drawable.ic_do_not_disturb_alt_24dp)
            .placeholder(R.drawable.ic_autorenew_24dp)
            .into(itemPicture);

        btnAddCart.setOnClickListener((View view) -> {
            new Handler().postDelayed(() -> {

                if (session.getToken().equals("")) {
                    loadingAlertDialog.cancel();
                    Intent intent = new Intent(getBaseContext(), SecurityActivity.class);
                    startActivity(intent);
                } else {
                    mPresenter.haveItemInCart(item);
                }
            }, 1000);

            loadingAlertDialog.show();
        });
    }

    public void setupBtnFavorite(boolean status) {
        Log.e("BTN Favorite", "Ada masuk kok");
        isFavorite = status;
        btnFavorite.setImageResource((status)
            ? R.drawable.ic_favorite_24dp
            : R.drawable.ic_favorite_border_black_24dp
        );
    }

    public void showErrorConnection(String tag) {
        displayView.errorRequirement(tag);
        displayView.loadView();
    }

    public void setupFavorite() {
        loadingAlertDialog.cancel();
    }

    public void setupAddCart() {
        loadingAlertDialog.cancel();
        cartAlertDialog.cancel();

        View successView = getLayoutInflater().inflate(R.layout.alert_success_cart, null);
        new AlertDialog.Builder(this)
            .setView(successView)
            .create()
            .show();
    }

    public void setupCart(boolean status, Item item) {
        loadingAlertDialog.cancel();
        if (status) {
            View cartView = getLayoutInflater().inflate(R.layout.alert_cart, null);
            cartAlertDialog = new AlertDialog.Builder(this)
                .setView(cartView)
                .create();

            TextView cartItemName = cartView.findViewById(R.id.item_name);
            ImageView cartItemPicture = cartView.findViewById(R.id.item_picture);
            TextView cartItemPrice = cartView.findViewById(R.id.item_price);
            Button btnAdd = cartView.findViewById(R.id.btn_add);
            EditText inputNote = cartView.findViewById(R.id.input_note);
            EditText inputQty = cartView.findViewById(R.id.input_qty);
            TextView priceTotal = cartView.findViewById(R.id.price_total);
            ImageView btnClose = cartView.findViewById(R.id.btn_close);
            TextInputLayout inputLayoutQty = cartView.findViewById(R.id.input_layout_qty);

            inputQty.setSelection(inputQty.getText().length());
            cartItemName.setText(item.getName());
            cartItemPrice.setText("Rp." + priceAfterDiscount + ",-");
            priceTotal.setText("Rp." + priceAfterDiscount + ",-");
            Picasso.with(getBaseContext())
                .load(item.getPicture())
                .error(R.drawable.ic_do_not_disturb_alt_24dp)
                .placeholder(R.drawable.ic_autorenew_24dp)
                .into(cartItemPicture);

            btnClose.setOnClickListener((View v) -> cartAlertDialog.cancel());

            inputQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!inputQty.getText().toString().equals("")) {
                        int result = Integer.parseInt(inputQty.getText().toString());
                        int maxBuy = Math.min(10, item.getStock());

                        if (result < 1) inputQty.setText("1");
                        else if (result > maxBuy) inputQty.setText(Integer.toString(maxBuy));

                        int total = priceAfterDiscount * Integer.parseInt(inputQty.getText().toString());
                        priceTotal.setText("Rp." + total + ",-");
                        inputQty.setSelection(inputQty.getText().length());
                    } else {
                        priceTotal.setText("Rp. 0,-");
                    }
                }
            });

            btnAdd.setOnClickListener((View viewCart) -> {
                if (inputQty.getText().toString().equals("")) {
                    inputLayoutQty.setError("Qty tidak boleh kosong");
                } else {
                    loadingAlertDialog.show();
                    mPresenter.addItemToCart(
                        item.getId(),
                        inputQty.getText().toString(),
                        inputNote.getText().toString()
                    );
                }
            });

            cartAlertDialog.show();
        } else {
            Toast.makeText(this, "Barang telah tersedia di dalam keranjang", Toast.LENGTH_LONG).show();
        }
    }
}
