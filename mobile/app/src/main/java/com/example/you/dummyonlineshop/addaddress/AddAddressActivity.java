package com.example.you.dummyonlineshop.addaddress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Base;
import com.example.you.dummyonlineshop.data.post.AddressBody;
import com.example.you.dummyonlineshop.library.RetrofitLibrary;
import com.example.you.dummyonlineshop.service.OnlineShopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        EditText inputAddress = findViewById(R.id.input_address);
        EditText inputCity = findViewById(R.id.input_city);
        EditText inputPoscode = findViewById(R.id.input_poscode);
        Button btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener((View v) -> {
            Session session = new Session(getBaseContext());
            Retrofit retrofit = RetrofitLibrary.getRetrofit();
            OnlineShopService service = retrofit.create(OnlineShopService.class);

            AddressBody addressBody = new AddressBody();
            addressBody.setCity(inputCity.getText().toString());
            addressBody.setName(inputAddress.getText().toString());
            addressBody.setPostcode(inputPoscode.getText().toString());

            service.addAddress("Bearer " + session.getToken(), addressBody).enqueue(new Callback<Base>() {
                @Override
                public void onResponse(Call<Base> call, Response<Base> response) {
                    if (response.isSuccessful()) {
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Base> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        });
    }
}
