package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.michanic.mymot.Models.YoutubeVideo;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.CatalogSliderCell;
import ru.michanic.mymot.UI.Cells.ReviewsSliderCell;

public class ReviewsSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<YoutubeVideo> videos;
    ClickListener clickListener;

    public ReviewsSliderAdapter(Context context, List<YoutubeVideo> videos, ClickListener clickListener) {
        this.context = context;
        this.videos = videos;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_review, viewGroup, false);
            viewHolder = new ReviewsSliderCell(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ReviewsSliderCell) {
            ((ReviewsSliderCell) holder).fillWithVideo(videos.get(position));
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
        return videos.size();
    }

}
