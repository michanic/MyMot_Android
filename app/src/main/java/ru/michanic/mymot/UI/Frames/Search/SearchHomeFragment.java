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

        new AsyncRequest().execute("https://www.avito.ru/rossiya/mototsikly_i_mototehnika/mototsikly");

        return rootView;
    }

    class AsyncRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg) {
            String path = arg[0];
            Document doc = null;
            try {
                doc = Jsoup.connect(path).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String text = doc.html();
            Log.e("Jsoup", "show elements");

            Elements elements = doc.getElementsByClass("js-catalog-item-enum");

            for (Element element: elements) {

                String id = element.attr("data-item-id");
                String title = element.select("a.item-description-title-link span").text();
                //TODO
                //guard title.checkForExteption() else { return nil }

                String city = element.select(".item_table-description .data p:eq(1)").text();
                String link = element.select(".item-description-title-link").attr("href");
                String priceText = element.select("span.price").text();
                String previewImage = element.selectFirst("img.large-picture-img").attr("src");

                Log.e("Jsoup", city + " - " + title);
                Log.e("Jsoup", priceText);
                Log.e("Jsoup", previewImage);
                Log.e("Jsoup", "-------------------------------");
            }


            return text;
        }

        @Override
        protected void onPostExecute(String text) {
            super.onPostExecute(text);
            //Log.e("Jsoup", text);
        }
    }

}
