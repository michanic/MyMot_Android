package ru.michanic.mymot.UI.Cells;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.R;

public class ImagesSliderSlide extends Fragment {

    String imagePath;

    public static ImagesSliderSlide newInstance(String imagePath) {
        ImagesSliderSlide fragment = new ImagesSliderSlide();
        fragment.imagePath = imagePath;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cell_images_slide, container, false);
        ImageView imageView=(ImageView)rootView.findViewById(R.id.image);
        Picasso.get().load(imagePath).into(imageView);
        return rootView;
    }

}
