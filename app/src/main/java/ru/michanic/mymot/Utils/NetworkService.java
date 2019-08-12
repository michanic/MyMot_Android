package ru.michanic.mymot.Utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NetworkService {

    Map<String, String> cookies = new HashMap<String, String>();

    public Connection.Response getHtmlData(String path) {
        try {
            Connection.Response response = Jsoup
                    .connect(path)
                    .cookies(cookies)
                    .execute();

            cookies = response.cookies();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection.Response getHtmlDataAsMobile(String path) {
        try {
            Connection.Response response = Jsoup
                    .connect(path)
                    .userAgent("Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36")
                    .cookies(cookies)
                    .execute();

            cookies = response.cookies();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Document getAutoRuPhonesData(String path, final String csrfToken) {

        Map<String, String> headers = new HashMap<String, String>() {{
            put("x-csrf-token", csrfToken);
            put("Cookie", "_csrf_token=" + csrfToken);
        }};

        Document cardPhonesDoc = null;
        try {
            Document doc = Jsoup.connect(path).headers(headers).ignoreContentType(true).get();

            String temp = "{\"blocks\":{\"card-phones\":\"<div class=\\\"card-phones card-phones_phones-redirect\\\"><div class=\\\"card-phones__phones-redirect\\\"><div class=\\\"card-phones__phones-redirect-icon dropdown-hover dropdown-hover_type_tooltip dropdown-hover__switcher i-bem\\\" data-bem='{\\\"dropdown-hover\\\":{}}'><div class=\\\"popup popup_theme_islands popup_target_anchor tooltip tooltip_theme_normal tooltip_to_bottom dropdown-hover__tooltip card-phones__phones-redirect-tooltip i-bem\\\" aria-hidden=\\\"true\\\" data-bem='{\\\"popup\\\":{\\\"mainOffset\\\":10,\\\"directions\\\":[\\\"bottom-center\\\"]}}'><div class=\\\"tooltip__text\\\"><strong>Продавец установил защиту номера</strong><br/>Смс доставлены не будут, звоните.</div><div class=\\\"tooltip__tail\\\"></div></div></div></div><div class=\\\"card-phones__phone-list card-phones__phone-list_tall\\\"><div class=\\\"card-phones__item card-phones__item_size_m card-phones__item_no-time\\\">+7 988 959-29-60</div><div class=\\\"card-phones__item card-phones__item_size_m card-phones__item_no-time\\\">+7 988 959-29-60</div></div></div>\",\"call-numbers\":\"<ul class=\\\"call-numbers show-phone__call-numbers\\\"><li class=\\\"call-numbers__item\\\"><div class=\\\"call-numbers__name\\\">dgoyl</div><div class=\\\"call-numbers__phone\\\">+7 988 959-29-60</div><div class=\\\"call-numbers__time\\\"></div></li><li class=\\\"call-numbers__item\\\"><div class=\\\"call-numbers__phone\\\">+7 988 959-29-60</div><div class=\\\"call-numbers__time\\\"></div></li></ul>\"}}";

            Log.e("doc.text", temp);

            JSONObject obj = new JSONObject(temp);//new JSONObject(doc.html());
            String cardPhonesHtml = obj.getJSONObject("blocks").getString("card-phones");
            Log.e("cardPhonesHtml", cardPhonesHtml);
            cardPhonesDoc = Jsoup.parse(cardPhonesHtml);
            Log.e("cardPhonesDoc", cardPhonesDoc.html());
        } catch (IOException e) {
            Log.e("error", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cardPhonesDoc;
    }

}
