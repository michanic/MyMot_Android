package ru.michanic.mymot.Kotlin.UI.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FavouritesPagerAdapter(fm: FragmentManager?, private val fragments: Array<Fragment?>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment? {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}