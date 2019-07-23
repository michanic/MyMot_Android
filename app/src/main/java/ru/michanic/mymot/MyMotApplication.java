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
    public static ConfigStorage configStorage = new ConfigStorage();
    public static SearchManager searchManager = new SearchManager();
    public static DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setAppContext(Context appContext) {
        MyMotApplication.appContext = appContext;
        //Realm.init(appContext);
        dataManager = new DataManager();
    }

    public static ConfigStorage getConfigStorage() {
        return configStorage;
    }

}
