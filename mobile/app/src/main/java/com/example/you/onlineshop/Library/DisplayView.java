package com.example.you.onlineshop.Library;

import android.view.View;
import android.widget.LinearLayout;

import com.example.you.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayView {

    private final int VIEW_SUCCESS = 1;
    private final int VIEW_PENDING = 0;
    private final int VIEW_ERROR = -1;

    private Map<String, Integer> listRequirement;
    private LinearLayout viewContent, viewLoading, viewError;

    public DisplayView(View view) {
        listRequirement = new HashMap<>();
        viewContent = view.findViewById(R.id.content_success);
        viewLoading = view.findViewById(R.id.content_loading);
        viewError = view.findViewById(R.id.content_error);
    }

    public void addRequirement(String key) {
        listRequirement.put(key, new Integer(VIEW_PENDING));
    }

    public void successRequirement(String key) {
        listRequirement.put(key, new Integer(VIEW_SUCCESS));
    }

    public void errorRequirement(String key) {
        listRequirement.put(key, new Integer(VIEW_ERROR));
    }

    public void loadView() {
        int status = getStatusRequirement();

        if (status == VIEW_ERROR) {
            this.viewError.setVisibility(View.VISIBLE);
            this.viewContent.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else if (status == VIEW_SUCCESS) {
            this.viewError.setVisibility(View.GONE);
            this.viewContent.setVisibility(View.VISIBLE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else {
            this.viewError.setVisibility(View.GONE);
            this.viewContent.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.VISIBLE);
        }
    }

    private int getStatusRequirement() {
        for (Map.Entry<String, Integer> requirement : listRequirement.entrySet()) {
            if (requirement.getValue().intValue() == VIEW_ERROR ) return VIEW_ERROR;
            if (requirement.getValue().intValue() == VIEW_PENDING) return VIEW_PENDING;
        }

        return VIEW_SUCCESS;
    }
}
