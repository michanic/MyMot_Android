package ru.michanic.mymot.UI.Frames.Favourites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.michanic.mymot.R;
import ru.michanic.mymot.Utils.DataManager;

public class FavouritesHomeFragment extends Fragment {

    private TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalog_home, null);
        DataManager dataManager = new DataManager();

        /*
        pager = (ViewPager) findViewById(R.id.base_pager);
        fm = getSupportFragmentManager();

        tabs = (TabLayout) rootView.findViewById(R.id.base_tabs);
        //tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).select();

        //--> Adding tabs
        for (int i = 0; i < tabs.getTabCount(); i++) {
            switch (i) {
                case 0:
                    tabs.getTabAt(i).setText("Объявления");
                    break;
                case 1:
                    tabs.getTabAt(i).setText("Модели");
                    break;
                default:
                    tabs.getTabAt(i).setText("Unknown");
                    break;
            }
        }*/

        return rootView;
    }

}
