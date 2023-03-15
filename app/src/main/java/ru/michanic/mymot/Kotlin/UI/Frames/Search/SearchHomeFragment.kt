package ru.michanic.mymot.Kotlin.UI.Frames.Search

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.Extensions.Font
import ru.michanic.mymot.Kotlin.Interactors.SitesInteractor
import ru.michanic.mymot.Kotlin.UI.Activities.AdvertActivity
import ru.michanic.mymot.Kotlin.UI.Adapters.SearchMainAdapter
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.Kotlin.Models.SearchFilterConfig
import ru.michanic.mymot.Kotlin.Models.Source
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.ClickListener
import ru.michanic.mymot.Kotlin.Protocols.FilterSettedInterface
import ru.michanic.mymot.Kotlin.Protocols.LoadingAdvertsInterface
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.Utils.DataManager

class SearchHomeFragment : Fragment() {
    private var currentSource: Source? = null
    private var avitoLoadMoreAvailable = true
    private var loadMoreAvailable = false
    private val dataManager = DataManager()
    private val sitesInteractor = SitesInteractor()
    private var searchAdapter: SearchMainAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var glm: GridLayoutManager? = null
    private var progressBar: ProgressBar? = null
    private var loading = false
    private val isLastPage = false
    private var titleView: TextView? = null
    private var resultView: RecyclerView? = null
    private val loadedAdverts: MutableList<Advert> = ArrayList()
    private var filterConfig: SearchFilterConfig? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search_home, null)
        
        progressBar = rootView.findViewById<View>(R.id.progressBar) as ProgressBar
        titleView = rootView.findViewById<View>(R.id.resultsTitle) as TextView
        resultView = rootView.findViewById<View>(R.id.resultsView) as RecyclerView
        titleView?.typeface = Font.oswald
        glm = GridLayoutManager(activity, 2)
        resultView?.layoutManager = glm
        mLayoutManager = LinearLayoutManager(activity)
        resultView?.setHasFixedSize(false)
        val advertPressed = ClickListener { section, row ->
            val advertActivity = Intent(activity, AdvertActivity::class.java)
            advertActivity.putExtra("advertId", loadedAdverts[row].id)
            activity?.startActivity(advertActivity)
        }
        searchAdapter = SearchMainAdapter(this.requireContext(), loadedAdverts, advertPressed)
        resultView?.adapter = searchAdapter
        resultView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastvisibleitemposition = glm?.findLastVisibleItemPosition()
                if (lastvisibleitemposition == searchAdapter?.itemCount?.minus(1)) {
                    if (!loading && !isLastPage) {
                        loading = true
                        loadMore()
                    }
                }
            }
        })
        progressBar?.visibility = View.VISIBLE
        reloadResults()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        MyMotApplication.searchManager.filterClosedCallback =
            FilterSettedInterface { reloadResults() }
    }

    private fun reloadResults() {
        filterConfig = MyMotApplication.searchManager.filterConfig
        titleView?.text = filterConfig?.mainSearchTitle
        loadedAdverts.clear()
        currentSource = null
        loadMore()
    }

    private fun loadMore() {
        loading = true
        if (currentSource == null) {
            currentSource = Source(
                SourceType.AVITO,
                filterConfig!!.priceFrom,
                filterConfig!!.priceFor,
                filterConfig?.selectedRegion
            )
            currentSource?.page = 1
        } else {
            if (currentSource?.type == SourceType.AVITO && avitoLoadMoreAvailable) {
                currentSource?.updateTypeAndRegion(
                    SourceType.AUTO_RU,
                    filterConfig?.selectedRegion
                )
            } else {
                if (avitoLoadMoreAvailable) {
                    currentSource?.incrementPage()
                    currentSource?.updateTypeAndRegion(
                        SourceType.AVITO,
                        filterConfig?.selectedRegion
                    )
                } else {
                    currentSource?.updateTypeAndRegion(
                        SourceType.AUTO_RU,
                        filterConfig?.selectedRegion
                    )
                    currentSource?.incrementPage()
                }
            }
        }
        sitesInteractor.loadFeedAdverts(currentSource, object : LoadingAdvertsInterface {
            override fun onLoaded(adverts: List<Advert>, loadMore: Boolean) {
                progressBar?.visibility = View.GONE
                Log.e("onLoaded", adverts.size.toString())
                loadedAdverts.addAll(adverts)
                searchAdapter?.notifyDataSetChanged()
                loading = false
                if (currentSource?.type == SourceType.AVITO) {
                    avitoLoadMoreAvailable = loadMore
                    loadMoreAvailable = true
                } else {
                    loadMoreAvailable = loadMore
                }
            }

            override fun onFailed() {}
        })
    }
}