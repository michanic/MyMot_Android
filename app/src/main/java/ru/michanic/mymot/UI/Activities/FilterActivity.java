package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.SectionItemsListAdapter;

public class FilterActivity extends UniversalActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setNavigationTitle("Фильтр");

        final List<SectionModelItem> items = new ArrayList();
        items.add(new SectionModelItem("Регион поиска"));
        items.add(new SectionModelItem("По всей России", null));

        items.add(new SectionModelItem("Модель"));
        items.add(new SectionModelItem("Все мотоциклы", null));

        items.add(new SectionModelItem("Цена"));
        items.add(new SectionModelItem("От", "-"));
        items.add(new SectionModelItem("До", "-"));

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setTypeface(Font.progress);

        PinnedSectionListView listView = (PinnedSectionListView) findViewById(R.id.listView);
        SectionItemsListAdapter sectionItemsListAdapter = new SectionItemsListAdapter(items);
        listView.setAdapter(sectionItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}
