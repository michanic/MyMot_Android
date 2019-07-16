package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.Interactors.SitesInteractor;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.AdvertDetails;
import ru.michanic.mymot.R;
import ru.michanic.mymot.Utils.DataManager;

public class AdvertActivity extends UniversalActivity {

    private ProgressBar loadingIndicator;
    private ScrollView contentView;
    SitesInteractor sitesInteractor = new SitesInteractor();
    Advert advert;
    AdvertDetails advertDetails;
    DataManager dataManager = new DataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        Intent intent = getIntent();
        String advertId = intent.getStringExtra("advertId");
        advert = dataManager.getAdvertById(advertId);

        Log.e("advert title", advert.getTitle());

        contentView = (ScrollView) findViewById(R.id.content_view);
        contentView.setVisibility(View.GONE);

        loadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        loadingIndicator.setVisibility(View.VISIBLE);

        //loadAdvertDetails(advertId);
    }

    private void loadAdvertDetails(final int advertId) {

        //sitesInteractor.loa


    }


}
