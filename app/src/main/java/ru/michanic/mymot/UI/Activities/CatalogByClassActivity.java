package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import io.realm.RealmList;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ModelsByClassAdapter;
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

        List<SectionModelItem> items = new ArrayList();
        for (Manufacturer manufacturer : dataManager.getManufacturers(true)) {
            items.add(new SectionModelItem(manufacturer.getName()));
            for (Model model : dataManager.getManufacturerModels(manufacturer, category)) {
                items.add(new SectionModelItem(model));
            }
        }

        PinnedSectionListView listView = (PinnedSectionListView) findViewById(R.id.listView);
        ModelsByClassAdapter modelsByClassAdapter = new ModelsByClassAdapter(items);
        listView.setAdapter(modelsByClassAdapter);

        /*
        RealmList<Model> models = category.getModels();
        Log.e("models count", String.valueOf(models.size()));

        for (Model model : models) {
            Log.e("model", model.getName() + " - " + model.getManufacturer().getName());
        }*/


    }

}
