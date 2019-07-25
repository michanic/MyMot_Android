package ru.michanic.mymot.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.Const;

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

        return filterConfig;
    }

    public void saveCsrfToken(String token) {

    }

    public String getCsrfToken() {


        return "";
    }

}
