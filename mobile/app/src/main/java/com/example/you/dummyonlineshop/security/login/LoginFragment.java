package com.example.you.dummyonlineshop.security.login;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View {

    private Session session;
    private View view;
    private Button btnSignIn;
    private EditText inputUsername;
    private EditText inputPassword;
    private TextInputLayout inputLayoutUsername;
    private TextInputLayout inputLayoutPassword;
    private LoginContract.Presenter mPresenter;

    private AlertDialog alertDialogLoading;

    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getContext());
        view = inflater.inflate(R.layout.fragment_login, container, false);

        btnSignIn = view.findViewById(R.id.btn_sign_in);
        inputUsername = view.findViewById(R.id.input_username);
        inputPassword = view.findViewById(R.id.input_password);
        inputLayoutUsername = view.findViewById(R.id.input_layout_username);
        inputLayoutPassword = view.findViewById(R.id.input_layout_password);

        View dialogLoadingView = getActivity().getLayoutInflater().inflate(R.layout.alert_loading, null);
        alertDialogLoading = new AlertDialog.Builder(getContext())
            .setView(dialogLoadingView)
            .create();

        btnSignIn.setOnClickListener((View view) -> {
            if (validation()) {
                alertDialogLoading.show();
                mPresenter.submit(inputUsername.getText().toString(), inputPassword.getText().toString());
            }
        });

        return view;
    }

    private boolean validation() {
        if (inputUsername.getText().toString().isEmpty()) {
            inputLayoutUsername.setError("Username tidak boleh kosong");
            return false;
        }
        if (inputPassword.getText().toString().isEmpty()) {
            inputLayoutPassword.setError("Password tidak boleh kosong");
            return false;
        }
        return true;
    }

    public void loginResponse(String message, String token) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        alertDialogLoading.cancel();
        session.setToken(token);

        if (!token.equals("")) getActivity().onBackPressed();
    }
}
