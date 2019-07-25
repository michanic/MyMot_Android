package ru.michanic.mymot;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import ru.michanic.mymot.Utils.ConfigStorage;
import ru.michanic.mymot.Utils.DataManager;
import ru.michanic.mymot.Utils.SearchManager;

public class MyMotApplication extends Application {

    public static volatile Context appContext;
    public static SearchManager searchManager;
    public static DataManager dataManager;
    public static ConfigStorage configStorage;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setAppContext(Context appContext) {
        MyMotApplication.appContext = appContext;
        dataManager = new DataManager();
        configStorage = new ConfigStorage(appContext);
        searchManager = new SearchManager();

        //Realm.init(appContext);
    }

    public static ConfigStorage getConfigStorage() {
        return configStorage;
    }

}
