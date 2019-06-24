package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.R;
import ru.michanic.mymot.Utils.DataManager;

public class CatalogModelActivity extends UniversalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_model);

        DataManager dataManager = new DataManager();

        Intent intent = getIntent();
        int modelId = intent.getIntExtra("modelId", 0);
        Model model = dataManager.getModelById(modelId);

        Log.e("model", model.getName());

    }

}
