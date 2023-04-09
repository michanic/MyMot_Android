package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.kodmap.app.library.PopopDialogBuilder
import com.kodmap.app.library.model.BaseItem
import com.shivam.library.imageslider.ImageSlider
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.Extensions.Font
import ru.michanic.mymot.Kotlin.Interactors.SitesInteractor
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.Kotlin.Models.AdvertDetails
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.LoadingAdvertPhonesInterface
import ru.michanic.mymot.Kotlin.UI.Adapters.ImagesSliderAdapter
import ru.michanic.mymot.Kotlin.UI.Adapters.ParametersListAdapter
import ru.michanic.mymot.Kotlin.UI.NonScrollListView
import ru.michanic.mymot.R

class AdvertActivity : UniversalActivity() {
    private var loadingIndicator: ProgressBar? = null
    private var contentView: ScrollView? = null
    var sitesInteractor = SitesInteractor()
    lateinit var advert: Advert
    var advertDetails: AdvertDetails? = null
    var sellerPhones: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advert)
        val intent = intent
        val advertId = intent.getStringExtra("advertId")
        advert = (MyMotApplication.dataManager?.getAdvertById(advertId)) ?: return
        setNavigationTitle(advert.title.toString())
        contentView = findViewById<View>(R.id.content_view) as ScrollView
        contentView?.visibility = View.GONE
        loadingIndicator = findViewById<View>(R.id.progressBar) as ProgressBar
        loadingIndicator?.visibility = View.VISIBLE
        loadAdvertDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favourite_menu, menu)
        switchFavouriteButton(menu.findItem(R.id.favourite_icon), advert.isFavourite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favourite_icon) {
            MyMotApplication.dataManager?.setAdvertFavourite(advert.id, !advert.isFavourite)
            switchFavouriteButton(item, advert.isFavourite)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchFavouriteButton(menuItem: MenuItem, active: Boolean) {
        if (advert.isFavourite) {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_active)
        } else {
            menuItem.setIcon(R.drawable.ic_navigation_favourite_inactive)
        }
    }

    private fun loadAdvertDetails() {
        sitesInteractor.loadAdvertDetails(advert, {
            advertDetails = it
            loadingIndicator?.visibility = View.GONE
            fillProperties()
            contentView?.visibility = View.VISIBLE
            MyMotApplication.configStorage?.saveCsrfToken(advertDetails?.csrfToken)
        }, {
            showNoConnectionDialog { loadAdvertDetails() }
        })
    }

    private fun fillProperties() {
        val fullscreenIcon = findViewById<View>(R.id.fullscreenIcon) as ImageView
        val imagesSliderWrapper = findViewById<View>(R.id.imagesSliderWrapper) as RelativeLayout
        val imagesSlider = findViewById<View>(R.id.imagesSlider) as ImageSlider
        val titleLabel = findViewById<View>(R.id.titleLabel) as TextView
        val cityLabel = findViewById<View>(R.id.cityLabel) as TextView
        val priceLabel = findViewById<View>(R.id.priceLabel) as TextView
        val dateLabel = findViewById<View>(R.id.dateLabel) as TextView
        val aboutLabel = findViewById<View>(R.id.aboutLabel) as TextView
        val parametersListView = findViewById<View>(R.id.parametersView) as NonScrollListView
        val callButton = findViewById<View>(R.id.callButton) as Button
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        imagesSliderWrapper.layoutParams.height = (width.toFloat() * 0.75).toInt()
        titleLabel.typeface = Font.oswald
        callButton.typeface = Font.progress
        titleLabel.text = advert.title
        cityLabel.text = advert.city
        dateLabel.text = advertDetails?.date
        priceLabel.text = advert.priceString
        val images = advertDetails?.images
        if (images != null) {
            if (images.size > 0) {
                val mSectionsPagerAdapter = ImagesSliderAdapter(supportFragmentManager, images)
                imagesSlider.setAdapter(mSectionsPagerAdapter)
            } else {
                imagesSlider.visibility = View.GONE
                fullscreenIcon.visibility = View.GONE
            }
        }
        fullscreenIcon.setOnClickListener { showImagesGallery(images) }
        val warning = advertDetails?.warning
        if (warning != null) {
            if (warning.length > 0) {
                MyMotApplication.dataManager?.setAdvertActive(advert.id, false)
                aboutLabel.text = warning
                imagesSlider.alpha = 0.5.toFloat()
                callButton.visibility = View.GONE
            } else {
                MyMotApplication.dataManager?.setAdvertActive(advert.id, true)
                imagesSlider.alpha = 1f
                callButton.visibility = View.VISIBLE
                val aboutText = advertDetails?.text
                if (aboutText != null) {
                    aboutLabel.text = Html.fromHtml(aboutText)
                } else {
                    aboutLabel.text = ""
                    val layoutParams = aboutLabel.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.setMargins(0, 0, 0, 0)
                    aboutLabel.layoutParams = layoutParams
                }
            }
        }
        callButton.setOnCreateContextMenuListener(this)
        callButton.setOnClickListener {
            sellerPhones.clear()
            val sourceType = advert.sourceType
            if (sourceType == SourceType.AVITO) {

                sitesInteractor.loadAvitoAdvertPhone(advert, object : LoadingAdvertPhonesInterface {
                    override fun onLoaded(phones: List<String?>?) {
                        sellerPhones = phones?.filterNotNull()?.toMutableList() ?: ArrayList()
                        if (sellerPhones.size > 0) {
                            makeCall(sellerPhones[0])
                        }
                    }
                })
            } else if (sourceType == SourceType.AUTO_RU) {
                sitesInteractor.loadAutoRuAdvertPhones(
                    advert.id.toString(),
                    advertDetails?.saleHash.toString(),
                    object : LoadingAdvertPhonesInterface {
                        override fun onLoaded(phones: List<String?>?) {
                            sellerPhones = phones?.filterNotNull()?.toMutableList() ?: ArrayList()
                            if (phones?.size == 1) {
                                val phone = phones[0]
                                if (phone!!.contains("c")) {
                                    openContextMenu(callButton)
                                } else {
                                    makeCall(sellerPhones[0])
                                }
                            } else if (sellerPhones.size > 1) {
                                openContextMenu(callButton)
                            }
                        }
                    })
            }
        }

        val parameters = advertDetails?.parameters
        val parametersListAdapter = ParametersListAdapter(parameters)
        parametersListView.adapter = parametersListAdapter
        parametersListView.isEnabled = false
    }

    private fun makeCall(phone: String) {
        var phone = phone
        if (phone.contains("c")) {
            phone = phone.substring(0, phone.indexOf("c") - 1)
        }
        val callPhone = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(callPhone)
    }

    private fun showImagesGallery(images: List<String>?) {
        val item_list: MutableList<BaseItem> = ArrayList()
        if (images != null) {
            for (imagePath in images) {
                val item = BaseItem()
                item.imageUrl = imagePath
                item_list.add(item)
            }
        }
        val dialog = PopopDialogBuilder(this)
            .showThumbSlider(true)
            .setList(item_list)
            .setHeaderBackgroundColor(android.R.color.black)
            .setDialogBackgroundColor(android.R.color.black)
            .setCloseDrawable(R.drawable.ic_close_white_24dp)
            .build()
        dialog.show()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        for (phone in sellerPhones) {
            menu.add(phone)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val phone = sellerPhones[item.itemId]
        makeCall(phone)
        return super.onContextItemSelected(item)
    }

    fun imageSliderClick(v: View?) {
        Log.e("imageSliderClick", "click")
    }
}