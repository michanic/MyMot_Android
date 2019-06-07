package ru.michanic.mymot;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import ru.michanic.mymot.UI.Frames.Catalog.CatalogHomeFragment;
import ru.michanic.mymot.UI.Frames.Favourites.FavouritesHomeFragment;
import ru.michanic.mymot.UI.Frames.Info.InfoHomeFragment;
import ru.michanic.mymot.UI.Frames.Search.SearchHomeFragment;

public class MainActivity extends AppCompatActivity {
    //private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new CatalogHomeFragment());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
