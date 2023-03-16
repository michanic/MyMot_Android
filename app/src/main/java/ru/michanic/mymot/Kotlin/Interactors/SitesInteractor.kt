package ru.michanic.mymot.Kotlin.Interactors

import android.util.Log
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.Models.*
import ru.michanic.mymot.Kotlin.Protocols.LoadingAdvertDetailsInterface
import ru.michanic.mymot.Kotlin.Protocols.LoadingAdvertPhonesInterface
import ru.michanic.mymot.Kotlin.Protocols.LoadingAdvertsInterface
import ru.michanic.mymot.Kotlin.Utils.DataManager

class SitesInteractor {
    private val dataManager = DataManager()
    fun loadFeedAdverts(source: Source, loadingInterface: LoadingAdvertsInterface) {
        Log.e("loadFeedAdverts", source.feedPath)
        loadSourceAdverts(source.type, source.feedPath, loadingInterface)
    }

    fun searchAdverts(
        page: Int,
        config: SearchFilterConfig,
        loadingInterface: LoadingAdvertsInterface
    ) {
        Log.e("searchAdverts page: ", page.toString())
        val loadedAdverts: MutableList<Advert> = ArrayList()
        val loadMore = booleanArrayOf(false)
        var avitoModelQuery = ""
        val avitoManufacturer = config.selectedManufacturer
        val avitoModel = config.selectedModel
        if (avitoManufacturer != null) {
            avitoModelQuery = avitoManufacturer.avitoSearchName
        } else if (avitoModel != null) {
            avitoModelQuery = avitoModel.avitoSearchName
        }
        val avitoSource = Source(SourceType.AVITO)
        avitoSource.setModel(avitoModelQuery)
        val avitoSelectedRegion = config.selectedRegion
        if (avitoSelectedRegion != null) {
            avitoSource.setRegion(avitoSelectedRegion.avito)
        }
        avitoSource.setpMin(config.priceFrom)
        avitoSource.setpMax(config.priceFor)
        avitoSource.page = page
        val avitoUrl = avitoSource.searchPath
        Log.e("avitoUrl: ", avitoUrl)
        loadSourceAdverts(avitoSource.type, avitoUrl, object : LoadingAdvertsInterface {
            override fun onLoaded(adverts: List<Advert?>?, avitoMore: Boolean) {
                loadedAdverts.addAll(adverts!!.filterNotNull())
                loadMore[0] = avitoMore
                var autoruModelQuery = ""
                val autoruManufacturer = config.selectedManufacturer
                val autoruModel = config.selectedModel
                if (autoruManufacturer != null) {
                    autoruModelQuery = autoruManufacturer.autoruSearchName
                } else if (autoruModel != null) {
                    autoruModelQuery = autoruModel.autoruSearchName
                }
                val autoruSource = Source(SourceType.AUTO_RU)
                autoruSource.setModel(autoruModelQuery)
                val autoruSelectedRegion = config.selectedRegion
                if (autoruSelectedRegion != null) {
                    autoruSource.setRegion(autoruSelectedRegion.autoru)
                }
                autoruSource.setpMin(config.priceFrom)
                autoruSource.setpMax(config.priceFor)
                autoruSource.page = page
                val autoruUrl = autoruSource.searchPath
                Log.e("autoruUrl: ", autoruUrl)
                loadSourceAdverts(autoruSource.type, autoruUrl, object : LoadingAdvertsInterface {
                    override fun onLoaded(adverts: List<Advert?>?, autoruMore: Boolean) {
                        loadedAdverts.addAll(adverts!!.filterNotNull())
                        if (!loadMore[0]) {
                            loadMore[0] = autoruMore
                        }
                        loadingInterface.onLoaded(loadedAdverts, loadMore[0])
                    }

                    override fun onFailed() {
                        loadingInterface.onFailed()
                    }
                })
            }

            override fun onFailed() {
                loadingInterface.onFailed()
            }
        })
    }

    private fun loadSourceAdverts(
        sourceType: SourceType,
        url: String?,
        loadingInterface: LoadingAdvertsInterface
    ) {
        HtmlAdvertsAsyncRequest({ output ->
            val result = output as ParseAdvertsResult
            val newAdverts = result.advetrs
            val resultAdvetrs: MutableList<Advert> = ArrayList()
            for (newAdvert in newAdverts) {
                val savedAdvert = dataManager.getAdvertById(newAdvert.id)
                if (savedAdvert != null) {
                    resultAdvetrs.add(savedAdvert)
                } else {
                    resultAdvetrs.add(newAdvert)
                }
            }
            dataManager.saveAdverts(resultAdvetrs)
            loadingInterface.onLoaded(resultAdvetrs, result.loadMore())
        }, sourceType).execute(url)
    }

    fun loadAdvertDetails(advert: Advert, loadingInterface: LoadingAdvertDetailsInterface) {
        Log.e("loadAdvertDetails", advert.link)
        HtmlAdvertAsyncRequest({ output ->
            val advertDetails = output as AdvertDetails
            loadingInterface.onLoaded(advertDetails)
        }, advert.sourceType).execute(advert.link)
    }

    fun loadAvitoAdvertPhone(advert: Advert, loadingInterface: LoadingAdvertPhonesInterface) {
        val link = advert.link!!.replace("www.avito", "m.avito")
        HtmlAdvertPhoneAsyncRequest({ output ->
            val phones = output as List<String>
            loadingInterface.onLoaded(phones)
        }, SourceType.AVITO).execute(link)
    }

    fun loadAutoRuAdvertPhones(
        saleId: String,
        saleHash: String,
        loadingInterface: LoadingAdvertPhonesInterface
    ) {
        val link =
            "https://auto.ru/-/ajax/desktop/phones/?category=moto&sale_id=$saleId&sale_hash=$saleHash&isFromPhoneModal=true&__blocks=card-phones%2Ccall-number"
        HtmlAdvertPhoneAsyncRequest({ output ->
            val phones = output as List<String>
            loadingInterface.onLoaded(phones)
        }, SourceType.AUTO_RU).execute(link)
    }
}