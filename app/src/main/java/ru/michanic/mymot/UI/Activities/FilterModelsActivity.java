package ru.michanic.mymot.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Protocols.LoadingInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ModelsExpandableListAdapter;
import ru.michanic.mymot.UI.Adapters.RegionsExpandableListAdapter;

public class FilterModelsActivity extends UniversalActivity {

    private ModelsExpandableListAdapter modelsExpandableListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_models);
        setNavigationTitle("Модель");

        modelsExpandableListAdapter = new ModelsExpandableListAdapter(this);
        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(modelsExpandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {

                return false;
            }
        });

    }

}
