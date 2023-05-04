package ru.michanic.mymot.Kotlin.UI.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.michanic.mymot.Kotlin.UI.Cells.ImagesSliderSlide

class ImagesSliderAdapter(fm: FragmentManager, var images: List<String>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        return ImagesSliderSlide.newInstance(images[i])
    }

    override fun getCount(): Int {
        return images.size
    }
}