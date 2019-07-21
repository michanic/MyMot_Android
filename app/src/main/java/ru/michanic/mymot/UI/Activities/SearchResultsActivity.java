package ru.michanic.mymot.UI.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.R;

public class SearchResultsActivity extends UniversalActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setNavigationTitle("Результаты поиска");

        MyMotApplication.searchManager.filterClosedCallback = new FilterSettedInterface() {
            @Override
            public void onSelected(SearchFilterConfig filterConfig) {
                Log.e("filterClosedCallback","");
            }
        };
        MyMotApplication.searchManager.searchPressedCallback = new FilterSettedInterface() {
            @Override
            public void onSelected(SearchFilterConfig filterConfig) {
                Log.e("searchPressedCallback","");
            }
        };
    }

}
