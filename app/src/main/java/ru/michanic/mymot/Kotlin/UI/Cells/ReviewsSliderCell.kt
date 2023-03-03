package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.michanic.mymot.Models.YoutubeVideo;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.R;

public class ReviewsSliderCell extends RecyclerView.ViewHolder {

    ImageView imageView;

    public ReviewsSliderCell(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cell_image);
    }

    public void fillWithVideo(YoutubeVideo video) {
        Picasso.get().load(video.getPreviewPath()).placeholder(R.drawable.ic_placeholder).into(imageView);
    }

}
