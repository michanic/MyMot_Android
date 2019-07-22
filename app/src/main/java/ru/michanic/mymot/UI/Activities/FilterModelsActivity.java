package ru.michanic.mymot.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.FilterModelItem;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ModelsExpandableListAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class FilterModelsActivity extends UniversalActivity {

    private ModelsExpandableListAdapter modelsExpandableListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_models);
        setNavigationTitle("Модель");

        final List<FilterModelItem> topCells = new ArrayList<>();
        boolean allChecked = MyMotApplication.searchManager.getManufacturer() == null && MyMotApplication.searchManager.getModel() == null;
        topCells.add(new FilterModelItem(allChecked));
        DataManager dataManager = new DataManager();

        List<Category> categories = dataManager.getCategories(true);
        for (Manufacturer manufacturer: dataManager.getManufacturers(true)) {
            topCells.add(new FilterModelItem(manufacturer.getName()));
            boolean manufacturerChecked = false;
            Manufacturer selectedManufacturer = MyMotApplication.searchManager.getManufacturer();
            if (selectedManufacturer != null) {
                if (selectedManufacturer.getId() == manufacturer.getId()) {
                    manufacturerChecked = true;
                }
            }
            topCells.add(new FilterModelItem(manufacturer, manufacturerChecked));
            for (Category category: categories) {
                List<Model> models = dataManager.getManufacturerModels(manufacturer, category);
                if (models.size() > 0) {
                    topCells.add(new FilterModelItem(category, models));
                }
            }
        }

        modelsExpandableListAdapter = new ModelsExpandableListAdapter(this, topCells);
        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(modelsExpandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                if (groupPosition == 0) {
                    MyMotApplication.searchManager.setManufacturer(null);
                    MyMotApplication.searchManager.setModel(null);
                    onBackPressed();
                } else {
                    Manufacturer manufacturer = topCells.get(groupPosition).getManufacturer();
                    if (manufacturer != null) {
                        MyMotApplication.searchManager.setManufacturer(manufacturer);
                        onBackPressed();
                    }
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Model model = topCells.get(groupPosition).getModels().get(childPosition);
                MyMotApplication.searchManager.setModel(model);
                onBackPressed();
                return false;
            }
        });

    }

}
