package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.SearchMainCell;

public class SearchMainAdapter extends BaseAdapter {

    Context context;
    List<Advert> adverts;
    ClickListener clickListener;

    public SearchMainAdapter(Context context, List<Advert> adverts, ClickListener clickListener) {
        this.context = context;
        this.adverts = adverts;
        this.clickListener = clickListener;
    }

    public int getCount() {
        return adverts.size();
    }

    public Object getItem(int position) {
        return adverts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_advert_main, parent, false);
        }

        Advert advert = adverts.get(position);

        ImageView previewImage = (ImageView) convertView.findViewById(R.id.cell_image);
        TextView advertText = (TextView) convertView.findViewById(R.id.cell_title);
        TextView priceText = (TextView) convertView.findViewById(R.id.cell_price);
        TextView detailsText = (TextView) convertView.findViewById(R.id.cell_details);

        Picasso.get().load(advert.getPreviewImage()).placeholder(R.drawable.ic_placeholder).into(previewImage);
        advertText.setText(advert.getTitle());
        priceText.setText(advert.getPriceString());
        detailsText.setText(advert.getDetails());

        return convertView;
    }


}
