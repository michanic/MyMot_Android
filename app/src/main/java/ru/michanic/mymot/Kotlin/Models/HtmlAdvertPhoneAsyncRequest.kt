package ru.michanic.mymot.Models;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
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

        if (sourceType == SourceType.AVITO) {
            Connection.Response response = MyMotApplication.networkService.getHtmlDataAsMobile(path);
            try {
                doc = response.parse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            phones.add(htmlParser.parsePhoneFromAvito(doc));

        } else if (sourceType == SourceType.AUTO_RU) {
            doc = MyMotApplication.networkService.getAutoRuPhonesData(path, csrfToken);
            if (doc != null) {
                try {
                    phones = htmlParser.parsePhonesFromAutoRu(doc);
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON");
                }
            } else {
                Log.e("doc", "is empty");
            }

            //doc = res.parse();
        }


        return phones;
    }

    @Override
    protected void onPostExecute(List<String> phones) {
        delegate.processFinish(phones);
    }

}
