package com.example.you.onlineshop;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.you.onlineshop.Fragment.HomeFragment;
import com.example.you.onlineshop.Library.BottomNavigationViewHelper;
import com.example.you.onlineshop.Library.ToolbarSearch;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    public String fragmentTagSavedInstance = "homeFragment";
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private LinearLayout mainContent;
    private ScrollView searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            fragmentTagSavedInstance = savedInstanceState.getString("FRAGMENT_TAG");
        }

        initView();
        initBottomNavigation();
        setSupportActionBar(toolbar);
    }

    private void initView() {
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        mainContent = findViewById(R.id.wow_1);
        searchContent = findViewById(R.id.search_content);
        toolbar = findViewById(R.id.toolbar);
    }

    private void initBottomNavigation() {
        BottomNavigationViewHelper helper = new BottomNavigationViewHelper(this, fragmentTagSavedInstance);

        helper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(helper.setOnNavigationItemSelectedListener);
    }

    public void testing(Menu menu) {
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        ToolbarSearch toolbarSearch = new ToolbarSearch(mainContent, searchContent);
        searchMenuItem.setOnActionExpandListener(toolbarSearch.setOnActionExpandListener());
        searchView.setOnQueryTextListener(toolbarSearch.setOnQueryTextListener());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("FRAGMENT_TAG", fragmentTagSavedInstance);
    }
}
