package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.SectionItemsListAdapter;

public class FilterActivity extends UniversalActivity {

    private boolean filterChanged = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setNavigationTitle("Фильтр");

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setTypeface(Font.progress);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void createCells() {
        final List<SectionModelItem> items = new ArrayList();
        items.add(new SectionModelItem("Регион поиска"));
        items.add(new SectionModelItem(MyMotApplication.searchManager.getRegionTitle(), null));

        items.add(new SectionModelItem("Модель"));
        items.add(new SectionModelItem(MyMotApplication.searchManager.getModelTitle(), null));

        items.add(new SectionModelItem("Цена"));
        items.add(new SectionModelItem("От", "-"));
        items.add(new SectionModelItem("До", "-"));

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
