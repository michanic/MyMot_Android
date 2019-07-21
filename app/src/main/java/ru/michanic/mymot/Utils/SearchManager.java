package ru.michanic.mymot.Utils;

import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SearchFilterConfig;
import ru.michanic.mymot.Protocols.FilterSettedInterface;

public class SearchManager {

    private SearchFilterConfig filterConfig = new SearchFilterConfig();
    public FilterSettedInterface filterUpdated;
    public FilterSettedInterface searchPressedCallback;
    public FilterSettedInterface filterClosedCallback;

    public void setRegion(Location region) {
        filterConfig.setSelectedRegion(region);
    }

    public String getRegionTitle() {
        Location region = filterConfig.getSelectedRegion();
        if (region != null) {
            return region.getName();
        } else {
            return  "По всей России";
        }
    }

    public void setModel(Manufacturer manufacturer) {
        filterConfig.setSelectedManufacturer(manufacturer);
        filterConfig.setSelectedModel(null);
    }

    public void setModel(Model model) {
        filterConfig.setSelectedManufacturer(null);
        filterConfig.setSelectedModel(model);
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

    public void backPressed() {
        filterClosedCallback.onSelected(filterConfig);
    }

    public void searchPressed() {
        searchPressedCallback.onSelected(filterConfig);
    }

}
