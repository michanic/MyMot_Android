package ru.michanic.mymot.Kotlin.UI.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.michanic.mymot.Kotlin.UI.Cells.ImagesSliderSlide

class ImagesSliderAdapter(fm: FragmentManager?, var images: List<String>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        return ImagesSliderSlide.newInstance(images[i])
    }

    override fun getCount(): Int {
        return images.size
    }
}