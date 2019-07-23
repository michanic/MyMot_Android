package ru.michanic.mymot.Utils;

import android.content.SharedPreferences;

import java.util.Collections;
import java.util.List;

import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.MyMotApplication;

public class ConfigStorage {

    private static final String PREFS_NAME = "MyMotPreferences";

    private static final String LOCATION_ID = "search_location_id";
    private static final String MANUFACTURER_ID = "search_manufacturer_id";
    private static final String MODEL_ID = "search_model_id";
    private static final String PRICE_FROM = "search_price_from";
    private static final String PRICE_FOR = "search_price_for";
    private static final String CSRF_TOKEN = "autoru_csrf_token";

    private SharedPreferences settings;// = MyMotApplication.appContext.getSharedPreferences(PREFS_NAME, 0);

    public List<String> exteptedWords = Collections.emptyList();
    public String aboutText = "";


    public void saveFilterConfig(SearchFilterConfig filterConfig) {
        SharedPreferences.Editor editor = settings.edit();

        /*

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Username",txtUname.getText().toString());
        editor.putString("Password",txtPWD.getText().toString());
        editor.commit();
        and then get SharedPreferences from below code

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        txtUname.setText(settings.getString("Username", "").toString());
        txtPWD.setText(settings.getString("Password", "").toString());
         */

        Location region = filterConfig.getSelectedRegion();
        // TODO; try try

        editor.commit();
    }

    public SearchFilterConfig getFilterConfig() {
        SharedPreferences.Editor editor = settings.edit();
        SearchFilterConfig filterConfig = new SearchFilterConfig();

        settings.getString("Username", "").toString();

        return filterConfig;
    }

    public void saveCsrfToken(String token) {

    }

    public String getCsrfToken() {


        return "";
    }

}
