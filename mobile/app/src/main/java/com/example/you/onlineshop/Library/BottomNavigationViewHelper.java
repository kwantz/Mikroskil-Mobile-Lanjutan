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

    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FavoriteFragment favoriteFragment;
    private KeranjangFragment keranjangFragment;

    private Fragment FRAGMENT_ACTIVE = null;
    private FragmentManager fragmentManager;

    public BottomNavigationViewHelper(MainActivity activity) {
        int content = R.id.content;
        this.homeFragment = new HomeFragment();
        this.profileFragment = new ProfileFragment();
        this.favoriteFragment = new FavoriteFragment();
        this.keranjangFragment = new KeranjangFragment();

        this.FRAGMENT_ACTIVE = this.homeFragment;

        this.fragmentManager = activity.getSupportFragmentManager();
        this.fragmentManager.beginTransaction().add(content, this.homeFragment).commit();
        this.fragmentManager.beginTransaction().add(content, this.profileFragment).hide(this.profileFragment).commit();
        this.fragmentManager.beginTransaction().add(content, this.favoriteFragment).hide(this.favoriteFragment).commit();
        this.fragmentManager.beginTransaction().add(content, this.keranjangFragment).hide(this.keranjangFragment).commit();
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
                //noinspection RestrictedApi
//                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener setOnNavigationItemSelectedListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        changeNavigationItemSelected(homeFragment);
                        return true;
                    case R.id.navigation_favorite:
                        changeNavigationItemSelected(favoriteFragment);
                        return true;
                    case R.id.navigation_keranjang:
                        changeNavigationItemSelected(keranjangFragment);
                        return true;
                    case R.id.navigation_profile:
                        changeNavigationItemSelected(profileFragment);
                        return true;
                }
                return false;
            }
        };
    }

    private void changeNavigationItemSelected(Fragment fragment) {
        if (FRAGMENT_ACTIVE == null) this.fragmentManager.beginTransaction().show(fragment).commit();
        else this.fragmentManager.beginTransaction().hide(FRAGMENT_ACTIVE).show(fragment).commit();

        FRAGMENT_ACTIVE = fragment;
        Log.e("OnlineShop Mobile Apps", "class BottomNavigationViewHelper ===> function changeNavigationItemSelected");
    }
}
