package com.example.you.dummyonlineshop.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.HistorySearchAdapter;
import com.example.you.dummyonlineshop.main.favorite.FavoriteFragment;
import com.example.you.dummyonlineshop.main.favorite.FavoritePresenter;
import com.example.you.dummyonlineshop.main.home.HomeFragment;
import com.example.you.dummyonlineshop.main.home.HomePresenter;
import com.example.you.dummyonlineshop.main.keranjang.KeranjangFragment;
import com.example.you.dummyonlineshop.main.keranjang.KeranjangPresenter;
import com.example.you.dummyonlineshop.main.profile.ProfileFragment;
import com.example.you.dummyonlineshop.main.profile.ProfilePresenter;
import com.example.you.dummyonlineshop.main.transaksi.TransaksiFragment;
import com.example.you.dummyonlineshop.searchitem.SearchItemActivity;

import java.lang.reflect.Field;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity {

    private final int VIEW_CONTENT = R.id.content;
    private final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";
    private final String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";
    private final String FAVORITE_FRAGMENT_TAG = "FAVORITE_FRAGMENT_TAG";
    private final String KERANJANG_FRAGMENT_TAG = "KERANJANG_FRAGMENT_TAG";
    private final String TRANSAKSI_FRAGMENT_TAG = "TRANSAKSI_FRAGMENT_TAG";

    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FavoriteFragment favoriteFragment;
    private KeranjangFragment keranjangFragment;
    private TransaksiFragment transaksiFragment;

    private String fragmentTagSavedInstance;

    private Fragment fragmentActive;
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            fragmentTagSavedInstance = savedInstanceState.getString(FRAGMENT_TAG);
        else
            fragmentTagSavedInstance = HOME_FRAGMENT_TAG;

        session = new Session(getBaseContext());
        navigation = findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupFragment();

        if (session.getToken().equals("")) {
            navigation.setVisibility(View.GONE);
        } else {
            removeShiftMode(navigation);
            navigation.setVisibility(View.VISIBLE);
            navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(FRAGMENT_TAG, fragmentTagSavedInstance);
    }

    private void setupFragment() {
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        favoriteFragment = new FavoriteFragment();
        keranjangFragment = new KeranjangFragment();
        transaksiFragment = new TransaksiFragment();

        new HomePresenter(homeFragment);
        new FavoritePresenter(favoriteFragment, getBaseContext());
        new KeranjangPresenter(keranjangFragment, getBaseContext());

        switch (fragmentTagSavedInstance) {
            case FAVORITE_FRAGMENT_TAG:
                fragmentActive = favoriteFragment;
                fragmentTagSavedInstance = FAVORITE_FRAGMENT_TAG;
                fragmentManager.beginTransaction()
                    .replace(VIEW_CONTENT, favoriteFragment, FAVORITE_FRAGMENT_TAG)
                    .commit();
                break;

            case KERANJANG_FRAGMENT_TAG:
                fragmentActive = keranjangFragment;
                fragmentTagSavedInstance = KERANJANG_FRAGMENT_TAG;
                fragmentManager.beginTransaction()
                    .replace(VIEW_CONTENT, keranjangFragment, KERANJANG_FRAGMENT_TAG)
                    .commit();
                break;

            case PROFILE_FRAGMENT_TAG:
                fragmentActive = profileFragment;
                fragmentTagSavedInstance = PROFILE_FRAGMENT_TAG;
                fragmentManager.beginTransaction()
                    .replace(VIEW_CONTENT, profileFragment, PROFILE_FRAGMENT_TAG)
                    .commit();
                break;

            case TRANSAKSI_FRAGMENT_TAG:
                fragmentActive = transaksiFragment;
                fragmentTagSavedInstance = TRANSAKSI_FRAGMENT_TAG;
                fragmentManager.beginTransaction()
                    .replace(VIEW_CONTENT, transaksiFragment, TRANSAKSI_FRAGMENT_TAG)
                    .commit();
                break;

            default:
                fragmentActive = homeFragment;
                fragmentTagSavedInstance = HOME_FRAGMENT_TAG;
                fragmentManager.beginTransaction()
                    .replace(VIEW_CONTENT, homeFragment, HOME_FRAGMENT_TAG)
                    .commit();
        }
    }

    @SuppressLint("RestrictedApi")
    private void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setChecked(item.getItemData().isChecked());
                // item.setShiftingMode(false);
            }
        }
        catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        }
        catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = (MenuItem item) -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                changeNavigationItemSelected(homeFragment, HOME_FRAGMENT_TAG);
                return true;
            case R.id.navigation_favorite:
                changeNavigationItemSelected(favoriteFragment, FAVORITE_FRAGMENT_TAG);
                return true;
            case R.id.navigation_keranjang:
                changeNavigationItemSelected(keranjangFragment, KERANJANG_FRAGMENT_TAG);
                return true;
            case R.id.navigation_profile:
                changeNavigationItemSelected(profileFragment, PROFILE_FRAGMENT_TAG);
                return true;
            case R.id.navigation_transaksi:
                changeNavigationItemSelected(transaksiFragment, TRANSAKSI_FRAGMENT_TAG);
                return true;
        }
        return false;
    };

    private void changeNavigationItemSelected(Fragment fragment, String tag) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                .add(VIEW_CONTENT, fragment, tag)
                .hide(fragmentActive)
                .show(fragment)
                .commit();
        }
        else {
            fragmentManager.beginTransaction()
                .hide(fragmentActive)
                .show(fragment)
                .commit();
        }

        fragmentActive = fragment;
        fragmentTagSavedInstance = tag;
    }
}
