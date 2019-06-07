package ru.michanic.mymot.Models;

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

}
