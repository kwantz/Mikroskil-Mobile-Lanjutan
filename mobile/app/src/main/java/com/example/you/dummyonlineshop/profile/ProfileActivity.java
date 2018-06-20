package com.example.you.dummyonlineshop.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileActivity  extends AppCompatActivity implements ProfileContract.View {

    private Button btnConfirm;
    private EditText inputName;
    private EditText inputNumber;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutNumber;
    private ProfileContract.Presenter mPresenter;

    public void setPresenter(@NonNull ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setPresenter(new ProfilePresenter(this, getBaseContext()));

        btnConfirm = findViewById(R.id.btn_confirm);
        inputName = findViewById(R.id.input_name);
        inputNumber = findViewById(R.id.input_number);
        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutNumber = findViewById(R.id.input_layout_number);

        btnConfirm.setOnClickListener((View view) -> {
            if (validation()) {
                mPresenter.submit(inputName.getText().toString(), inputNumber.getText().toString());
            }
        });
    }

    private boolean validation() {
        if (inputName.getText().toString().isEmpty()) {
            inputLayoutName.setError("Name tidak boleh kosong");
            return false;
        }
        if (inputNumber.getText().toString().isEmpty()) {
            inputLayoutNumber.setError("Number tidak boleh kosong");
            return false;
        }
        return true;
    }

    public void response(boolean status) {
        if (status) onBackPressed();
    }
}
