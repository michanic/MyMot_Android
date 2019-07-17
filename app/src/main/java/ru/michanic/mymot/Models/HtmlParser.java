package ru.michanic.mymot.Models;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Utils.DataManager;

public class HtmlParser {

    JsonParser jsonParser = new JsonParser();

    public ParseAdvertsResult parseAdverts(Document document, SourceType sourceType) {
        boolean loadMore = false;
        Elements elements = document.select(sourceType.itemSelector());//document.getElementsByClass("js-catalog-item-enum");
        List<Advert> adverts = new ArrayList();

        switch (sourceType) {
            case AVITO:
                for (Element element: elements) {
                    adverts.add(createOrUpdateFromAvito(element));
                }
                break;
            case AUTO_RU:
                for (Element element: elements) {
                    adverts.add(createOrUpdateFromAutoRu(element));
                }
                break;
        }
        return new ParseAdvertsResult(adverts, loadMore);
    }

    private Advert createOrUpdateFromAvito(Element element) {
        Advert advert = new Advert();

        String id = element.attr("data-item-id");
        String title = element.select("a.item-description-title-link span").text();
        if (title == null || title.length() == 0) {
            title = element.select("a.description-title-link span").text();
        }

        //TODO
        //guard title.checkForExteption() else { return nil }

        String city = element.select(".item_table-description .data p:eq(1)").text();
        String link = SourceType.AVITO.domain() + element.select(".item-description-title-link").attr("href");
        String previewImage = "https:" + element.selectFirst("img.large-picture-img").attr("src");
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
        Advert advert = new Advert();

        String id = jsonParser.parse(element.attr("data-bem")).getAsJsonObject().getAsJsonObject("listing-item").get("id").getAsString();
        String title = element.select(".listing-item__link").text();
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

        String text = document.select(".item-description-text p").html();
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

        // TODO: load parameters

        List<String> images = new ArrayList<>();
        Elements elements = document.select(".gallery__thumb-item");
        for (Element element: elements) {
            images.add("https:" + element.attr("data-img"));
        }

        String saleHash = jsonParser.parse(document.select(".stat-publicapi").attr("data-bem")).getAsJsonObject().getAsJsonObject("card").get("sale_hash").getAsString();

        advertDetails.setText(text);
        advertDetails.setDate(date);
        advertDetails.setWarning(warning);
        advertDetails.setImages(images);
        advertDetails.setSaleHash(saleHash);

        return advertDetails;
    }

}
