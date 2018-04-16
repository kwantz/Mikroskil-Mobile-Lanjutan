package com.example.you.onlineshop.Library;

import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

public class ToolbarSearch {

    private View mainContent;
    private View searchContent;

    public ToolbarSearch(View mainContent, View searchContent) {
        this.mainContent = mainContent;
        this.searchContent = searchContent;
    }

    public MenuItem.OnActionExpandListener setOnActionExpandListener() {
        return new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                mainContent.setVisibility(View.GONE);
                searchContent.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mainContent.setVisibility(View.VISIBLE);
                searchContent.setVisibility(View.GONE);
                return true;
            }
        };
    }

    public SearchView.OnQueryTextListener setOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }
}

