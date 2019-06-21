package ru.michanic.mymot.UI.Frames.Catalog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Activities.CatalogByClassActivity;
import ru.michanic.mymot.UI.Activities.CatalogByManufacturerActivity;
import ru.michanic.mymot.UI.Adapters.ClassesSliderAdapter;
import ru.michanic.mymot.UI.Adapters.ManufacturersSliderAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class CatalogHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.catalog_home, null);

        DataManager dataManager = new DataManager();

        TextView classesTitleView = (TextView) rootView.findViewById(R.id.classesTitle);
        RecyclerView classesRecyclerView = (RecyclerView) rootView.findViewById(R.id.classesSlider);

        TextView manufacturersTitleView = (TextView) rootView.findViewById(R.id.manufacturersTitle);
        RecyclerView manufacturersRecyclerView = (RecyclerView) rootView.findViewById(R.id.manufacturersSlider);


        classesTitleView.setTypeface(Font.suzuki);

        LinearLayoutManager classesLayoutManager = new LinearLayoutManager(getActivity());
        classesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        classesRecyclerView.setLayoutManager(classesLayoutManager);

        final List<Category> classes = dataManager.getCategories();
        ClickListener classPressed = new ClickListener() {
            @Override
            public void onClick(int section, int row) {
                Intent catalogByClassActivity = new Intent(getActivity(), CatalogByClassActivity.class);
                catalogByClassActivity.putExtra("classId", classes.get(row).getId());
                getActivity().startActivity(catalogByClassActivity);
            }
        };
        ClassesSliderAdapter classesAdapter = new ClassesSliderAdapter(getActivity(), classes, classPressed);
        classesRecyclerView.setAdapter(classesAdapter);



        manufacturersTitleView.setTypeface(Font.suzuki);

        LinearLayoutManager manufacturersLayoutManager = new LinearLayoutManager(getActivity());
        manufacturersLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manufacturersRecyclerView.setLayoutManager(manufacturersLayoutManager);

        final List<Manufacturer> manufacturers = dataManager.getManufacturers();
        ClickListener manufacturerPressed = new ClickListener() {
            @Override
            public void onClick(int section, int row) {
                Intent catalogByManufacturerActivity = new Intent(getActivity(), CatalogByManufacturerActivity.class);
                catalogByManufacturerActivity.putExtra("manufacturerId", manufacturers.get(row).getId());
                getActivity().startActivity(catalogByManufacturerActivity);
            }
        };
        ManufacturersSliderAdapter manufacturersAdapter = new ManufacturersSliderAdapter(getActivity(), manufacturers, manufacturerPressed);
        manufacturersRecyclerView.setAdapter(manufacturersAdapter);


        return rootView;
    }

}
