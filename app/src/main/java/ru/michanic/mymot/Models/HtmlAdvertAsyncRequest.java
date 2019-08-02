package ru.michanic.mymot.Models;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.AsyncRequestCompleted;

public class HtmlAdvertAsyncRequest extends AsyncTask<String, Void, AdvertDetails> {

    public AsyncRequestCompleted delegate = null;
    private HtmlParser htmlParser = new HtmlParser();
    private SourceType sourceType;

    public HtmlAdvertAsyncRequest(AsyncRequestCompleted asyncResponse, SourceType sourceType) {
        this.delegate = asyncResponse;
        this.sourceType = sourceType;
    }


    @Override
    protected AdvertDetails doInBackground(String... arg) {
        String path = arg[0];
        Document doc = null;
        String csrf_token = "";

        try {
            Connection.Response res = Jsoup.connect(path).execute();
            doc = res.parse();
            csrf_token = res.cookie("_csrf_token");

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            doc = Jsoup.connect(path).get();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        AdvertDetails advertDetails = htmlParser.parseAdvertDetails(doc, sourceType);
        advertDetails.setCsrfToken(csrf_token);
        return advertDetails;
    }

    @Override
    protected void onPostExecute(AdvertDetails result) {
        //super.onPostExecute(result);
        delegate.processFinish(result);
    }

}
