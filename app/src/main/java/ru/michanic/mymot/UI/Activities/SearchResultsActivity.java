package ru.michanic.mymot.UI.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Interactors.SitesInteractor;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.R;

public class SearchResultsActivity extends UniversalActivity {

    private PinnedSectionListView searchResultsView;
    private SitesInteractor sitesInteractor = new SitesInteractor();
    private SearchFilterConfig filterConfig;
    private int currentPage = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setNavigationTitle("Результаты поиска");

        searchResultsView = (PinnedSectionListView) findViewById(R.id.listView);
        searchResultsView.setVisibility(View.GONE);

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

        filterConfig = MyMotApplication.searchManager.getFilterConfig();

        sitesInteractor.searchAdverts(currentPage, filterConfig, new LoadingAdvertsInterface() {
            @Override
            public void onLoaded(List<Advert> adverts, boolean loadMore) {
                Log.e("onLoaded", String.valueOf(adverts.size()));
                for (Advert advert: adverts) {
                    Log.e(advert.getId(), advert.getTitle());
                }
            }
            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_icon) {
            Intent filterActivity = new Intent(getApplicationContext(), FilterActivity.class);
            startActivity(filterActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchResults() {

    }

}
