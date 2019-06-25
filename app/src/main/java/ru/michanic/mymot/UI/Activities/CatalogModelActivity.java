package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.ModelDetails;
import ru.michanic.mymot.Models.YoutubeVideo;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.Protocols.LoadingModelDetailsInterface;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ReviewsSliderAdapter;
import ru.michanic.mymot.Utils.DataManager;

public class CatalogModelActivity extends UniversalActivity {

    private ProgressBar loadingIndicator;
    private ScrollView contentView;
    ApiInteractor apiInteractor = new ApiInteractor();
    Model model;
    ModelDetails modelDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_model);

        DataManager dataManager = new DataManager();

        Intent intent = getIntent();
        int modelId = intent.getIntExtra("modelId", 0);
        model = dataManager.getModelById(modelId);

        contentView = (ScrollView) findViewById(R.id.content_view);
        contentView.setVisibility(View.GONE);

        loadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        loadingIndicator.setVisibility(View.VISIBLE);

        loadModelDetails(modelId);
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
        TextView modelLabel = (TextView) findViewById(R.id.modelLabel);
        TextView manufacturerLabel = (TextView) findViewById(R.id.manufacturerLabel);
        TextView classLabel = (TextView) findViewById(R.id.classLabel);
        TextView yearsLabel = (TextView) findViewById(R.id.yearsLabel);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        TextView aboutLabel = (TextView) findViewById(R.id.aboutLabel);
        TextView parametersTitle = (TextView) findViewById(R.id.parametersTitle);
        TextView reviewsTitle = (TextView) findViewById(R.id.reviewsTitle);
        RecyclerView reviewsSlider = (RecyclerView) findViewById(R.id.reviewsSlider);

        modelLabel.setTypeface(Font.suzuki);
        searchButton.setTypeface(Font.progress);
        parametersTitle.setTypeface(Font.suzuki);
        reviewsTitle.setTypeface(Font.suzuki);

        modelLabel.setText(model.getName());
        manufacturerLabel.setText(model.getManufacturer().getName());
        classLabel.setText(model.getCategory().getName());
        yearsLabel.setText(model.getYears());
        aboutLabel.setText(modelDetails.getPreview_text());





        List<String> videoIDs = modelDetails.getVideo_reviews();
        if (videoIDs != null) {
            LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
            reviewsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            reviewsSlider.setLayoutManager(reviewsLayoutManager);

            List<YoutubeVideo> videos = new ArrayList();
            for (String videoId: videoIDs) {
                videos.add(new YoutubeVideo(videoId));
            }
            ClickListener reviewPressed = new ClickListener() {
                @Override
                public void onClick(int section, int row) {
                    /*Intent catalogByClassActivity = new Intent(r, CatalogByClassActivity.class);
                    catalogByClassActivity.putExtra("classId", classes.get(row).getId());
                    getActivity().startActivity(catalogByClassActivity);*/
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
