package ru.michanic.mymot.UI.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Interactors.SitesInteractor;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.SearchResultsAdapter;

public class SearchResultsActivity extends UniversalActivity {

    private RecyclerView resultView;
    private GridLayoutManager glm;
    private SearchResultsAdapter searchAdapter;
    private ProgressBar progressBar;
    private SitesInteractor sitesInteractor = new SitesInteractor();
    private SearchFilterConfig filterConfig;
    private int currentPage = 1;
    private Boolean loading = false;
    private Boolean isLastPage = false;

    private List<Advert> loadedAdverts = new ArrayList<Advert>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setNavigationTitle("Результаты поиска");

        resultView = (RecyclerView) findViewById(R.id.resultsView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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


        ClickListener advertPressed = new ClickListener() {
            @Override
            public void onClick(int section, int row) {
                Intent adveryActivity = new Intent(getApplicationContext(), AdvertActivity.class);
                adveryActivity.putExtra("advertId", loadedAdverts.get(row).getId());
                startActivity(adveryActivity);
            }
        };

        glm = new GridLayoutManager(this, 1);
        resultView.setLayoutManager(glm);

        searchAdapter = new SearchResultsAdapter(this, loadedAdverts, advertPressed);
        resultView.setAdapter(searchAdapter);

        resultView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastvisibleitemposition = glm.findLastVisibleItemPosition();
                if (lastvisibleitemposition == searchAdapter.getItemCount() - 1) {
                    if (!loading && !isLastPage) {
                        loading = true;
                        loadMore();
                    }
                }
            }
        });

        MyMotApplication.searchManager.filterClosedCallback = new FilterSettedInterface() {
            @Override
            public void onSelected(SearchFilterConfig filterConfig) {
                reloadResults();
            }
        };

        reloadResults();
    }

    private void reloadResults() {
        filterConfig = MyMotApplication.searchManager.getFilterConfig();
        loadedAdverts.clear();
        searchAdapter.notifyDataSetChanged();
        currentPage = 1;
        progressBar.setVisibility(View.VISIBLE);
        loadMore();
    }

    private void loadMore() {
        loading = true;

        sitesInteractor.searchAdverts(currentPage, filterConfig, new LoadingAdvertsInterface() {
            @Override
            public void onLoaded(List<Advert> adverts, boolean loadMore) {
                progressBar.setVisibility(View.GONE);
                Log.e("onLoaded", String.valueOf(adverts.size()));
                loadedAdverts.addAll(adverts);
                searchAdapter.notifyDataSetChanged();
                loading = false;
                isLastPage = !loadMore;
                currentPage ++;
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
            filterActivity.putExtra("goBackOnSearch", true);
            startActivity(filterActivity);
        }
        return super.onOptionsItemSelected(item);
    }

}
