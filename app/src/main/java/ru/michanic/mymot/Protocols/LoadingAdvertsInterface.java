package ru.michanic.mymot.Protocols;

import java.util.List;

import ru.michanic.mymot.Kotlin.Models.Advert;

public interface LoadingAdvertsInterface {
    void onLoaded(List<Advert> adverts, boolean loadMore);
    void onFailed();
}
