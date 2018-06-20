package com.example.you.dummyonlineshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        prefs = context.getSharedPreferences("Zro2iro_Session", context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        prefs.edit().putString("token", token).commit();
    }

    public String getToken() {
        return prefs.getString("token", "");
    }

    public void setHistorySearch(Set<String> set) {
        prefs.edit().putStringSet("history_search", set).commit();
    }

    public Set<String> getHistorySearch() {
        return prefs.getStringSet("history_search", new HashSet<>());
    }
}
