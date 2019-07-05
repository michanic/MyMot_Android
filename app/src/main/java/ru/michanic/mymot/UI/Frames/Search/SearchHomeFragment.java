package ru.michanic.mymot.UI.Frames.Search;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Interactors.SitesInteractor;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.Source;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Activities.AdvertActivity;
import ru.michanic.mymot.UI.Activities.CatalogByClassActivity;
import ru.michanic.mymot.UI.Adapters.SearchMainAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class SearchHomeFragment extends Fragment {

    private Source currentSource;
    private boolean avitoLoadMoreAvailable = true;
    private SitesInteractor sitesInteractor = new SitesInteractor();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_home, null);

        DataManager dataManager = new DataManager();

        TextView titleView = (TextView) rootView.findViewById(R.id.resultsTitle);
        final GridView resultsGridView = (GridView) rootView.findViewById(R.id.resultsView);
        titleView.setTypeface(Font.suzuki);

        /*LinearLayoutManager classesLayoutManager = new LinearLayoutManager(getActivity());
        classesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        resultsRecyclerView.setLayoutManager(classesLayoutManager);*/

        if (currentSource == null) {
            currentSource = new Source(SourceType.AVITO);
        } else {
            if (currentSource.getType() == SourceType.AVITO) {
                currentSource = new Source(SourceType.AUTO_RU);
            } else {
                currentSource = new Source(SourceType.AVITO);
            }
        }
        //currentSource.setRegion("");


        sitesInteractor.loadFeedAdverts(currentSource, new LoadingAdvertsInterface() {
            @Override
            public void onLoaded(List<Advert> adverts, boolean loadMore) {

                ClickListener advertPressed = new ClickListener() {
                    @Override
                    public void onClick(int section, int row) {
                        Intent adveryActivity = new Intent(getActivity(), AdvertActivity.class);
                        //adveryActivity.putExtra("advertId", classes.get(row).getId());
                        getActivity().startActivity(adveryActivity);
                    }
                };

                SearchMainAdapter searchAdapter = new SearchMainAdapter(getActivity(), adverts, advertPressed);
                resultsGridView.setAdapter(searchAdapter);

            }

            @Override
            public void onFailed() {

            }
        });

        return rootView;
    }

}
