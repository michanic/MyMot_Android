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
            Connection.Response response = Jsoup.connect(path).headers(headers).ignoreContentType(true).execute();
            String json = response.body();
            Log.e("json", json);
            String cardPhonesHtml = json.replace("{\"blocks\":{\"card-phones\":\"", "").replace("}}", "").replace("\\\"", "");
            cardPhonesDoc = Jsoup.parse(cardPhonesHtml);
        } catch (IOException e) {
            Log.e("error", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return cardPhonesDoc;
    }

}
