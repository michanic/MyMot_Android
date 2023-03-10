package ru.michanic.mymot.Protocols;

import ru.michanic.mymot.Kotlin.Models.AdvertDetails;

public interface LoadingAdvertDetailsInterface {
    void onLoaded(AdvertDetails advertDetails);
    void onFailed();
}
