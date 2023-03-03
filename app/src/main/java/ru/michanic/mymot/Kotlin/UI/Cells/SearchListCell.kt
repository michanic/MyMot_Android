package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.R;

public class SearchListCell extends RecyclerView.ViewHolder  {

    ImageView imageView;
    TextView titleLabel;
    TextView priceText;
    TextView detailsText;
    ImageView favouriteIcon;

    public SearchListCell(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cell_image);
        titleLabel = (TextView) itemView.findViewById(R.id.advert_title);
        priceText = (TextView) itemView.findViewById(R.id.price_label);
        detailsText = (TextView) itemView.findViewById(R.id.advert_details);
        favouriteIcon = (ImageView) itemView.findViewById(R.id.favourite_icon);
    }

    public void fillWithAdvert(final Advert advert) {
        Picasso.get().load(advert.getPreviewImage()).placeholder(R.drawable.ic_placeholder).into(imageView);
        titleLabel.setText(advert.getTitle());
        priceText.setText(advert.getPriceString());
        detailsText.setText(advert.getDetails());

        imageView.setAlpha(advert.isActive() ? 1 : (float) 0.5);
        priceText.setAlpha(advert.isActive() ? 1 : (float) 0.5);

        switchFavouriteButton(advert.isFavourite());

        favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newState = !advert.isFavourite();
                Log.e("setAdvertFavourite", String.valueOf(newState));
                MyMotApplication.dataManager.setAdvertFavourite(advert.getId(), newState);
                switchFavouriteButton(newState);
            }
        });
    }

    private void switchFavouriteButton(boolean favourite) {
        if (favourite) {
            favouriteIcon.setImageResource(R.drawable.ic_list_favourite_active);
        } else {
            favouriteIcon.setImageResource(R.drawable.ic_list_favourite_passive);
        }
    }

}
