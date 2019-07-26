package ru.michanic.mymot.Interactors;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.AdvertDetails;
import ru.michanic.mymot.Models.HtmlAdvertAsyncRequest;
import ru.michanic.mymot.Models.HtmlAdvertsAsyncRequest;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.ParseAdvertsResult;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.Models.Source;
import ru.michanic.mymot.Protocols.AsyncRequestCompleted;
import ru.michanic.mymot.Protocols.LoadingAdvertDetailsInterface;
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface;
import ru.michanic.mymot.Utils.DataManager;

public class SitesInteractor {

    private DataManager dataManager = new DataManager();

    public void loadFeedAdverts(Source source, final LoadingAdvertsInterface loadingInterface) {
        Log.e("loadFeedAdverts", source.getFeedPath());
        loadSourceAdverts(source.getType(), source.getFeedPath(), loadingInterface);
    }

    public void searchAdverts(final int page, final SearchFilterConfig config, final LoadingAdvertsInterface loadingInterface) {
        Log.e("searchAdverts page: ", String.valueOf(page));

        final List<Advert> loadedAdverts = new ArrayList<>();
        final boolean[] loadMore = {false};

        String avitoModelQuery = "";
        Manufacturer avitoManufacturer = config.getSelectedManufacturer();
        Model avitoModel = config.getSelectedModel();
        if (avitoManufacturer != null) {
            avitoModelQuery = avitoManufacturer.getAvitoSearchName();
        } else if (avitoModel != null) {
            avitoModelQuery = avitoModel.getAvitoSearchName();
        }

        Source avitoSource = new Source(SourceType.AVITO);
        avitoSource.setModel(avitoModelQuery);
        avitoSource.setRegion(config.getSelectedRegion().getAvito());
        avitoSource.setpMin(config.getPriceFrom());
        avitoSource.setpMax(config.getPriceFor());
        avitoSource.setPage(page);
        String avitoUrl = avitoSource.getSearchPath();
        Log.e("avitoUrl: ", avitoUrl);

        loadSourceAdverts(avitoSource.getType(), avitoUrl, new LoadingAdvertsInterface() {
            @Override
            public void onLoaded(List<Advert> adverts, boolean avitoMore) {
                loadedAdverts.addAll(adverts);
                loadMore[0] = avitoMore;

                String autoruModelQuery = "";
                Manufacturer autoruManufacturer = config.getSelectedManufacturer();
                Model autoruModel = config.getSelectedModel();
                if (autoruManufacturer != null) {
                    autoruModelQuery = autoruManufacturer.getAvitoSearchName();
                } else if (autoruModel != null) {
                    autoruModelQuery = autoruModel.getAvitoSearchName();
                }

                Source autoruSource = new Source(SourceType.AUTO_RU);
                autoruSource.setModel(autoruModelQuery);
                autoruSource.setRegion(config.getSelectedRegion().getAutoru());
                autoruSource.setpMin(config.getPriceFrom());
                autoruSource.setpMax(config.getPriceFor());
                autoruSource.setPage(page);
                String autoruUrl = autoruSource.getSearchPath();
                Log.e("autoruUrl: ", autoruUrl);

                loadSourceAdverts(autoruSource.getType(), autoruUrl, new LoadingAdvertsInterface() {
                    @Override
                    public void onLoaded(List<Advert> adverts, boolean autoruMore) {
                        loadedAdverts.addAll(adverts);
                        if (!loadMore[0]) {
                            loadMore[0] = autoruMore;
                        }
                        loadingInterface.onLoaded(loadedAdverts, loadMore[0]);
                    }

                    @Override
                    public void onFailed() {
                        loadingInterface.onFailed();
                    }
                });

            }
            @Override
            public void onFailed() {
                loadingInterface.onFailed();
            }
        });
    }

    private void loadSourceAdverts(SourceType sourceType, String url, final LoadingAdvertsInterface loadingInterface) {
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
        }, sourceType).execute(url);
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
