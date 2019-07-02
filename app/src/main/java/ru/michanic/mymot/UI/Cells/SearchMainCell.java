package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.R;

public class SearchMainCell extends RecyclerView.ViewHolder  {

    ImageView imageView;
    TextView titleLabel;

    public SearchMainCell(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cell_image);
        titleLabel = (TextView) itemView.findViewById(R.id.cell_title);
    }

    public void fillWithAdvert(Advert advert) {
        //Picasso.get().load(Const.DOMAIN + category.getImage()).placeholder(R.drawable.ic_placeholder).into(imageView);
        titleLabel.setText(advert.getTitle());
    }

}
