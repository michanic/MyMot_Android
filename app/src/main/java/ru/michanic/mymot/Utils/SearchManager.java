package ru.michanic.mymot.Utils;

import android.util.Log;

import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.FilterSettedInterface;

public class SearchManager {

    private boolean filterChanged = false;

    private SearchFilterConfig filterConfig = MyMotApplication.configStorage.getFilterConfig();
    public FilterSettedInterface filterUpdated;
    public FilterSettedInterface searchPressedCallback;
    public FilterSettedInterface filterClosedCallback;

    public void setRegion(Location region) {
        filterConfig.setSelectedRegion(region);
        filterChanged = true;
        if (filterUpdated != null) {
            filterUpdated.onSelected(filterConfig);
        }
        MyMotApplication.configStorage.saveFilterConfig(filterConfig);
    }

    public Location getRegion() {
        return filterConfig.getSelectedRegion();
    }

    public String getRegionTitle() {
        Location region = filterConfig.getSelectedRegion();
        if (region != null) {
            return region.getName();
        } else {
            return  "По всей России";
        }
    }

    public void setManufacturer(Manufacturer manufacturer) {
        filterConfig.setSelectedManufacturer(manufacturer);
        filterConfig.setSelectedModel(null);
        filterChanged = true;
        if (filterUpdated != null) {
            filterUpdated.onSelected(filterConfig);
        }
        MyMotApplication.configStorage.saveFilterConfig(filterConfig);
    }

    public Manufacturer getManufacturer() {
        return filterConfig.getSelectedManufacturer();
    }

    public void setModel(Model model) {
        filterConfig.setSelectedManufacturer(null);
        filterConfig.setSelectedModel(model);
        filterChanged = true;
        if (filterUpdated != null) {
            filterUpdated.onSelected(filterConfig);
        }
        MyMotApplication.configStorage.saveFilterConfig(filterConfig);
    }

    public Model getModel() {
        return filterConfig.getSelectedModel();
    }

    public String getModelTitle() {
        Manufacturer manufacturer = filterConfig.getSelectedManufacturer();
        Model model = filterConfig.getSelectedModel();
        if (manufacturer != null) {
            return "Все мотоциклы " + manufacturer.getName();
        } else if (model != null) {
            return model.getName();
        } else {
            return "Все мотоциклы";
        }
    }

    public void setPriceFrom(int newPrice) {
        filterConfig.setPriceFrom(newPrice);
        filterChanged = true;
        //filterUpdated.onSelected(filterConfig);
        MyMotApplication.configStorage.saveFilterConfig(filterConfig);
    }

    public int getPriceFrom() {
        return filterConfig.getPriceFrom();
    }


    public void setPriceFor(int newPrice) {
        filterConfig.setPriceFor(newPrice);
        filterChanged = true;
        //filterUpdated.onSelected(filterConfig);
        MyMotApplication.configStorage.saveFilterConfig(filterConfig);
    }

    public int getPriceFor() {
        return filterConfig.getPriceFor();
    }

    public SearchFilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void backPressed() {
        Log.e("backPressed", String.valueOf(filterChanged));
        if (filterClosedCallback != null && filterChanged) {
            filterClosedCallback.onSelected(filterConfig);
            filterChanged = false;
        }
    }

    public void searchPressed() {
        searchPressedCallback.onSelected(filterConfig);
    }

}
