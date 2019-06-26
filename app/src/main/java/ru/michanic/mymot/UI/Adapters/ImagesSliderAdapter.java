package ru.michanic.mymot.UI.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ru.michanic.mymot.UI.Cells.ImagesSliderSlide;

public class ImagesSliderAdapter extends FragmentPagerAdapter {

    List<String> images;

    public ImagesSliderAdapter(FragmentManager fm, List<String> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int i) {
        return ImagesSliderSlide.newInstance(images.get(i));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
