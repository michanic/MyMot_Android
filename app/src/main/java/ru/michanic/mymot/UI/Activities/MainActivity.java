package ru.michanic.mymot.UI.Activities;

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
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Frames.Catalog.CatalogHomeFragment;
import ru.michanic.mymot.UI.Frames.Favourites.FavouritesHomeFragment;
import ru.michanic.mymot.UI.Frames.Info.InfoHomeFragment;
import ru.michanic.mymot.UI.Frames.Search.SearchHomeFragment;
import ru.michanic.mymot.Utils.DataManager;
import ru.michanic.mymot.Utils.TypefaceSpan;

public class MainActivity extends UniversalActivity {
    //private TextView mTextMessage;

    private MenuItem filterIcon;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            setNavigationTitle(item.getTitle().toString());

            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    fragment = new CatalogHomeFragment();
                    showFilterIcon(false);
                    break;
                case R.id.navigation_search:
                    fragment = new SearchHomeFragment();
                    showFilterIcon(true);
                    break;
                case R.id.navigation_favourites:
                    fragment = new FavouritesHomeFragment();
                    showFilterIcon(false);
                    break;
                case R.id.navigation_info:
                    fragment = new InfoHomeFragment();
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

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("tap", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        filterIcon = menu.getItem(0);
        showFilterIcon(false);
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

    private void showFilterIcon(boolean show) {
        if (filterIcon != null) {
            filterIcon.setVisible(show);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
