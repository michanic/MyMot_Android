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
import ru.michanic.mymot.MyMotApplication;
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

        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(regionsExpandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                if (groupPosition == 0) {
                    MyMotApplication.searchManager.setRegion(null);
                } else {
                    Location region = regions.get(groupPosition - 1);
                    //Log.e("getCitiesCount", String.valueOf(region.getCitiesCount()));
                    //regionsExpandableListAdapter.notifyDataSetChanged();
                    if (region.getCitiesCount() == 0) {
                        apiInteractor.loadRegionCities(region, new LoadingInterface() {
                            @Override
                            public void onLoaded() {
                                Log.e("expandGroup", String.valueOf(groupPosition));
                                expandableListView.collapseGroup(groupPosition);
                                expandableListView.expandGroup(groupPosition);
                            }
                            @Override
                            public void onFailed() {

                            }
                        });
                    }
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (childPosition == 0) {
                    Location region = regions.get(groupPosition - 1);
                    MyMotApplication.searchManager.setRegion(region);
                } else {
                    Location region = regions.get(groupPosition - 1);
                    Location city = region.getCities().get(childPosition - 1);
                    MyMotApplication.searchManager.setRegion(city);
                }
                return false;
            }
        });
    }

}
