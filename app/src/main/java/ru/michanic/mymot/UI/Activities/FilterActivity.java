package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.SectionItemsListAdapter;

public class FilterActivity extends UniversalActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setNavigationTitle("Фильтр");

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setTypeface(Font.progress);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchResultsActivity = new Intent(getApplicationContext(), SearchResultsActivity.class);
                startActivity(searchResultsActivity);
            }
        });

        createCells();

        MyMotApplication.searchManager.filterUpdated = new FilterSettedInterface() {
            @Override
            public void onSelected(SearchFilterConfig filterConfig) {
                createCells();
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyMotApplication.searchManager.backPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MyMotApplication.searchManager.backPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createCells() {
        final List<SectionModelItem> items = new ArrayList();
        items.add(new SectionModelItem("Регион поиска"));
        items.add(new SectionModelItem(MyMotApplication.searchManager.getRegionTitle(), null));

        items.add(new SectionModelItem("Модель"));
        items.add(new SectionModelItem(MyMotApplication.searchManager.getModelTitle(), null));

        items.add(new SectionModelItem("Цена"));
        String priceFromString = "";
        int priceFromInt = MyMotApplication.searchManager.getPriceFrom();
        if (priceFromInt != 0) {
            priceFromString = String.valueOf(priceFromInt);
        }
        items.add(new SectionModelItem(SectionModelItem.PRICE_FROM_NAME, priceFromString));

        String priceForString = "";
        int priceForInt = MyMotApplication.searchManager.getPriceFor();
        if (priceForInt != 0) {
            priceForString = String.valueOf(priceForInt);
        }
        items.add(new SectionModelItem(SectionModelItem.PRICE_FOR_NAME, priceForString));

        PinnedSectionListView listView = (PinnedSectionListView) findViewById(R.id.listView);
        SectionItemsListAdapter sectionItemsListAdapter = new SectionItemsListAdapter(items);
        listView.setAdapter(sectionItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent regionsActivity = new Intent(getApplicationContext(), FilterRegionsActivity.class);
                        startActivity(regionsActivity);
                        break;
                    case 3:
                        Intent modelsActivity = new Intent(getApplicationContext(), FilterModelsActivity.class);
                        startActivity(modelsActivity);
                        break;
                }
            }
        });
    }

}
