package com.example.you.dummyonlineshop.library;

import android.view.View;

import com.example.you.dummyonlineshop.R;

import java.util.HashMap;
import java.util.Map;

public class DisplayContentView {

    private enum DisplayStatus {
        VIEW_ERROR,
        VIEW_SUCCESS,
        VIEW_LOADING
    }

    private View viewError;
    private View viewLoading;
    private View viewSuccess;

    private Map<String, DisplayStatus> listRequirement;

    public DisplayContentView(View view) {
        listRequirement = new HashMap<>();
        viewError = view.findViewById(R.id.content_error);
        viewSuccess = view.findViewById(R.id.content_success);
        viewLoading = view.findViewById(R.id.content_loading);
    }

    public void addRequirement(String key) {
        listRequirement.put(key, DisplayStatus.VIEW_LOADING);
    }

    public void successRequirement(String key) {
        listRequirement.put(key, DisplayStatus.VIEW_SUCCESS);
    }

    public void errorRequirement(String key) {
        listRequirement.put(key, DisplayStatus.VIEW_ERROR);
    }

    public void loadView() {
        DisplayStatus status = getStatusRequirement();

        if (status == DisplayStatus.VIEW_ERROR) {
            this.viewError.setVisibility(View.VISIBLE);
            this.viewSuccess.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else if (status == DisplayStatus.VIEW_SUCCESS) {
            this.viewError.setVisibility(View.GONE);
            this.viewSuccess.setVisibility(View.VISIBLE);
            this.viewLoading.setVisibility(View.GONE);
        }
        else {
            this.viewError.setVisibility(View.GONE);
            this.viewSuccess.setVisibility(View.GONE);
            this.viewLoading.setVisibility(View.VISIBLE);
        }
    }

    private DisplayStatus getStatusRequirement() {
        for (Map.Entry<String, DisplayStatus> requirement : listRequirement.entrySet()) {
            if (requirement.getValue() == DisplayStatus.VIEW_ERROR ) return DisplayStatus.VIEW_ERROR;
            if (requirement.getValue() == DisplayStatus.VIEW_LOADING) return DisplayStatus.VIEW_LOADING;
        }

        return DisplayStatus.VIEW_SUCCESS;
    }
}
