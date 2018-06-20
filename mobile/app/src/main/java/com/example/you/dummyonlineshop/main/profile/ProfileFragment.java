package com.example.you.dummyonlineshop.main.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.data.Profile;
import com.example.you.dummyonlineshop.main.MainActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private Session session;
    private View view;
    private ImageView btnLogout;
    private ProfileContract.Presenter mPresenter;

    public ProfileFragment() {}

    public void setPresenter(@NonNull ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mPresenter = new ProfilePresenter(this, getContext());

        session = new Session(getContext());

        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener((View view) -> {
            View dialogView = getActivity().getLayoutInflater().inflate(R.layout.alert_logout, null);
            View dialogLoadingView = getActivity().getLayoutInflater().inflate(R.layout.alert_loading, null);

            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

            AlertDialog alertDialogLoading = new AlertDialog.Builder(getContext())
                .setView(dialogLoadingView)
                .create();

            TextView cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView confirm = dialogView.findViewById(R.id.btn_confirm);

            cancel.setOnClickListener((View viewOnDialog) -> {
                alertDialog.cancel();
            });

            confirm.setOnClickListener((View viewOnDialog) -> {
                session.setToken("");
                alertDialog.cancel();
                alertDialogLoading.show();
                new Handler().postDelayed(() -> {
                    alertDialogLoading.cancel();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }, 2000);
            });

            alertDialog.show();
        });

        return view;
    }

    public void setDisplay(Profile profile) {
        TextView profileName = view.findViewById(R.id.profile_name);
        TextView profileEmail = view.findViewById(R.id.profile_email);
        TextView profileNumber = view.findViewById(R.id.profile_number);

        profileName.setText(profile.getName());
        profileEmail.setText(profile.getEmail());
        profileNumber.setText(profile.getNumber());
    }
}
