package ru.michanic.mymot.UI.Activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
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
import ru.michanic.mymot.Utils.TypefaceSpan;

public class MainActivity extends UniversalActivity {
    //private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            setNavigationTitle(item.getTitle().toString());

            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    fragment = new CatalogHomeFragment();
                    break;
                case R.id.navigation_search:
                    fragment = new SearchHomeFragment();
                    break;
                case R.id.navigation_favourites:
                    fragment = new FavouritesHomeFragment();
                    break;
                case R.id.navigation_info:
                    fragment = new InfoHomeFragment();
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

        /*Realm realm = Realm.getDefaultInstance();
        ArrayList<Location> locations = new ArrayList<Location>(realm.where(Location.class).findAll());

        Log.e("finish loading", "finish loading");
        for (Location region : locations) {
            Log.e("finish loading", region.getName());
        }*/

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
