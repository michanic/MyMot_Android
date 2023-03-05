package ru.michanic.mymot.Kotlin.UI.Frames.Favourites

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.michanic.mymot.Kotlin.UI.Adapters.FavouritesPagerAdapter
import ru.michanic.mymot.R
import ru.michanic.mymot.Utils.DataManager

class FavouritesHomeFragment : Fragment() {
    private var tabs: TabLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favourites_home, null)
        val dataManager = DataManager()
        val fragments = arrayOfNulls<Fragment>(2)
        fragments[0] = FavouriteModelsFragment()
        fragments[1] = FavouriteAdvertsFragment()
        val pager = rootView.findViewById<View>(R.id.base_pager) as ViewPager
        pager.adapter = FavouritesPagerAdapter(childFragmentManager, fragments)
        tabs = rootView.findViewById<View>(R.id.base_tabs) as TabLayout
        tabs?.setupWithViewPager(pager)
        tabs?.getTabAt(0)?.select()

        //--> Adding tabs
        for (i in 0 until tabs!!.tabCount) {
            when (i) {
                0 -> tabs?.getTabAt(i)?.text = "Модели"
                1 -> tabs?.getTabAt(i)?.text = "Объявления"
                else -> tabs?.getTabAt(i)?.text = "Unknown"
            }
        }
        return rootView
    }
}