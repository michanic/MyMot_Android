package ru.michanic.mymot.Kotlin.Interactors

import android.util.Log
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.Models.*
import ru.michanic.mymot.Kotlin.Protocols.AsyncRequestCompleted
import ru.michanic.mymot.Kotlin.Protocols.LoadingAdvertPhonesInterface
import ru.michanic.mymot.Kotlin.Utils.DataManager

class SitesInteractor {
    private val dataManager = DataManager()
    fun loadFeedAdverts(
        source: Source,
        onSuccess: ((List<Advert?>?, Boolean) -> Unit)
    ) {
        Log.e("loadFeedAdverts", source.feedPath)
        loadSourceAdverts(source.type, source.feedPath, onSuccess)

    }

    fun searchAdverts(
        page: Int,
        config: SearchFilterConfig,
        onSuccess: (List<Advert?>?, Boolean) -> Unit
    ) {
        Log.e("searchAdverts page: ", page.toString())
        val loadedAdverts: MutableList<Advert> = ArrayList()
        val loadMore = booleanArrayOf(false)
        var avitoModelQuery = ""
        val avitoManufacturer = config.selectedManufacturer
        val avitoModel = config.selectedModel
        if (avitoManufacturer != null) {
            avitoModelQuery = avitoManufacturer.avitoSearchName.toString()
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
        loadSourceAdverts(avitoSource.type, avitoUrl, { adverts, avitoMore ->
                loadedAdverts.addAll(adverts?.filterNotNull() ?: emptyList())
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
                loadSourceAdverts(autoruSource.type, autoruUrl) { adverts, autoruMore ->
                    loadedAdverts.addAll(adverts?.filterNotNull() ?: emptyList())
                    if (!loadMore[0]) {
                        loadMore[0] = autoruMore
                    }
                    onSuccess(loadedAdverts, loadMore[0])
                }
        })
    }

    private fun loadSourceAdverts(
        sourceType: SourceType,
        url: String?,
        onSuccess: (List<Advert?>?, Boolean) -> Unit
    ) {
        HtmlAdvertsAsyncRequest(object : AsyncRequestCompleted {
            override fun processFinish(output: Any?) {
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
                onSuccess(resultAdvetrs, result.loadMore())
            }

        }, sourceType).execute(url)
    }

    fun loadAdvertDetails(
        advert: Advert,
        onSuccess: ((advertDetails: AdvertDetails?) -> Unit),
        onFail: (() -> Unit)
    ) {
        Log.e("loadAdvertDetails", advert.link)
        HtmlAdvertAsyncRequest(object : AsyncRequestCompleted {
            override fun processFinish(output: Any?) {
                val advertDetails = output as AdvertDetails
                onSuccess(advertDetails)
            }
        }, advert.sourceType).execute(advert.link)
        onFail()
    }

    fun loadAvitoAdvertPhone(advert: Advert, loadingInterface: LoadingAdvertPhonesInterface) {
        val link = advert.link?.replace("www.avito", "m.avito")
        HtmlAdvertPhoneAsyncRequest(
            object : AsyncRequestCompleted {
                override fun processFinish(output: Any?) {
                    val phones = output as List<String>
                    loadingInterface.onLoaded(phones)
                }
            }, SourceType.AVITO
        ).execute(link)
    }

    fun loadAutoRuAdvertPhones(
        saleId: String,
        saleHash: String,
        loadingInterface: LoadingAdvertPhonesInterface,
    ) {
        val link =
            buildString {
                append("https://auto.ru/-/ajax/desktop/phones/?category=moto&sale_id=")
                append(saleId)
                append("&sale_hash=")
                append(saleHash)
                append("&isFromPhoneModal=true&__blocks=card-phones%2Ccall-number")
            }
        HtmlAdvertPhoneAsyncRequest(
            object : AsyncRequestCompleted {
                override fun processFinish(output: Any?) {
                    val phones = output as List<String>
                    loadingInterface.onLoaded(phones)
                }
            }, SourceType.AUTO_RU
        ).execute(link)
    }
}


