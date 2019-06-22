package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.realm.RealmList;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.R;
import ru.michanic.mymot.Utils.DataManager;

public class CatalogByManufacturerActivity extends UniversalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_by_manufacturer);

        DataManager dataManager = new DataManager();

        Intent intent = getIntent();
        int manufacturerId = intent.getIntExtra("manufacturerId", 0);
        Manufacturer manufacturer = dataManager.getManufacturerById(manufacturerId);

        setNavigationTitle(manufacturer.getName());

        RealmList<Model> models = manufacturer.getModels();
        Log.e("models count", String.valueOf(models.size()));

        for (Model model : models) {
            Log.e("model", model.getName() + " - " + model.getCategory().getName());
        }

    }

}
