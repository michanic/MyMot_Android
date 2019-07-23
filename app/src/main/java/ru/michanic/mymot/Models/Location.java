package ru.michanic.mymot.Models;

import java.util.List;

import io.realm.RealmList;
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
    private Location region;
    private RealmList<Location> cities;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCitiesCount() {
        return cities.size();
    }

    public void setRegion(Location region) {
        this.region = region;
    }

    public Location getRegion() {
        return region;
    }

    public RealmList<Location> getCities() {
        return cities;
    }

    public void setCities(List<Location> cities) {
        this.cities.clear();
        for (Location city: cities) {
            this.cities.add(city);
        }
    }
}
