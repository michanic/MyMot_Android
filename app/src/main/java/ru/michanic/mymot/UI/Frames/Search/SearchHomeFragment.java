package ru.michanic.mymot.UI.Frames.Search;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.Models.Source;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.Protocols.FilterSettedInterface;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Activities.AdvertActivity;
import ru.michanic.mymot.UI.Activities.CatalogByClassActivity;
import ru.michanic.mymot.UI.Adapters.SearchMainAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class SearchHomeFragment extends Fragment {

    private Source currentSource;
    private boolean avitoLoadMoreAvailable = true;
    private boolean loadMoreAvailable = false;
    private DataManager dataManager = new DataManager();

    private SitesInteractor sitesInteractor = new SitesInteractor();
    private SearchMainAdapter searchAdapter;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager glm;
    private ProgressBar progressBar;

    private Boolean loading = false;
    private Boolean isLastPage = false;

    private TextView titleView;
    private RecyclerView resultView;
    private List<Advert> loadedAdverts = new ArrayList<Advert>();
    private  SearchFilterConfig filterConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_home, null);

        //Log.e("saved adverts", String.valueOf(new DataManager().getFavouriteAdverts().size()));

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        titleView = (TextView) rootView.findViewById(R.id.resultsTitle);
        resultView = (RecyclerView) rootView.findViewById(R.id.resultsView);
        titleView.setTypeface(Font.oswald);

        glm = new GridLayoutManager(getActivity(), 2);
        resultView.setLayoutManager(glm);

        mLayoutManager = new LinearLayoutManager(getActivity());
        //resultView.setLayoutManager(mLayoutManager);
        resultView.setHasFixedSize(false);

        ClickListener advertPressed = new ClickListener() {
            @Override
            public void onClick(int section, int row) {
                Intent adveryActivity = new Intent(getActivity(), AdvertActivity.class);
                adveryActivity.putExtra("advertId", loadedAdverts.get(row).getId());
                getActivity().startActivity(adveryActivity);
            }
        };

        searchAdapter = new SearchMainAdapter(getActivity(), loadedAdverts, advertPressed);
        resultView.setAdapter(searchAdapter);

        resultView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastvisibleitemposition = glm.findLastVisibleItemPosition();
                if (lastvisibleitemposition == searchAdapter.getItemCount() - 1) {

                    if (!loading && !isLastPage) {
                        loading = true;
                        loadMore();
                    }
                }
            }
        });
        progressBar.setVisibility(View.VISIBLE);

        reloadResults();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMotApplication.searchManager.filterClosedCallback = new FilterSettedInterface() {
            @Override
            public void onSelected(SearchFilterConfig filterConfig) {
                reloadResults();
            }
        };
    }

    private void reloadResults() {
        filterConfig = MyMotApplication.searchManager.getFilterConfig();
        titleView.setText(filterConfig.getMainSearchTitle());
        loadedAdverts.clear();
        currentSource = null;
        loadMore();
    }

    private void loadMore() {
        loading = true;

        if (currentSource == null) {
            currentSource = new Source(SourceType.AVITO, filterConfig.getPriceFrom(), filterConfig.getPriceFor(), filterConfig.getSelectedRegion());
            currentSource.setPage(1);
        } else {
            //currentSource.page += 1;
            if (currentSource.getType() == SourceType.AVITO && avitoLoadMoreAvailable) {
                currentSource.updateTypeAndRegion(SourceType.AUTO_RU, filterConfig.getSelectedRegion());
            } else {
                if (avitoLoadMoreAvailable) {
                    currentSource.incrementPage();
                    currentSource.updateTypeAndRegion(SourceType.AVITO, filterConfig.getSelectedRegion());
                } else {
                    currentSource.updateTypeAndRegion(SourceType.AUTO_RU, filterConfig.getSelectedRegion());
                    currentSource.incrementPage();
                }
            }
        }

        sitesInteractor.loadFeedAdverts(currentSource, new LoadingAdvertsInterface() {
            @Override
            public void onLoaded(List<Advert> adverts, boolean loadMore) {
                progressBar.setVisibility(View.GONE);
                Log.e("onLoaded", String.valueOf(adverts.size()));

                loadedAdverts.addAll(adverts);
                searchAdapter.notifyDataSetChanged();
                loading = false;

                if (currentSource.getType() == SourceType.AVITO) {
                    avitoLoadMoreAvailable = loadMore;
                    loadMoreAvailable = true;
                } else {
                    loadMoreAvailable = loadMore;
                }

            }

            @Override
            public void onFailed() {

            }
        });

    }

}
