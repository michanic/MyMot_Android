package ru.michanic.mymot.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import ru.michanic.mymot.Kotlin.Models.Location;
import ru.michanic.mymot.Kotlin.Models.Manufacturer;
import ru.michanic.mymot.Kotlin.Models.Model;
import ru.michanic.mymot.Kotlin.Models.SearchFilterConfig;
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

    public ConfigStorage(Context context) {
        this.settings = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public void saveFilterConfig(SearchFilterConfig filterConfig) {
        SharedPreferences.Editor editor = settings.edit();

        Location location = filterConfig.getSelectedRegion();
        if (location != null) {
            editor.putInt(LOCATION_ID, location.getId());
        } else {
            editor.putInt(LOCATION_ID, 0);
        }

        Manufacturer manufacturer = filterConfig.getSelectedManufacturer();
        if (manufacturer != null) {
            editor.putInt(MANUFACTURER_ID, manufacturer.getId());
        } else {
            editor.putInt(MANUFACTURER_ID, 0);
        }

        Model model = filterConfig.getSelectedModel();
        if (model != null) {
            editor.putInt(MODEL_ID, model.getId());
        } else {
            editor.putInt(MODEL_ID, 0);
        }

        editor.putInt(PRICE_FROM, filterConfig.getPriceFrom());
        editor.putInt(PRICE_FOR, filterConfig.getPriceFor());

        editor.commit();
    }

    public SearchFilterConfig getFilterConfig() {
        SearchFilterConfig filterConfig = new SearchFilterConfig();

        Location selectedRegion = MyMotApplication.dataManager.getRegionById(settings.getInt(LOCATION_ID, 0));
        if (selectedRegion != null) {
            filterConfig.setSelectedRegion(selectedRegion);
        }

        Manufacturer manufacturer = MyMotApplication.dataManager.getManufacturerById(settings.getInt(MANUFACTURER_ID, 0));
        if (manufacturer != null) {
            filterConfig.setSelectedManufacturer(manufacturer);
        }

        Model model = MyMotApplication.dataManager.getModelById(settings.getInt(MODEL_ID, 0));
        if (model != null) {
            filterConfig.setSelectedModel(model);
        }

        filterConfig.setPriceFrom(settings.getInt(PRICE_FROM, 0));
        filterConfig.setPriceFor(settings.getInt(PRICE_FOR, 0));

        return filterConfig;
    }


    public void saveCsrfToken(String token) {
        String oldToken = settings.getString(CSRF_TOKEN, "");
        if (oldToken.length() > 0) {
            return;
        }

        if (token != null) {
            if (token.length() > 0) {
                Log.e("saveCsrfToken", token);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(CSRF_TOKEN, token);
                editor.commit();
            }
        }
    }

    public void clearCsrfToken() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CSRF_TOKEN, "");
        editor.commit();
    }

    public String getCsrfToken() {
        return settings.getString(CSRF_TOKEN, "");
    }

}
