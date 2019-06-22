package ru.michanic.mymot.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Manufacturer extends RealmObject {

    @PrimaryKey
    private int id;

    private String code;
    private String image;
    private String name;
    private int sort;

    // связи
    private RealmList<Model> models;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public RealmList<Model> getModels() {
        return models;
    }
}
