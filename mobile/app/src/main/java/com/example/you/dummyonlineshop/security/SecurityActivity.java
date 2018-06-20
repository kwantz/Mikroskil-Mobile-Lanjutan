package com.example.you.dummyonlineshop.security;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.security.login.LoginFragment;
import com.example.you.dummyonlineshop.security.login.LoginPresenter;
import com.example.you.dummyonlineshop.security.register.RegisterFragment;
import com.example.you.dummyonlineshop.security.register.RegisterPresenter;

public class SecurityActivity extends AppCompatActivity {

    private String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT_TAG";
    private String REGISTER_FRAGMENT_TAG = "REGISTER_FRAGMENT_TAG";

    private FragmentManager fragmentManager;
    private TextView btnSignIn;
    private TextView btnSignUp;
    private ImageView btnBack;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        setupFragment();
        setupBackButton();
        setupSignInButton();
        setupSignUpButton();
    }

    private void setupFragment() {
        loginFragment = new LoginFragment();
        loginFragment.setPresenter(new LoginPresenter(loginFragment));

        registerFragment = new RegisterFragment();
        registerFragment.setPresenter(new RegisterPresenter(registerFragment));

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.content, loginFragment, LOGIN_FRAGMENT_TAG)
            .commit();
    }

    private void setupBackButton() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener((View view) -> onBackPressed());
    }

    private void setupSignInButton() {
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setVisibility(View.INVISIBLE);
        btnSignIn.setOnClickListener((View view) -> {
            btnSignIn.setVisibility(View.INVISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction()
                .replace(R.id.content, loginFragment, LOGIN_FRAGMENT_TAG)
                .commit();
        });
    }

    private void setupSignUpButton() {
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener((View view) -> {
            btnSignUp.setVisibility(View.INVISIBLE);
            btnSignIn.setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction()
                .replace(R.id.content, registerFragment, REGISTER_FRAGMENT_TAG)
                .commit();
        });
    }
}
