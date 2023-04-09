package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import com.shivam.library.imageslider.ImageSlider
import ru.michanic.mymot.Kotlin.Extensions.Font
import ru.michanic.mymot.Kotlin.Interactors.ApiInteractor
import ru.michanic.mymot.Kotlin.Models.Model
import ru.michanic.mymot.Kotlin.Models.ModelDetails
import ru.michanic.mymot.Kotlin.Models.YoutubeVideo
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.ClickListener
import ru.michanic.mymot.Kotlin.Protocols.LoadingModelDetailsInterface
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.ImagesSliderAdapter
import ru.michanic.mymot.Kotlin.UI.Adapters.ParametersListAdapter
import ru.michanic.mymot.Kotlin.UI.Adapters.ReviewsSliderAdapter
import ru.michanic.mymot.Kotlin.UI.NonScrollListView
import ru.michanic.mymot.Kotlin.Utils.DataManager

class CatalogModelActivity : UniversalActivity() {
    private var loadingIndicator: ProgressBar? = null
    private var contentView: ScrollView? = null
    var apiInteractor = ApiInteractor()
    lateinit var model: Model
    var modelDetails: ModelDetails? = null
    var dataManager = DataManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_model)
        val intent = intent
        val modelId = intent.getIntExtra("modelId", 0)
        model = (dataManager.getModelById(modelId) ?: setNavigationTitle(model.name ?: "")) as Model
        contentView = findViewById<View>(R.id.content_view) as ScrollView
        contentView?.visibility = View.GONE
        loadingIndicator = findViewById<View>(R.id.progressBar) as ProgressBar
        loadingIndicator?.visibility = View.VISIBLE
        loadModelDetails(modelId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favourite_menu, menu)
        switchFavouriteButton(menu.findItem(R.id.favourite_icon), model.isFavourite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favourite_icon) {
            dataManager.setModelFavourite(model, !model.isFavourite)
            switchFavouriteButton(item, model.isFavourite)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchFavouriteButton(menuItem: MenuItem, active: Boolean) {
        if (model.isFavourite) {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_active)
        } else {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_inactive)
        }
    }

    private fun loadModelDetails(modelId: Int) {
        apiInteractor.loadModelDetails(modelId, {
            modelDetails = it
            loadingIndicator?.visibility = View.GONE
            fillProperties()
            contentView?.visibility = View.VISIBLE
        }, {
            showNoConnectionDialog { loadModelDetails(modelId) }
        })
    }

    private fun fillProperties() {
        val imagesSlider = findViewById<View>(R.id.imagesSlider) as ImageSlider
        val modelLabel = findViewById<View>(R.id.modelLabel) as TextView
        val manufacturerLabel = findViewById<View>(R.id.manufacturerLabel) as TextView
        val classLabel = findViewById<View>(R.id.classLabel) as TextView
        val yearsLabel = findViewById<View>(R.id.yearsLabel) as TextView
        val searchButton = findViewById<View>(R.id.searchButton) as Button
        val aboutLabel = findViewById<View>(R.id.aboutLabel) as TextView
        val parametersTitle = findViewById<View>(R.id.parametersTitle) as TextView
        val parametersListView = findViewById<View>(R.id.parametersView) as NonScrollListView
        val reviewsTitle = findViewById<View>(R.id.reviewsTitle) as TextView
        val reviewsSlider = findViewById<View>(R.id.reviewsSlider) as RecyclerView
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        imagesSlider.layoutParams.height = (width.toFloat() * 0.75).toInt()
        modelLabel.typeface = Font.oswald
        searchButton.typeface = Font.progress
        parametersTitle.typeface = Font.oswald
        reviewsTitle.typeface = Font.oswald
        modelLabel.text = model.name
        manufacturerLabel.text = model.manufacturer?.name
        classLabel.text = model.category?.name
        yearsLabel.text = model.years
        aboutLabel.text = modelDetails?.preview_text
        val images = modelDetails?.images
        if (images != null) {
            val mSectionsPagerAdapter = ImagesSliderAdapter(supportFragmentManager, images)
            imagesSlider.setAdapter(mSectionsPagerAdapter)
        }
        val parameters = modelDetails?.parameters
        val parametersListAdapter = ParametersListAdapter(parameters)
        parametersListView.adapter = parametersListAdapter
        parametersListView.isEnabled = false
        val videoIDs = modelDetails?.video_reviews
        if (videoIDs != null) {
            val reviewsLayoutManager = LinearLayoutManager(this)
            reviewsLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            reviewsSlider.layoutManager = reviewsLayoutManager
            val videos: MutableList<YoutubeVideo> = ArrayList()
            for (videoId in videoIDs) {
                videos.add(YoutubeVideo(videoId))
            }
            val reviewPressed = object : ClickListener {
                override fun onClick(section: Int, row: Int) {
                    val video = videos[row]
                    val videoActivity = Intent(applicationContext, VideoViewActivity::class.java)
                    videoActivity.putExtra("videoId", video.videoId)
                    startActivity(videoActivity)
                }
            }
            val reviewsAdapter = ReviewsSliderAdapter(this, videos, reviewPressed)
            reviewsSlider.adapter = reviewsAdapter
        } else {
            reviewsTitle.visibility = View.GONE
            reviewsSlider.visibility = View.GONE
        }
        searchButton.setOnClickListener {
            MyMotApplication.searchManager?.model = model
            val searchResultsActivity =
                Intent(applicationContext, SearchResultsActivity::class.java)
            startActivity(searchResultsActivity)
        }
    }
}