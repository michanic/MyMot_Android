package ru.michanic.mymot.Kotlin.UI.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FavouritesPagerAdapter(fm: FragmentManager, private val fragments: Array<Fragment?>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        return (fragments[i] ?: "") as Fragment
    }

    override fun getCount(): Int {
        return fragments.size
    }
}