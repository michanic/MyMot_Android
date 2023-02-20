package ru.michanic.mymot.UI.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.kodmap.app.library.PopopDialogBuilder;
import com.kodmap.app.library.model.BaseItem;
import com.shivam.library.imageslider.ImageSlider;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Interactors.SitesInteractor;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.AdvertDetails;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.LoadingAdvertDetailsInterface;
import ru.michanic.mymot.Protocols.LoadingAdvertPhonesInterface;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Adapters.ImagesSliderAdapter;
import ru.michanic.mymot.UI.Adapters.ParametersListAdapter;
import ru.michanic.mymot.UI.NonScrollListView;

public class AdvertActivity extends UniversalActivity {

    private ProgressBar loadingIndicator;
    private ScrollView contentView;
    SitesInteractor sitesInteractor = new SitesInteractor();
    Advert advert;
    AdvertDetails advertDetails;
    List<String> sellerPhones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        Intent intent = getIntent();
        String advertId = intent.getStringExtra("advertId");
        advert = MyMotApplication.dataManager.getAdvertById(advertId);

        setNavigationTitle(advert.getTitle());

        contentView = (ScrollView) findViewById(R.id.content_view);
        contentView.setVisibility(View.GONE);

        loadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        loadingIndicator.setVisibility(View.VISIBLE);

        loadAdvertDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_menu, menu);
        switchFavouriteButton(menu.findItem(R.id.favourite_icon), advert.isFavourite());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favourite_icon) {
            MyMotApplication.dataManager.setAdvertFavourite(advert.getId(), !advert.isFavourite());
            switchFavouriteButton(item, advert.isFavourite());
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchFavouriteButton(MenuItem menuItem, boolean active) {
        if (advert.isFavourite()) {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_active);
        } else {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_inactive);
        }
    }

    private void loadAdvertDetails() {

        sitesInteractor.loadAdvertDetails(advert, new LoadingAdvertDetailsInterface() {
            @Override
            public void onLoaded(AdvertDetails details) {

                advertDetails = details;
                loadingIndicator.setVisibility(View.GONE);
                fillProperties();
                contentView.setVisibility(View.VISIBLE);
                MyMotApplication.configStorage.saveCsrfToken(advertDetails.getCsrfToken());
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

        ImageView fullscreenIcon = (ImageView)findViewById(R.id.fullscreenIcon);
        RelativeLayout imagesSliderWrapper = (RelativeLayout)findViewById(R.id.imagesSliderWrapper);
        ImageSlider imagesSlider = (ImageSlider)findViewById(R.id.imagesSlider);
        TextView titleLabel = (TextView) findViewById(R.id.titleLabel);
        TextView cityLabel = (TextView) findViewById(R.id.cityLabel);
        TextView priceLabel = (TextView) findViewById(R.id.priceLabel);
        TextView dateLabel = (TextView) findViewById(R.id.dateLabel);
        TextView aboutLabel = (TextView) findViewById(R.id.aboutLabel);
        NonScrollListView parametersListView  = (NonScrollListView) findViewById(R.id.parametersView);
        final Button callButton = (Button) findViewById(R.id.callButton);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        imagesSliderWrapper.getLayoutParams().height = (int) ((float)width * 0.75);

        titleLabel.setTypeface(Font.oswald);
        callButton.setTypeface(Font.progress);

        titleLabel.setText(advert.getTitle());
        cityLabel.setText(advert.getCity());
        dateLabel.setText(advertDetails.getDate());
        priceLabel.setText(advert.getPriceString());


        final List<String> images = advertDetails.getImages();
        if (images != null) {
            if (images.size() > 0) {
                ImagesSliderAdapter mSectionsPagerAdapter = new ImagesSliderAdapter(getSupportFragmentManager(), images);
                imagesSlider.setAdapter(mSectionsPagerAdapter);
            } else {
                imagesSlider.setVisibility(View.GONE);
                fullscreenIcon.setVisibility(View.GONE);
            }
        }

        fullscreenIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagesGallery(images);
            }
        });


        String warning = advertDetails.getWarning();
        if (warning.length() > 0) {
            MyMotApplication.dataManager.setAdvertActive(advert.getId(), false);
            aboutLabel.setText(warning);
            imagesSlider.setAlpha((float) 0.5);
            callButton.setVisibility(View.GONE);
        } else {
            MyMotApplication.dataManager.setAdvertActive(advert.getId(), true);
            imagesSlider.setAlpha((float) 1);
            callButton.setVisibility(View.VISIBLE);

            String aboutText = advertDetails.getText();
            if (aboutText.length() > 0) {
                aboutLabel.setText(Html.fromHtml(aboutText));
            } else {
                aboutLabel.setText("");
                final RelativeLayout.LayoutParams layoutparams = (RelativeLayout.LayoutParams)aboutLabel.getLayoutParams();
                layoutparams.setMargins(0, 0, 0, 0);
                aboutLabel.setLayoutParams(layoutparams);
            }
        }

        callButton.setOnCreateContextMenuListener(this);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerPhones.clear();
                SourceType sourceType = advert.getSourceType();
                if (sourceType == SourceType.AVITO) {
                    sitesInteractor.loadAvitoAdvertPhone(advert, new LoadingAdvertPhonesInterface() {
                        @Override
                        public void onLoaded(List<String> phones) {
                            sellerPhones = phones;
                            if (phones.size() > 0) {
                                makeCall(phones.get(0));
                            }
                        }
                    });
                } else if (sourceType == SourceType.AUTO_RU) {
                    sitesInteractor.loadAutoRuAdvertPhones(advert.getId(), advertDetails.getSaleHash(), new LoadingAdvertPhonesInterface() {
                        @Override
                        public void onLoaded(List<String> phones) {
                            sellerPhones = phones;
                            if (phones.size() == 1) {
                                String phone = phones.get(0);
                                if (phone.contains("c")) {
                                    openContextMenu(callButton);
                                } else {
                                    makeCall(phone);
                                }
                            } else if (phones.size() > 1) {
                                openContextMenu(callButton);
                            }
                        }
                    });
                }
            }
        });

        List<LinkedTreeMap<String,String>> parameters = advertDetails.getParameters();
        ParametersListAdapter parametersListAdapter = new ParametersListAdapter(parameters);
        parametersListView.setAdapter(parametersListAdapter);
        parametersListView.setEnabled(false);

    }

    private void makeCall(String phone) {
        if (phone.contains("c")) {
            phone = phone.substring(0, phone.indexOf("c") - 1);
        }
        Intent callPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(callPhone);
    }

    private void showImagesGallery(List<String> images) {

        List<BaseItem> item_list = new ArrayList<>();

        for (String imagePath: images) {
            BaseItem item = new BaseItem();
            item.setImageUrl(imagePath);
            item_list.add(item);
        }

        Dialog dialog = new PopopDialogBuilder(this)
                .showThumbSlider(true)
                .setList(item_list)
                .setHeaderBackgroundColor(android.R.color.black)
                .setDialogBackgroundColor(android.R.color.black)
                .setCloseDrawable(R.drawable.ic_close_white_24dp)
                // Set loading view for pager image and preview image
                //.setLoadingView(R.layout.loading_view)
                //.setDialogStyle(R.style.DialogStyle)
                //.showThumbSlider(true)
                // Set image scale type for slider image
                //.setSliderImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                //.setSelectorIndicator(R.drawable.sample_indicator_selector)
                // Build Km Slider Popup Dialog
                .build();

        dialog.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        for (String phone: sellerPhones) {
            menu.add(phone);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String phone = sellerPhones.get(item.getItemId());
        makeCall(phone);
        return super.onContextItemSelected(item);
    }

    public void imageSliderClick(View v) {
        Log.e("imageSliderClick", "click");
    }

}