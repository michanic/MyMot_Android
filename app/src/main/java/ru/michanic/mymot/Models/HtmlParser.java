package ru.michanic.mymot.Models;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;

public class HtmlParser {

    public ParseAdvertsResult parseAdverts(Document document, SourceType sourceType) {

        Elements elements = document.getElementsByClass("js-catalog-item-enum");
        List<Advert> adverts = new ArrayList();

        for (Element element: elements) {

            Advert advert = new Advert();

            String id = element.attr("data-item-id");
            String title = element.select("a.item-description-title-link span").text();
            if (title == null || title.length() == 0) {
                title = element.select("a.description-title-link span").text();
            }
            //TODO
            //guard title.checkForExteption() else { return nil }

            String city = element.select(".item_table-description .data p:eq(1)").text();
            String link = element.select(".item-description-title-link").attr("href");
            String priceText = element.select("span.price").text();
            String previewImage = "https:" + element.selectFirst("img.large-picture-img").attr("src");
            String date = element.select(".js-item-date").text();

            Log.e("Jsoup", city + " - " + title);
            //Log.e("Jsoup", priceText);
            //Log.e("Jsoup", previewImage);
            //Log.e("Jsoup", "-------------------------------");

            advert.setId(id);
            advert.setTitle(title);
            advert.setCity(city);
            advert.setLink(link);
            advert.setPrice(100);
            advert.setPreviewImage(previewImage);
            advert.setDate(date);

            adverts.add(advert);
        }


        return new ParseAdvertsResult(adverts, false);
    }

}
