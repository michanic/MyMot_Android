package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.SearchListCell;
import ru.michanic.mymot.UI.Cells.SearchMainCell;

public class AdvertsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Advert> adverts;
    ClickListener clickListener;

    public AdvertsListAdapter(Context context, List<Advert> adverts, ClickListener clickListener) {
        this.context = context;
        this.adverts = adverts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_adverts_list, viewGroup, false);
        return new SearchListCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchListCell) {
            ((SearchListCell) holder).fillWithAdvert(adverts.get(position));
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

}
