package ru.michanic.mymot;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import ru.michanic.mymot.Utils.ConfigStorage;
import ru.michanic.mymot.Utils.DataManager;

public class MyMotApplication extends Application {

    public static volatile Context appContext;
    private static ConfigStorage configStorage = new ConfigStorage();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setAppContext(Context appContext) {
        MyMotApplication.appContext = appContext;
        Realm.init(appContext);
    }

    public static ConfigStorage getConfigStorage() {
        return configStorage;
    }

}
