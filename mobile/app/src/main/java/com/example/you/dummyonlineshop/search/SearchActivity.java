package com.example.you.dummyonlineshop.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.you.dummyonlineshop.R;
import com.example.you.dummyonlineshop.Session;
import com.example.you.dummyonlineshop.adapter.HistorySearchAdapter;
import com.example.you.dummyonlineshop.searchitem.SearchItemActivity;

import java.util.HashSet;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private Session session;
    private ImageView btnBack;
    private ImageView btnClear;
    private EditText inputSearch;
    private RecyclerView rvSearch;
    private Set<String> historySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        session = new Session(getBaseContext());
        rvSearch = findViewById(R.id.rv_search);
        btnBack = findViewById(R.id.btn_back);
        btnClear = findViewById(R.id.btn_clear);
        inputSearch = findViewById(R.id.input_search);

        setupHistorySearch();
        setupSearchInput();
        setupClearButton();
        setupBackButton();
    }

    private void setupHistorySearch() {
        historySearch = session.getHistorySearch();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter = new HistorySearchAdapter(historySearch);

        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(adapter);
    }

    private void setupBackButton() {
        btnBack.setOnClickListener((View view) ->  onBackPressed());
    }

    private void setupClearButton() {
        btnClear.setVisibility((inputSearch.getText().toString().equals("")) ? View.GONE : View.VISIBLE);
        btnClear.setOnClickListener((View view) -> {
            inputSearch.setText("");
            btnClear.setVisibility(View.GONE);
            rvSearch.setAdapter(new HistorySearchAdapter(historySearch));
        });
    }

    private void setupSearchInput() {
        inputSearch.setText(getIntent().getStringExtra("SEARCH_NAME"));
        inputSearch.setFocusable(true);
        inputSearch.setSelection(inputSearch.getText().length());
        inputSearch.addTextChangedListener(toggleClearButton);
        inputSearch.setOnKeyListener(onSubmit);
    }

    private TextWatcher toggleClearButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (inputSearch.getText().toString().equals("")) {
                btnClear.setVisibility(View.GONE);
                rvSearch.setAdapter(new HistorySearchAdapter(historySearch));
            } else {
                btnClear.setVisibility(View.VISIBLE);

                Set<String> temp = new HashSet<>();
                for (String searchResult : historySearch) {
                    if (searchResult.indexOf(inputSearch.getText().toString()) == 0) {
                        temp.add(searchResult);
                    }
                }
                rvSearch.setAdapter(new HistorySearchAdapter(temp));
            }
        }
    };

    private View.OnKeyListener onSubmit = (View view, int keyCode, KeyEvent keyEvent) -> {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && !inputSearch.getText().toString().equals("")) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    historySearch.add(inputSearch.getText().toString());
                    session.setHistorySearch(historySearch);

                    Intent intent = new Intent(getBaseContext(), SearchItemActivity.class);
                    intent.putExtra("SEARCH_NAME", inputSearch.getText().toString());
                    startActivity(intent);
                    finish();

                    return true;
                default:
                    break;
            }
        }
        return false;
    };
}
