package com.example.you.dummyonlineshop.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.main.MainActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private Session session;
    private ProgressBar progressBar;
    private SplashScreenContract.Presenter mPresenter;

    public void setPresenter(@NonNull SplashScreenContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new SplashScreenPresenter(this, getBaseContext());
        session = new Session(getBaseContext());
        setupProgressBar();
    }

    private void setupProgressBar() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            String token = session.getToken();

            progressBar.setVisibility(View.VISIBLE);
            mPresenter.checkToken(token);
        }, 2000);
    }

    public void changeActivity(boolean status) {
        if (!status)
            session.setToken("");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
