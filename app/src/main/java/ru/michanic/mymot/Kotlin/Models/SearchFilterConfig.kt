package ru.michanic.mymot.Models;

public class SearchFilterConfig {

    Location selectedRegion;
    Manufacturer selectedManufacturer;
    Model selectedModel;
    int priceFrom = 0;
    int priceFor = 0;

    public Location getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Location selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public Manufacturer getSelectedManufacturer() {
        return selectedManufacturer;
    }

    public void setSelectedManufacturer(Manufacturer selectedManufacturer) {
        this.selectedManufacturer = selectedManufacturer;
    }

    public Model getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(Model selectedModel) {
        this.selectedModel = selectedModel;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceFor() {
        return priceFor;
    }

    public void setPriceFor(int priceFor) {
        this.priceFor = priceFor;
    }

    public String getMainSearchTitle() {
        String sectionTitle = "Все мотоциклы";
        if (selectedRegion != null) {
            sectionTitle = selectedRegion.getName();
        }
        if (priceFrom > 0) {
            if (priceFor > 0) {
                sectionTitle += ",\n" + priceFrom + " - " + priceFor + " руб.";
            } else {
                sectionTitle += ",\nЦена от " + priceFrom + " руб.";
            }
        } else if (priceFor > 0) {
            sectionTitle += ",\nЦена до " + priceFor + " руб.";
        }
        return sectionTitle;
    }

}
