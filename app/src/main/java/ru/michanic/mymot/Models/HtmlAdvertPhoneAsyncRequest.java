package ru.michanic.mymot.Models;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.AsyncRequestCompleted;

public class HtmlAdvertPhoneAsyncRequest extends AsyncTask<String, Void, List<String>> {

    public AsyncRequestCompleted delegate = null;
    private HtmlParser htmlParser = new HtmlParser();
    private SourceType sourceType;

    public HtmlAdvertPhoneAsyncRequest(AsyncRequestCompleted asyncResponse, SourceType sourceType) {
        this.delegate = asyncResponse;
        this.sourceType = sourceType;
    }

    @Override
    protected  List<String> doInBackground(String... arg) {
        String path = arg[0];
        Log.e("doInBackground", path);
        Document doc = null;
        List<String> phones = new ArrayList<>();
        final String csrfToken = MyMotApplication.configStorage.getCsrfToken();
        Log.e("csrfToken", csrfToken);
        try {
            if (sourceType == SourceType.AVITO) {
                doc = Jsoup.connect(path)
                        .userAgent("Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36")
                        .get();
                phones.add(htmlParser.parsePhoneFromAvito(doc));
            } else if (sourceType == SourceType.AUTO_RU) {

                Map<String, String> headers = new HashMap<String, String>() {{
                    put("x-csrf-token", csrfToken);
                    put("Cookie", "_csrf_token=" + csrfToken);
                }};

                Connection.Response res = Jsoup.connect(path).headers(headers).execute();
                Log.e("doc", res.body());
                doc = res.parse();
                phones = htmlParser.parsePhonesFromAutoRu(doc);
            }
            Log.e("doc", doc.text());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return phones;
    }

    @Override
    protected void onPostExecute(List<String> phones) {
        delegate.processFinish(phones);
    }

}
