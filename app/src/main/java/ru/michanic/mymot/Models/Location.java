package ru.michanic.mymot.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Location extends RealmObject {

    @PrimaryKey
    private int id;

    private String autoru;
    private String avito;
    private String name;
    private int sort;

    // связи
    private int region_id;
    //private RealmList<Location> cities;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRegionId() {
        return region_id;
    }

    public void setRegionId(int regionId) {
        this.region_id = regionId;
    }

    public String getAvito() {
        return avito;
    }

    public String getAutoru() {
        return autoru;
    }

    /*public int getCitiesCount() {
        return cities.size();
    }

    public void setRegion(Location region) {
        this.region = region;
    }

    public Location getRegion() {
        return region;
    }*/

    /*public RealmList<Location> getCities() {
        return cities;
    }

    public void setCities(List<Location> cities) {
        this.cities.clear();
        for (Location city: cities) {
            this.cities.add(city);
        }
    }*/
}
