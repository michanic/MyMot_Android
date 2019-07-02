package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Protocols.ClickListener;
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
        TextView advertTitle = (TextView) convertView.findViewById(R.id.cell_title);
        advertTitle.setText(advert.getTitle());

        return convertView;
    }



    /*
    public SearchMainAdapter(Context context, List<Advert> adverts, ClickListener clickListener) {
        this.context = context;
        this.adverts = adverts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_advert_main, viewGroup, false);
            viewHolder = new SearchMainCell(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchMainCell) {
            ((SearchMainCell) holder).fillWithAdvert(adverts.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(0, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return adverts.size();
    }
    */
}
