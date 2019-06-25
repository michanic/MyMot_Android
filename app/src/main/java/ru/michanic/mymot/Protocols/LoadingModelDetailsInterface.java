package ru.michanic.mymot.Protocols;

import ru.michanic.mymot.Models.ModelDetails;

public interface LoadingModelDetailsInterface {
    void onLoaded(ModelDetails modelDetails);
    void onFailed();
}
