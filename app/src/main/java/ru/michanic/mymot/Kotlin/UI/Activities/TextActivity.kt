package ru.michanic.mymot.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.Protocols.LoadingTextInterface;
import ru.michanic.mymot.R;

public class TextActivity extends UniversalActivity {

    private ProgressBar loadingIndicator;
    private TextView pageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Intent intent = getIntent();
        String pageTitle = intent.getStringExtra("title");
        setNavigationTitle(pageTitle);

        loadingIndicator = (ProgressBar) findViewById(R.id.progressBarAgreement);
        loadingIndicator.setVisibility(View.VISIBLE);

        pageText = (TextView) findViewById(R.id.pageText);
        pageText.setVisibility(View.GONE);

        ApiInteractor apiInteractor = new ApiInteractor();
        apiInteractor.loadAgreementText(new LoadingTextInterface() {
            @Override
            public void onLoaded(String text) {
                loadingIndicator.setVisibility(View.GONE);
                pageText.setText(Html.fromHtml(text));
                pageText.setVisibility(View.VISIBLE);
            }
        });
    }


}

