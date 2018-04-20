package com.example.you.onlineshop.Library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

import com.example.you.onlineshop.Fragment.FavoriteFragment;
import com.example.you.onlineshop.Fragment.HomeFragment;
import com.example.you.onlineshop.Fragment.KeranjangFragment;
import com.example.you.onlineshop.Fragment.ProfileFragment;
import com.example.you.onlineshop.MainActivity;
import com.example.you.onlineshop.R;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {

    private final int CONTENT = R.id.content;
    private final String HOME_FRAGMENT_TAG = "home";
    private final String PROFILE_FRAGMENT_TAG = "profile";
    private final String FAVORITE_FRAGMENT_TAG = "favorite";
    private final String KERANJANG_FRAGMENT_TAG = "keranjang";

    private MainActivity activity;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FavoriteFragment favoriteFragment;
    private KeranjangFragment keranjangFragment;

    private Fragment fragmentActive = null;
    private FragmentManager fragmentManager;

    public BottomNavigationViewHelper(MainActivity activity, String tag) {
        this.activity = activity;
        this.homeFragment = new HomeFragment();
        this.profileFragment = new ProfileFragment();
        this.favoriteFragment = new FavoriteFragment();
        this.keranjangFragment = new KeranjangFragment();
        this.fragmentManager = activity.getSupportFragmentManager();

        this.setFragment(tag);
    }

    private void setFragment(String tag) {
        if (tag.equals(FAVORITE_FRAGMENT_TAG)) {
            this.fragmentActive = this.favoriteFragment;
            this.fragmentManager.beginTransaction()
                .replace(CONTENT, this.favoriteFragment, FAVORITE_FRAGMENT_TAG)
                .commit();
        }
        else if (tag.equals(KERANJANG_FRAGMENT_TAG)) {
            this.fragmentActive = this.keranjangFragment;
            this.fragmentManager.beginTransaction()
                .replace(CONTENT, this.keranjangFragment, KERANJANG_FRAGMENT_TAG)
                .commit();
        }
        else if (tag.equals(PROFILE_FRAGMENT_TAG)) {
            this.fragmentActive = this.profileFragment;
            this.fragmentManager.beginTransaction()
                .replace(CONTENT, this.profileFragment, PROFILE_FRAGMENT_TAG)
                .commit();
        }
        else {
            this.fragmentActive = this.homeFragment;
            this.fragmentManager.beginTransaction()
                .replace(CONTENT, this.homeFragment, HOME_FRAGMENT_TAG)
                .commit();
        }
    }

    @SuppressLint("RestrictedApi")
    public void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                // noinspection RestrictedApi
                // item.setShiftingMode(false);

                // set once again checked value, so view will be updated
                // noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener setOnNavigationItemSelectedListener = (item) -> {
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
        }
        return false;
    };

    private void changeNavigationItemSelected(Fragment fragment, String tag) {
        if (this.fragmentManager.findFragmentByTag(tag) == null) {
            this.fragmentManager.beginTransaction()
                .add(CONTENT, fragment, tag)
                .hide(fragmentActive)
                .show(fragment)
                .commit();
        }
        else {
            this.fragmentManager.beginTransaction()
                .hide(fragmentActive)
                .show(fragment)
                .commit();
        }

        fragmentActive = fragment;
        this.activity.fragmentTagSavedInstance = tag;
    }
}
