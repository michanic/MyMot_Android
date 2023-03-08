package ru.michanic.mymot.Protocols;

import ru.michanic.mymot.Kotlin.Models.ModelDetails;

public interface LoadingModelDetailsInterface {
    void onLoaded(ModelDetails modelDetails);
    void onFailed();
}
