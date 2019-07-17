package ru.michanic.mymot.Models;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Protocols.AsyncRequestCompleted;

public class HtmlAdvertsAsyncRequest extends AsyncTask<String, Void, ParseAdvertsResult> {

    public AsyncRequestCompleted delegate = null;
    private HtmlParser htmlParser = new HtmlParser();
    private SourceType sourceType;

    public HtmlAdvertsAsyncRequest(AsyncRequestCompleted asyncResponse, SourceType sourceType) {
        this.delegate = asyncResponse;
        this.sourceType = sourceType;
    }


    @Override
    protected ParseAdvertsResult doInBackground(String... arg) {
        String path = arg[0];
        Document doc = null;
        try {
            doc = Jsoup.connect(path).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ParseAdvertsResult parsedAdverts = htmlParser.parseAdverts(doc, sourceType);
        return parsedAdverts;
    }

    @Override
    protected void onPostExecute(ParseAdvertsResult result) {
        //super.onPostExecute(result);
        delegate.processFinish(result);
    }

}
