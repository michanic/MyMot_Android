package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shivam.library.imageslider.ImageSlider;

import java.util.List;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.Interactors.SitesInteractor;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.AdvertDetails;
import ru.michanic.mymot.Protocols.LoadingAdvertDetailsInterface;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ImagesSliderAdapter;
import ru.michanic.mymot.UI.NonScrollListView;
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

        setNavigationTitle(advert.getTitle());

        contentView = (ScrollView) findViewById(R.id.content_view);
        contentView.setVisibility(View.GONE);

        loadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        loadingIndicator.setVisibility(View.VISIBLE);

        loadAdvertDetails();
    }

    private void loadAdvertDetails() {

        sitesInteractor.loadAdvertDetails(advert, new LoadingAdvertDetailsInterface() {
            @Override
            public void onLoaded(AdvertDetails details) {

                advertDetails = details;
                loadingIndicator.setVisibility(View.GONE);
                fillProperties();
                contentView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed() {
                showNoConnectionDialog(new NoConnectionRepeatInterface() {
                    @Override
                    public void repeatPressed() {
                        loadAdvertDetails();
                    }
                });
            }
        });

    }

    private void fillProperties() {

        ImageSlider imagesSlider = (ImageSlider)findViewById(R.id.imagesSlider);
        TextView titleLabel = (TextView) findViewById(R.id.titleLabel);
        TextView cityLabel = (TextView) findViewById(R.id.cityLabel);
        TextView priceLabel = (TextView) findViewById(R.id.priceLabel);
        TextView dateLabel = (TextView) findViewById(R.id.dateLabel);
        TextView aboutLabel = (TextView) findViewById(R.id.aboutLabel);
        NonScrollListView parametersListView  = (NonScrollListView) findViewById(R.id.parametersView);
        Button callButton = (Button) findViewById(R.id.callButton);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        imagesSlider.getLayoutParams().height = (int) ((float)width * 0.75);

        titleLabel.setTypeface(Font.suzuki);
        callButton.setTypeface(Font.progress);

        titleLabel.setText(advert.getTitle());
        cityLabel.setText(advert.getCity());
        dateLabel.setText(advertDetails.getDate());
        priceLabel.setText(advert.getPriceString());
        aboutLabel.setText(Html.fromHtml(advertDetails.getText()));
        //parametersListView.

        List<String> images = advertDetails.getImages();
        if (images != null) {
            ImagesSliderAdapter mSectionsPagerAdapter = new ImagesSliderAdapter(getSupportFragmentManager(), images);
            imagesSlider.setAdapter(mSectionsPagerAdapter);
        }

    }

}
