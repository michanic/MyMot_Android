package ru.michanic.mymot.UI.Frames.Favourites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Activities.AdvertActivity;
import ru.michanic.mymot.UI.Adapters.AdvertsListAdapter;

public class FavouriteAdvertsFragment extends Fragment {

    private TabLayout tabs;
    private GridLayoutManager glm;
    private RecyclerView resultView;
    private TextView placeholder;
    private AdvertsListAdapter advertsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite_adverts, null);
        resultView = (RecyclerView) rootView.findViewById(R.id.resultsView);
        placeholder = (TextView) rootView.findViewById(R.id.listIsEmpty);
        placeholder.setTypeface(Font.oswald);

        glm = new GridLayoutManager(getActivity(), 1);
        resultView.setLayoutManager(glm);

        loadAdverts();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAdverts();
    }

    private void loadAdverts() {
        final List<Advert> adverts = MyMotApplication.dataManager.getFavouriteAdverts();
        ClickListener advertPressed = new ClickListener() {
            @Override
            public void onClick(int section, int row) {
                Intent adveryActivity = new Intent(getContext(), AdvertActivity.class);
                adveryActivity.putExtra("advertId", adverts.get(row).getId());
                startActivity(adveryActivity);
            }
        };
        advertsAdapter = new AdvertsListAdapter(getContext(), adverts, advertPressed);
        resultView.setAdapter(advertsAdapter);
    }

}
