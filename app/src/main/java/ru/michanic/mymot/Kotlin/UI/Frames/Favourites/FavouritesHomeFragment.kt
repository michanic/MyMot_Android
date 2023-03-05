package ru.michanic.mymot.UI.Frames.Favourites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.michanic.mymot.R;
import ru.michanic.mymot.Kotlin.UI.Adapters.FavouritesPagerAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class FavouritesHomeFragment extends Fragment {

    private TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourites_home, null);
        DataManager dataManager = new DataManager();

        Fragment[] fragments = new Fragment[2];
        fragments[0] = new FavouriteModelsFragment();
        fragments[1] = new FavouriteAdvertsFragment();

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.base_pager);
        pager.setAdapter(new FavouritesPagerAdapter(getChildFragmentManager(), fragments));

        tabs = (TabLayout) rootView.findViewById(R.id.base_tabs);
        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).select();

        //--> Adding tabs
        for (int i = 0; i < tabs.getTabCount(); i++) {
            switch (i) {
                case 0:
                    tabs.getTabAt(i).setText("Модели");
                    break;
                case 1:
                    tabs.getTabAt(i).setText("Объявления");
                    break;
                default:
                    tabs.getTabAt(i).setText("Unknown");
                    break;
            }
        }

        return rootView;
    }

}
