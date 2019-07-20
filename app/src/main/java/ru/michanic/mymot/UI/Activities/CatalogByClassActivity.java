package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.SectionItemsListAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class CatalogByClassActivity extends UniversalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_by_class);

        DataManager dataManager = new DataManager();

        Intent intent = getIntent();
        int classId = intent.getIntExtra("classId", 0);
        Category category = dataManager.getCategoryById(classId);

        setNavigationTitle(category.getName());

        final List<SectionModelItem> items = new ArrayList();
        for (Manufacturer manufacturer : dataManager.getManufacturers(true)) {
            List<Model> models = dataManager.getManufacturerModels(manufacturer, category);
            if (models.size() > 0) {
                items.add(new SectionModelItem(manufacturer.getName()));
                for (Model model : models) {
                    items.add(new SectionModelItem(model));
                }
            }
        }

        PinnedSectionListView listView = (PinnedSectionListView) findViewById(R.id.listView);
        SectionItemsListAdapter sectionItemsListAdapter = new SectionItemsListAdapter(items);
        listView.setAdapter(sectionItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model model = items.get(position).getModel();
                if (model != null) {

                    Intent catalogModelActivity = new Intent(getApplicationContext(), CatalogModelActivity.class);
                    catalogModelActivity.putExtra("modelId", model.getId());
                    startActivity(catalogModelActivity);

                }
            }
        });

    }

}
