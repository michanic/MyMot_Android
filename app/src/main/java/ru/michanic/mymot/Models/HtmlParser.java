package ru.michanic.mymot.Models;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Utils.DataManager;

public class HtmlParser {

    private JsonParser jsonParser = new JsonParser();
    private List<String> exteptedWords = MyMotApplication.getConfigStorage().exteptedWords;

    public ParseAdvertsResult parseAdverts(Document document, SourceType sourceType) {
        boolean loadMore = false;
        List<Advert> adverts = new ArrayList();
        if (document == null) {
            Log.e("document", "null");
            return new ParseAdvertsResult(adverts, loadMore);
        }
        Elements elements = document.select(sourceType.itemSelector());//document.getElementsByClass("js-catalog-item-enum");
        switch (sourceType) {
            case AVITO:
                for (Element element: elements) {
                    Advert advert = createOrUpdateFromAvito(element);
                    if (advert != null) {
                        adverts.add(advert);
                    }
                }
                loadMore = document.select(".pagination-page.js-pagination-next").hasText();
                break;
            case AUTO_RU:
                for (Element element: elements) {
                    Advert advert = createOrUpdateFromAutoRu(element);
                    if (advert != null) {
                        adverts.add(advert);
                    }
                }
                if (document.select(".pager__next.button__control .button__text").hasText()) {
                    loadMore = !document.select(".pager__next.button__control").hasClass("button_disabled");
                }
                break;
        }
        return new ParseAdvertsResult(adverts, loadMore);
    }

    private Advert createOrUpdateFromAvito(Element element) {

        String title = element.select("a.item-description-title-link span").text();
        if (title == null || title.length() == 0) {
            title = element.select("a.description-title-link span").text();
        }
        if (!checkForException(title)) {
            return null;
        }
        String id = element.attr("data-item-id");

        Advert advert = new Advert();

        String city = element.select(".item_table-description .data p:eq(1)").text();
        String link = SourceType.AVITO.domain() + element.select(".item-description-title-link").attr("href");
        String previewImage = element.selectFirst("img.large-picture-img").attr("src");
        if (!previewImage.contains("http")) {
            previewImage = "https:" + previewImage;
        }
        String date = element.select(".js-item-date").text();

        String priceText = element.select("span.price").text();
        priceText = priceText.replace(" ", "");
        priceText = priceText.replace("\u20BD", "");
        int priceInt;
        try {
            priceInt = Integer.parseInt(priceText);
        }
        catch (NumberFormatException e)
        {
            priceInt = 0;
        }

        advert.setId(id);
        advert.setTitle(title);
        advert.setCity(city);
        advert.setLink(link);
        advert.setPrice(priceInt);
        advert.setPreviewImage(previewImage);
        advert.setDate(date);

        return advert;
    }

    private Advert createOrUpdateFromAutoRu(Element element) {

        String title = element.select(".listing-item__link").text();
        if (!checkForException(title)) {
            return null;
        }
        String id = jsonParser.parse(element.attr("data-bem")).getAsJsonObject().getAsJsonObject("listing-item").get("id").getAsString();

        Advert advert = new Advert();

        String city = element.select(".listing-item__place").text();
        String link = element.select(".listing-item__link").attr("href");
        String previewImage = "https:" + element.selectFirst(".image.tile__image").attr("data-original");
        String date = element.select(".listing-item__date").text();

        String priceText = element.select(".listing-item__price").text();
        priceText = priceText.replace(" ", "");
        priceText = priceText.replace(" ", "");
        priceText = priceText.replace("\u20BD", "");
        int priceInt;
        try {
            priceInt = Integer.parseInt(priceText);
        }
        catch (NumberFormatException e)
        {
            priceInt = 0;
        }

        advert.setId(id);
        advert.setTitle(title);
        advert.setCity(city);
        advert.setLink(link);
        advert.setPrice(priceInt);
        advert.setPreviewImage(previewImage);
        advert.setDate(date);

        return advert;
    }

    private boolean checkForException(String title) {
        for (String word: exteptedWords) {
            if (title.toLowerCase().contains(word.toLowerCase())) {
                //Log.e("except", title);
                return false;
            }
        }
        return true;
    }

    public AdvertDetails parseAdvertDetails(Document document, SourceType sourceType) {
        AdvertDetails advertDetails = new AdvertDetails();
        switch (sourceType) {
            case AVITO:
                advertDetails = parseFromAvito(document, advertDetails);
                break;
            case AUTO_RU:
                advertDetails = parseFromAutoRu(document, advertDetails);
                break;
        }
        return advertDetails;
    }

    private AdvertDetails parseFromAvito(Document document, AdvertDetails advertDetails) {

        String text = "";
        if (!document.select(".item-description-text").html().isEmpty()) {
            text = document.select(".item-description-text").html();
        } else {
            text = document.select(".item-description-html").html();
        }

        String date = document.select(".title-info-actions-item .title-info-metadata-item-redesign").text();
        String warning = document.select(".item-view-warning-content .has-bold").text();

        List<String> images = new ArrayList<>();
        Elements elements = document.select(".js-gallery-img-frame");
        for (Element element: elements) {
            images.add("https:" + element.attr("data-url"));
        }

        advertDetails.setText(text);
        advertDetails.setDate(date);
        advertDetails.setWarning(warning);
        advertDetails.setImages(images);

        return advertDetails;
    }

    private AdvertDetails parseFromAutoRu(Document document, AdvertDetails advertDetails) {

        String text = document.select(".seller-details__text").html();
        String date = document.select(".card__stat .card__stat-item:eq(1)").text();
        String warning = document.select(".card__sold-message-header").text();

        List<String> images = new ArrayList<>();
        Elements elements = document.select(".gallery__thumb-item");
        for (Element element: elements) {
            images.add("https:" + element.attr("data-img"));
        }

        List<LinkedTreeMap<String,String>> parametersArray = new ArrayList<>();
        Elements parameters = document.select(".card__info .card__info-label");
        for (Element element: parameters) {
            String title = element.text();
            String value = element.nextElementSibling().text();
            LinkedTreeMap<String,String> parameter = new LinkedTreeMap<>();
            parameter.put(title, value);
            parametersArray.add(parameter);
        }
        advertDetails.setParameters(parametersArray);

        try {
            String saleHash = jsonParser.parse(document.select(".stat-publicapi").attr("data-bem")).getAsJsonObject().getAsJsonObject("card").get("sale_hash").getAsString();
            advertDetails.setSaleHash(saleHash);
        } catch (IllegalStateException error) {
            Log.e("error", error.getLocalizedMessage());
        }

        advertDetails.setText(text);
        advertDetails.setDate(date);
        advertDetails.setWarning(warning);
        advertDetails.setImages(images);

        return advertDetails;
    }

    public String parsePhoneFromAvito(Document document) {

        //String selector = "[data-marker=\"item-contact-bar/call\"]";
        //Element element = document.select(selector).first();

        Elements phoneBar = document.getElementsByAttributeValue("data-marker", "item-contact-bar/call");
        if (!phoneBar.isEmpty()) {
            return phoneBar.first().attr("href").replace("tel:", "");
        } else {
            return "";
        }
    }

    public List<String> parsePhonesFromAutoRu(Document document) {
        //Log.e("parsePhonesFromAutoRu", document.html());
        return new ArrayList<>();
    }

}
