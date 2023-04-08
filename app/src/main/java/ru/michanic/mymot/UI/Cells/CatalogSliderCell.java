package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Volume;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.R;

public class CatalogSliderCell extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView titleLabel;

    public CatalogSliderCell(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cell_image);
        titleLabel = (TextView) itemView.findViewById(R.id.cell_title);
    }

    public void fillWithCategory(Category category) {
        Picasso.get().load(Const.DOMAIN + category.getImage()).placeholder(R.drawable.ic_placeholder).into(imageView);
        titleLabel.setText(category.getName());
    }

    public void fillWithManufacturer(Manufacturer manufacturer) {
        Picasso.get().load(Const.DOMAIN + manufacturer.getImage()).placeholder(R.drawable.ic_placeholder).into(imageView);
        titleLabel.setText(manufacturer.getName());
    }

    public void fillWithVolume(Volume volume) {
        Picasso.get().load(Const.DOMAIN + volume.getImage()).placeholder(R.drawable.ic_placeholder).into(imageView);
        titleLabel.setText(volume.getName());
    }

}