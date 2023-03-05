package ru.michanic.mymot.Kotlin.UI.Frames.Favourites

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.michanic.mymot.Extensions.Font
import ru.michanic.mymot.Kotlin.UI.Activities.AdvertActivity
import ru.michanic.mymot.Kotlin.UI.Adapters.AdvertsListAdapter
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.Protocols.ClickListener
import ru.michanic.mymot.R

class FavouriteAdvertsFragment : Fragment() {
    private val tabs: TabLayout? = null
    private var glm: GridLayoutManager? = null
    private var resultView: RecyclerView? = null
    private var placeholder: TextView? = null
    private var advertsAdapter: AdvertsListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favourite_adverts, null)
        resultView = rootView.findViewById<View>(R.id.resultsView) as RecyclerView
        placeholder = rootView.findViewById<View>(R.id.listIsEmpty) as TextView
        placeholder?.typeface = Font.oswald
        glm = GridLayoutManager(activity, 1)
        resultView?.layoutManager = glm
        loadAdverts()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadAdverts()
    }

    private fun loadAdverts() {
        val adverts = MyMotApplication.dataManager.favouriteAdverts
        val advertPressed = ClickListener { section, row ->
            val advertActivity = Intent(context, AdvertActivity::class.java)
            advertActivity.putExtra("advertId", adverts[row].id)
            startActivity(advertActivity)
        }
        advertsAdapter = AdvertsListAdapter(this.requireContext(), adverts, advertPressed)
        resultView?.adapter = advertsAdapter
    }
}