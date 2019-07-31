package ru.michanic.mymot.Models;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {

    @PrimaryKey
    private int id;

    private String description;
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

    public void setModels(RealmList<Model> models) {
        this.models = models;
    }

    public RealmResults<Model> getModels() {
        return models.sort("sort");
    }

    public List<Model> getManufacturerModels(int manufacturerId) {
        List<Model> modelsList = new ArrayList<>();
        for (Model model: getModels()) {
            if (model.getManufacturer().getId() == manufacturerId) {
                modelsList.add(model);
            }
        }
        return modelsList;
    }

}
