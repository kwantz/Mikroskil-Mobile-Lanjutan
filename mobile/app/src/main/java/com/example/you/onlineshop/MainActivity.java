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

    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private LinearLayout mainContent;
    private ScrollView searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initView();
        this.initToolbar();
        this.initBottomNavigation();
    }

    private void initView() {
        this.navigation = findViewById(R.id.navigation);
        this.mainContent = findViewById(R.id.wow_1);
        this.searchContent = findViewById(R.id.search_content);
        this.toolbar = findViewById(R.id.toolbar);
    }

    private BottomNavigationViewHelper helper;
    private void initBottomNavigation() {
        this.helper = new BottomNavigationViewHelper(this);

        helper.removeShiftMode(this.navigation);
        this.navigation.setOnNavigationItemSelectedListener(helper.setOnNavigationItemSelectedListener());
    }

    private void initToolbar() {
        setSupportActionBar(this.toolbar);
    }

    public void testing(Menu menu) {
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        ToolbarSearch toolbarSearch = new ToolbarSearch(mainContent, searchContent);
        searchMenuItem.setOnActionExpandListener(toolbarSearch.setOnActionExpandListener());
        searchView.setOnQueryTextListener(toolbarSearch.setOnQueryTextListener());
    }

}
