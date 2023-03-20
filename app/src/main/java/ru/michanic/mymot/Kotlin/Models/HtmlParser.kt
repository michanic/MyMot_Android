package ru.michanic.mymot.Kotlin.Models

import android.util.Log
import com.google.gson.JsonParser
import com.google.gson.internal.LinkedTreeMap
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.MyMotApplication
import java.util.*

class HtmlParser {
    private val jsonParser = JsonParser()
    private val exteptedWords = MyMotApplication.configStorage?.exteptedWords
    fun parseAdverts(document: Document?, sourceType: SourceType): ParseAdvertsResult {
        var loadMore = false
        val adverts: MutableList<Advert> = ArrayList<Advert>()
        if (document == null) {
            Log.e("document", "null")
            return ParseAdvertsResult(adverts, loadMore)
        }
        val elements =
            document.select(sourceType.itemSelector()) //document.getElementsByClass("js-catalog-item-enum");
        when (sourceType) {
            SourceType.AVITO -> {
                for (element in elements) {
                    val advert = createOrUpdateFromAvito(element)
                    if (advert != null) {
                        adverts.add(advert)
                    }
                }
                loadMore = document.select(".pagination-page.js-pagination-next").hasText()
            }
            SourceType.AUTO_RU -> {
                for (element in elements) {
                    val advert = createOrUpdateFromAutoRu(element)
                    if (advert != null) {
                        adverts.add(advert)
                    }
                }
                if (document.select(".pager__next.button__control .button__text").hasText()) {
                    loadMore =
                        !document.select(".pager__next.button__control").hasClass("button_disabled")
                }
            }
        }
        return ParseAdvertsResult(adverts, loadMore)
    }

    private fun createOrUpdateFromAvito(element: Element): Advert? {
        var title = element.select("a.item-description-title-link span").text()
        if (title == null || title.length == 0) {
            title = element.select("a.description-title-link span").text()
        }
        if (!checkForException(title)) {
            return null
        }
        val id = element.attr("data-item-id")
        val advert = Advert()
        val city = element.select(".item_table-description .data p:eq(1)").text()
        val link =
            SourceType.AVITO.domain() + element.select(".item-description-title-link").attr("href")
        var previewImage = element.selectFirst("img.large-picture-img").attr("src")
        if (!previewImage.contains("http")) {
            previewImage = "https:$previewImage"
        }
        val date = element.select(".js-item-date").text()
        var priceText = element.select("span.price").text()
        priceText = priceText.replace(" ", "")
        priceText = priceText.replace("\u20BD", "")
        val priceInt: Int
        priceInt = try {
            priceText.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        advert.id = id
        advert.title = title
        advert.city = city
        advert.link = link
        advert.setPrice(priceInt)
        advert.previewImage = previewImage
        advert.date = date
        return advert
    }

    private fun createOrUpdateFromAutoRu(element: Element): Advert? {
        val title = element.select(".listing-item__link").text()
        if (!checkForException(title)) {
            return null
        }
        val id =
            jsonParser.parse(element.attr("data-bem")).asJsonObject.getAsJsonObject("listing-item")["id"].asString
        val advert = Advert()
        var previewImage = ""
        val city = element.select(".listing-item__place").text()
        val link = element.select(".listing-item__link").attr("href")
        val firstImage = element.selectFirst(".image.tile__image")
        if (firstImage != null) {
            previewImage =
                "https:" + element.selectFirst(".image.tile__image").attr("data-original")
        }
        val date = element.select(".listing-item__date").text()
        var priceText = element.select(".listing-item__price").text()
        priceText = priceText.replace(" ", "")
        priceText = priceText.replace(" ", "")
        priceText = priceText.replace("\u20BD", "")
        val priceInt: Int
        priceInt = try {
            priceText.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        advert.id = id
        advert.title = title
        advert.city = city
        advert.link = link
        advert.setPrice(priceInt)
        advert.previewImage = previewImage
        advert.date = date
        return advert
    }

    private fun checkForException(title: String?): Boolean {
        if (exteptedWords != null) {
            for (word in exteptedWords) {
                if (title!!.lowercase(Locale.getDefault())
                        .contains(word.lowercase(Locale.getDefault()))
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun parseAdvertDetails(document: Document, sourceType: SourceType?): AdvertDetails {
        var advertDetails = AdvertDetails()
        when (sourceType) {
            SourceType.AVITO -> advertDetails = parseFromAvito(document, advertDetails)
            SourceType.AUTO_RU -> advertDetails = parseFromAutoRu(document, advertDetails)
        }
        return advertDetails
    }

    private fun parseFromAvito(document: Document, advertDetails: AdvertDetails): AdvertDetails {
        var text: String? = ""
        text = if (!document.select(".item-description-text").html().isEmpty()) {
            document.select(".item-description-text").html()
        } else {
            document.select(".item-description-html").html()
        }
        val date =
            document.select(".title-info-actions-item .title-info-metadata-item-redesign").text()
        val warning = document.select(".item-view-warning-content .has-bold").text()
        val images: MutableList<String> = ArrayList()
        val elements = document.select(".js-gallery-img-frame")
        for (element in elements) {
            images.add("https:" + element.attr("data-url"))
        }
        advertDetails.text = text
        advertDetails.date = date
        advertDetails.warning = warning
        advertDetails.images = images
        return advertDetails
    }

    private fun parseFromAutoRu(document: Document, advertDetails: AdvertDetails): AdvertDetails {
        Log.e("parseAdvertDetails", "parseFromAutoRu")
        val text = document.select(".seller-details__text").html()
        val date = document.select(".card__stat .card__stat-item:eq(1)").text()
        val warning = document.select(".card__sold-message-header").text()
        val images: MutableList<String> = ArrayList()
        val elements = document.select(".gallery__thumb-item")
        for (element in elements) {
            images.add("https:" + element.attr("data-img"))
        }
        Log.e("images", images.size.toString())
        val parametersArray: MutableList<LinkedTreeMap<String, String>> = ArrayList()
        val parameters = document.select(".card__info .card__info-label")
        for (element in parameters) {
            val title = element.text()
            val value = element.nextElementSibling().text()
            val parameter = LinkedTreeMap<String, String>()
            parameter[title] = value
            parametersArray.add(parameter)
        }
        advertDetails.parameters = parametersArray
        try {
            val saleHash = jsonParser.parse(
                document.select(".stat-publicapi").attr("data-bem")
            ).asJsonObject.getAsJsonObject("card")["sale_hash"].asString
            advertDetails.saleHash = saleHash
        } catch (error: IllegalStateException) {
            Log.e("error", error.localizedMessage)
        }
        advertDetails.text = text
        advertDetails.date = date
        advertDetails.warning = warning
        advertDetails.images = images
        return advertDetails
    }

    fun parsePhoneFromAvito(document: Document): String {

        val phoneBar = document.getElementsByAttributeValue("data-marker", "item-contact-bar/call")
        return if (!phoneBar.isEmpty()) {
            phoneBar.first().attr("href").replace("tel:", "")
        } else {
            ""
        }
    }

    fun parsePhonesFromAutoRu(document: Document): List<String> {
        val phones: MutableList<String> = ArrayList()
        val elements = document.select(".card-phones__item")
        for (element in elements) {
            Log.e("element", element.html())
            phones.add(element.text())
        }
        Log.e("phones", phones.size.toString())
        return phones
    }
}