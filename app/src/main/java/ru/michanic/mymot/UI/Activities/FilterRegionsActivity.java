package ru.michanic.mymot.UI.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.List;

import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Protocols.LoadingInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.RegionsExpandableListAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class FilterRegionsActivity extends UniversalActivity {

    private RegionsExpandableListAdapter regionsExpandableListAdapter;
    private ApiInteractor apiInteractor = new ApiInteractor();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_regions);
        setNavigationTitle("Регион");

        final List<Location> regions = new DataManager().getRegions();
        regionsExpandableListAdapter = new RegionsExpandableListAdapter(regions, this);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(regionsExpandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Location region = regions.get(groupPosition - 1);
                Log.e("getCitiesCount", String.valueOf(region.getCitiesCount()));
                if (region.getCitiesCount() == 0) {
                    apiInteractor.loadRegionCities(region, new LoadingInterface() {
                        @Override
                        public void onLoaded() {

                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                }
                return false;
            }
        });
    }

}
