package ru.michanic.mymot.Interactors;

import android.util.Log;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Models.HtmlAsyncRequest;
import ru.michanic.mymot.Models.HtmlParser;
import ru.michanic.mymot.Models.ParseAdvertsResult;
import ru.michanic.mymot.Models.Source;
import ru.michanic.mymot.Protocols.AsyncRequestCompleted;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.UI.Frames.Search.SearchHomeFragment;

public class SitesInteractor {

    public void loadFeedAdverts(Source source, final LoadingAdvertsInterface loadingInterface) {

        Log.e("loadFeedAdverts", source.getFeedPath());

        new HtmlAsyncRequest(new AsyncRequestCompleted() {
            @Override
            public void processFinish(Object output) {
                ParseAdvertsResult result = (ParseAdvertsResult) output;
                loadingInterface.onLoaded(result.getAdvetrs(), result.loadMore());
            }
        }, source.getType()).execute(source.getFeedPath());
    }

    private void loadSourceAdverts(SourceType sourceType, String url) {

    }

}
