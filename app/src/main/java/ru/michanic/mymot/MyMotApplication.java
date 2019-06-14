package ru.michanic.mymot;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class MyMotApplication extends Application {

    public static volatile Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static void setAppContext(Context appContext) {
        MyMotApplication.appContext = appContext;
    }
}
