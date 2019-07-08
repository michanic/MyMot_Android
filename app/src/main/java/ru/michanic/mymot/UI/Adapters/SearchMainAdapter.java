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

public class SearchMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Advert> adverts;
    ClickListener clickListener;

    public SearchMainAdapter(Context context, List<Advert> adverts, ClickListener clickListener) {
        this.context = context;
        this.adverts = adverts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_advert_main, viewGroup, false);
        return new SearchMainCell(view);
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
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).setTitle("Заголовок");
        }
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;
        private final TextView headerTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            // Добавьте свои компоненты ui здесь, как показано ниже
            headerTitle = (TextView) View.findViewById(R.id.headerTitle);

        }

        public void setTitle(String title) {
            headerTitle.setText(title);
        }
    }

}
