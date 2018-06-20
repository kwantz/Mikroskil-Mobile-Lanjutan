package com.example.you.dummyonlineshop.security.register;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.profile.ProfileActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegisterFragment extends Fragment implements RegisterContract.View {

    private Session session;
    private View view;
    private Button btnSignUp;
    private EditText inputEmail;
    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputRePassword;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutUsername;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutRePassword;
    private RegisterContract.Presenter mPresenter;

    private AlertDialog alertDialogLoading;

    public void setPresenter(@NonNull RegisterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getContext());
        view = inflater.inflate(R.layout.fragment_register, container, false);

        btnSignUp = view.findViewById(R.id.btn_sign_up);
        inputEmail = view.findViewById(R.id.input_email);
        inputUsername = view.findViewById(R.id.input_username);
        inputPassword = view.findViewById(R.id.input_password);
        inputRePassword = view.findViewById(R.id.input_re_password);
        inputLayoutEmail = view.findViewById(R.id.input_layout_email);
        inputLayoutUsername = view.findViewById(R.id.input_layout_username);
        inputLayoutPassword = view.findViewById(R.id.input_layout_password);
        inputLayoutRePassword = view.findViewById(R.id.input_layout_re_password);

        View dialogLoadingView = getActivity().getLayoutInflater().inflate(R.layout.alert_loading, null);
        alertDialogLoading = new AlertDialog.Builder(getContext())
            .setView(dialogLoadingView)
            .create();

        btnSignUp.setOnClickListener((View view) -> {
            if (validation()) {
                alertDialogLoading.show();
                mPresenter.submit(
                    inputEmail.getText().toString(),
                    inputUsername.getText().toString(),
                    inputPassword.getText().toString()
                );
            }
        });

        return view;
    }

    private boolean validation() {
        if (inputUsername.getText().toString().isEmpty()) {
            inputLayoutUsername.setError("Username tidak boleh kosong");
            return false;
        }if (inputEmail.getText().toString().isEmpty()) {
            inputLayoutEmail.setError("Email tidak boleh kosong");
            return false;
        }
        if (!inputPassword.getText().toString().equals(inputRePassword.getText().toString())) {
            inputLayoutPassword.setError("Password dan Re-Password harus sesuai");
            inputLayoutRePassword.setError("Password dan Re-Password harus sesuai");
            return false;
        }
        return true;
    }

    public void registerResponse(String message, String token) {
        alertDialogLoading.cancel();
        session.setToken(token);
        Log.e("message", message);
        Log.e("token", token);
        if (!token.equals("")) {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
