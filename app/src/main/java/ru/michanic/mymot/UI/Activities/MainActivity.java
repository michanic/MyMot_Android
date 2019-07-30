package ru.michanic.mymot.UI.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import io.realm.Realm;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.SectionItemsListAdapter;
import ru.michanic.mymot.UI.Frames.Catalog.CatalogHomeFragment;
import ru.michanic.mymot.UI.Frames.Favourites.FavouritesHomeFragment;
import ru.michanic.mymot.UI.Frames.Info.InfoHomeFragment;
import ru.michanic.mymot.UI.Frames.Search.SearchHomeFragment;
import ru.michanic.mymot.Utils.DataManager;
import ru.michanic.mymot.Utils.TypefaceSpan;

public class MainActivity extends UniversalActivity {
    //private TextView mTextMessage;

    private BottomNavigationView navView;
    private MenuItem searchIcon;
    private MenuItem filterIcon;
    private PinnedSectionListView searchResultsView;
    private SectionItemsListAdapter searchResultsAdapter;

    private DataManager dataManager = new DataManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            setNavigationTitle(item.getTitle().toString());

            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    fragment = new CatalogHomeFragment();
                    showSearchIcon(false);
                    showFilterIcon(false);
                    break;
                case R.id.navigation_search:
                    fragment = new SearchHomeFragment();
                    showSearchIcon(true);
                    showFilterIcon(true);
                    break;
                case R.id.navigation_favourites:
                    fragment = new FavouritesHomeFragment();
                    showSearchIcon(false);
                    showFilterIcon(false);
                    break;
                case R.id.navigation_info:
                    fragment = new InfoHomeFragment();
                    showSearchIcon(false);
                    showFilterIcon(false);
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rootActivity = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new CatalogHomeFragment());
        setNavigationTitle(getResources().getString(R.string.title_catalog));

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        searchResultsView = (PinnedSectionListView) findViewById(R.id.listView);
        searchResultsView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_filter_menu, menu);

        searchIcon = menu.findItem(R.id.search_icon);
        filterIcon = menu.findItem(R.id.filter_icon);
        showSearchIcon(false);
        showFilterIcon(false);

        SearchView searchView = (SearchView) searchIcon.getActionView();
        searchView.setQueryHint("Модель или объем");
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("newText", newText);
                showSearchResults(newText);
                return false;
            }
        });

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

    private void showSearchIcon(boolean show) {
        if (searchIcon != null) {
            searchIcon.setVisible(show);
        }
    }

    private void showFilterIcon(boolean show) {
        if (filterIcon != null) {
            filterIcon.setVisible(show);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void showSearchResults(String searchText) {
        if (searchText.length() < 1) {
            searchResultsView.setVisibility(View.GONE);
        } else {
            List<Model> models = dataManager.searchModelsByName(searchText);
            if (models.size() > 0) {
                final List<SectionModelItem> items = new ArrayList();
                for (Model model : models) {
                    items.add(new SectionModelItem(model));
                }

                if (searchResultsAdapter != null) {
                    searchResultsAdapter.setItems(items);
                    searchResultsAdapter.notifyDataSetChanged();
                } else {
                    searchResultsAdapter = new SectionItemsListAdapter(items);
                    searchResultsView.setAdapter(searchResultsAdapter);
                }
                searchResultsView.setVisibility(View.VISIBLE);
                searchResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Model model = items.get(position).getModel();
                        MyMotApplication.searchManager.setModel(model);
                        Intent searchResultsActivity = new Intent(getApplicationContext(), SearchResultsActivity.class);
                        startActivity(searchResultsActivity);
                    /*if (model != null) {
                        Intent catalogModelActivity = new Intent(getApplicationContext(), CatalogModelActivity.class);
                        catalogModelActivity.putExtra("modelId", model.getId());
                        startActivity(catalogModelActivity);
                    }*/
                    }
                });
            } else {
                searchResultsView.setVisibility(View.GONE);
            }
        }
    }


}
