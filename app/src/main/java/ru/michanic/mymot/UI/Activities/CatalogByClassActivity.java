package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Models.Category;
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

        PinnedSectionListView listView = (PinnedSectionListView) findViewById(R.id.listView);
        ModelsByClassAdapter modelsByClassAdapter = new ModelsByClassAdapter(dataManager.getCategories());
        listView.setAdapter(modelsByClassAdapter);


    }

}
