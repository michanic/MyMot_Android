package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.R;

public class SearchMainCell extends RecyclerView.ViewHolder  {

    ImageView imageView;
    TextView titleLabel;
    TextView priceText;
    TextView detailsText;

    public SearchMainCell(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cell_image);
        titleLabel = (TextView) itemView.findViewById(R.id.cell_title);
        priceText = (TextView) itemView.findViewById(R.id.cell_price);
        detailsText = (TextView) itemView.findViewById(R.id.cell_details);
    }

    public void fillWithAdvert(Advert advert) {
        Log.e("getPreviewImage", advert.getPreviewImage());

        Picasso.get().load(advert.getPreviewImage()).placeholder(R.drawable.ic_placeholder).into(imageView);
        titleLabel.setText(advert.getTitle());
        priceText.setText(advert.getPriceString());
        detailsText.setText(advert.getDetails());
    }

}
