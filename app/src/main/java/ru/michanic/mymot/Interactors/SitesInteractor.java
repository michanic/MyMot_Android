package ru.michanic.mymot.Interactors;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.AdvertDetails;
import ru.michanic.mymot.Models.HtmlAdvertAsyncRequest;
import ru.michanic.mymot.Models.HtmlAdvertsAsyncRequest;
import ru.michanic.mymot.Models.ParseAdvertsResult;
import ru.michanic.mymot.Models.Source;
import ru.michanic.mymot.Protocols.AsyncRequestCompleted;
import ru.michanic.mymot.Protocols.LoadingAdvertDetailsInterface;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.Utils.DataManager;

public class SitesInteractor {

    private DataManager dataManager = new DataManager();

    public void loadFeedAdverts(Source source, final LoadingAdvertsInterface loadingInterface) {

        Log.e("loadFeedAdverts", source.getFeedPath());

        new HtmlAdvertsAsyncRequest(new AsyncRequestCompleted() {
            @Override
            public void processFinish(Object output) {
                ParseAdvertsResult result = (ParseAdvertsResult) output;
                List<Advert> newAdverts = result.getAdvetrs();
                List<Advert> resultAdvetrs = new ArrayList<>();
                for (Advert newAdvert: newAdverts) {
                    Advert savedAdvert = dataManager.getAdvertById(newAdvert.getId());
                    if (savedAdvert != null) {
                        resultAdvetrs.add(savedAdvert);
                    } else {
                        resultAdvetrs.add(newAdvert);
                    }
                }
                dataManager.saveAdverts(resultAdvetrs);
                loadingInterface.onLoaded(resultAdvetrs, result.loadMore());
            }
        }, source.getType()).execute(source.getFeedPath());
    }


    public void loadAdvertDetails(Advert advert, final LoadingAdvertDetailsInterface loadingInterface) {

        Log.e("loadAdvertDetails", advert.getLink());

        new HtmlAdvertAsyncRequest(new AsyncRequestCompleted() {
            @Override
            public void processFinish(Object output) {
                AdvertDetails advertDetails = (AdvertDetails) output;
                loadingInterface.onLoaded(advertDetails);
            }
        }, advert.getSourceType()).execute(advert.getLink());

    }
}
