package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.shivam.library.imageslider.ImageSlider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.ModelDetails;
import ru.michanic.mymot.Models.YoutubeVideo;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.Protocols.LoadingModelDetailsInterface;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ImagesSliderAdapter;
import ru.michanic.mymot.UI.Adapters.ParametersListAdapter;
import ru.michanic.mymot.UI.Adapters.ReviewsSliderAdapter;
import ru.michanic.mymot.UI.NonScrollListView;
import ru.michanic.mymot.Utils.DataManager;

public class CatalogModelActivity extends UniversalActivity {

    private ProgressBar loadingIndicator;
    private ScrollView contentView;
    ApiInteractor apiInteractor = new ApiInteractor();
    Model model;
    ModelDetails modelDetails;
    DataManager dataManager = new DataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_model);

        Intent intent = getIntent();
        int modelId = intent.getIntExtra("modelId", 0);
        model = dataManager.getModelById(modelId);

        contentView = (ScrollView) findViewById(R.id.content_view);
        contentView.setVisibility(View.GONE);

        loadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        loadingIndicator.setVisibility(View.VISIBLE);

        loadModelDetails(modelId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_menu, menu);
        switchFavouriteButton(menu.findItem(R.id.favourite_icon), model.isFavourite());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favourite_icon) {
            dataManager.setModelFavourite(model, !model.isFavourite());
            switchFavouriteButton(item, model.isFavourite());
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchFavouriteButton(MenuItem menuItem, boolean active) {
        if (model.isFavourite()) {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_active);
        } else {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_inactive);
        }
    }


    private void loadModelDetails(final int modelId) {
        apiInteractor.loadModelDetails(modelId, new LoadingModelDetailsInterface() {
            @Override
            public void onLoaded(ModelDetails details) {
                modelDetails = details;
                loadingIndicator.setVisibility(View.GONE);
                fillProperties();
                contentView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed() {
                showNoConnectionDialog(new NoConnectionRepeatInterface() {
                    @Override
                    public void repeatPressed() {
                        loadModelDetails(modelId);
                    }
                });
            }
        });
    }


    private void fillProperties() {
        ImageSlider imagesSlider = (ImageSlider)findViewById(R.id.imagesSlider);
        TextView modelLabel = (TextView) findViewById(R.id.modelLabel);
        TextView manufacturerLabel = (TextView) findViewById(R.id.manufacturerLabel);
        TextView classLabel = (TextView) findViewById(R.id.classLabel);
        TextView yearsLabel = (TextView) findViewById(R.id.yearsLabel);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        TextView aboutLabel = (TextView) findViewById(R.id.aboutLabel);
        TextView parametersTitle = (TextView) findViewById(R.id.parametersTitle);
        NonScrollListView parametersListView  = (NonScrollListView) findViewById(R.id.parametersView);
        TextView reviewsTitle = (TextView) findViewById(R.id.reviewsTitle);
        RecyclerView reviewsSlider = (RecyclerView) findViewById(R.id.reviewsSlider);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        imagesSlider.getLayoutParams().height = (int) ((float)width * 0.75);

        modelLabel.setTypeface(Font.suzuki);
        searchButton.setTypeface(Font.progress);
        parametersTitle.setTypeface(Font.suzuki);
        reviewsTitle.setTypeface(Font.suzuki);

        modelLabel.setText(model.getName());
        manufacturerLabel.setText(model.getManufacturer().getName());
        classLabel.setText(model.getCategory().getName());
        yearsLabel.setText(model.getYears());
        aboutLabel.setText(modelDetails.getPreview_text());


        List<String> images = modelDetails.getImages();
        if (images != null) {
            ImagesSliderAdapter  mSectionsPagerAdapter = new ImagesSliderAdapter(getSupportFragmentManager(), images);
            imagesSlider.setAdapter(mSectionsPagerAdapter);
        }

        List<LinkedTreeMap<String,String>> parameters = modelDetails.getParameters();
        ParametersListAdapter parametersListAdapter = new ParametersListAdapter(parameters);

        parametersListView.setAdapter(parametersListAdapter);
        parametersListView.setEnabled(false);


        List<String> videoIDs = modelDetails.getVideo_reviews();
        if (videoIDs != null) {
            LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
            reviewsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            reviewsSlider.setLayoutManager(reviewsLayoutManager);

            final List<YoutubeVideo> videos = new ArrayList();
            for (String videoId: videoIDs) {
                videos.add(new YoutubeVideo(videoId));
            }
            ClickListener reviewPressed = new ClickListener() {
                @Override
                public void onClick(int section, int row) {
                    YoutubeVideo video = videos.get(row);
                    Intent videoActivity = new Intent(getApplicationContext(), VideoViewActivity.class);
                    videoActivity.putExtra("videoId", video.getVideoId());
                    startActivity(videoActivity);
                }
            };
            ReviewsSliderAdapter reviewsAdapter = new ReviewsSliderAdapter(this, videos, reviewPressed);
            reviewsSlider.setAdapter(reviewsAdapter);

        } else {
            reviewsTitle.setVisibility(View.GONE);
            reviewsSlider.setVisibility(View.GONE);
        }

    }

}
