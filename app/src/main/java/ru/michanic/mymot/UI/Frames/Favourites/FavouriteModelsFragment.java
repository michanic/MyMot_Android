package ru.michanic.mymot.UI.Frames.Favourites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Activities.CatalogModelActivity;
import ru.michanic.mymot.UI.Adapters.SectionItemsListAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class FavouriteModelsFragment extends Fragment {

    private TabLayout tabs;

    private SectionItemsListAdapter sectionItemsListAdapter;
    private PinnedSectionListView listView;
    private TextView placeholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite_models, null);
        listView = (PinnedSectionListView) rootView.findViewById(R.id.listView);
        placeholder = (TextView) rootView.findViewById(R.id.listIsEmpty);
        placeholder.setTypeface(Font.oswald);
        loadModels();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadModels();
    }

    private void loadModels() {
        final List<SectionModelItem> items = new ArrayList();
        List<Model> models = MyMotApplication.dataManager.getFavouriteModels();
        if (models.size() > 0) {
            for (Model model : models) {
                items.add(new SectionModelItem(model));
            }

            sectionItemsListAdapter = new SectionItemsListAdapter(items);
            listView.setAdapter(sectionItemsListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Model model = items.get(position).getModel();
                    if (model != null) {
                        Intent catalogModelActivity = new Intent(getContext(), CatalogModelActivity.class);
                        catalogModelActivity.putExtra("modelId", model.getId());
                        startActivity(catalogModelActivity);
                    }
                }
            });
            listView.setVisibility(View.VISIBLE);
            sectionItemsListAdapter.notifyDataSetChanged();
        } else {
            listView.setVisibility(View.GONE);
        }
    }

}
