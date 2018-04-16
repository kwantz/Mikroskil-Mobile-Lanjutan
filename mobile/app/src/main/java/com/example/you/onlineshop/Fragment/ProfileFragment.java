package com.example.you.onlineshop.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.you.onlineshop.R;

public class ProfileFragment extends Fragment {

    private View view;

    public ProfileFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        this.view = inflater.inflate(R.layout.fragment_profile, container, false);
        return this.view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_none, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
