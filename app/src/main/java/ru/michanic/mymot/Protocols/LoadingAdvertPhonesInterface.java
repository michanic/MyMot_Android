package ru.michanic.mymot.Protocols;

import java.util.List;

import ru.michanic.mymot.Models.AdvertDetails;

public interface LoadingAdvertPhonesInterface {
    void onLoaded(List<String> phones);
}
