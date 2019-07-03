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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Activities.AdvertActivity;
import ru.michanic.mymot.UI.Activities.CatalogByClassActivity;
import ru.michanic.mymot.UI.Adapters.SearchMainAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class SearchHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_home, null);

        DataManager dataManager = new DataManager();

        TextView titleView = (TextView) rootView.findViewById(R.id.resultsTitle);
        GridView resultsGridView = (GridView) rootView.findViewById(R.id.resultsView);
        titleView.setTypeface(Font.suzuki);

        /*LinearLayoutManager classesLayoutManager = new LinearLayoutManager(getActivity());
        classesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        resultsRecyclerView.setLayoutManager(classesLayoutManager);*/

        List<Advert> adverts = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Advert advert = new Advert();
            advert.setTitle(String.valueOf(i));
            adverts.add(advert);
        }

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

        new AsyncRequest().execute("123", "/ajax", "foo=bar");

        return rootView;
    }

    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://my-mot.ru/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String text = doc.html();
            return text;
        }

        @Override
        protected void onPostExecute(String text) {
            super.onPostExecute(text);
            Log.e("Jsoup", text);
        }
    }

}
