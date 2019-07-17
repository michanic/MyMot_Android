package ru.michanic.mymot.Protocols;

import ru.michanic.mymot.Models.AdvertDetails;

public interface LoadingAdvertDetailsInterface {
    void onLoaded(AdvertDetails advertDetails);
    void onFailed();
}
