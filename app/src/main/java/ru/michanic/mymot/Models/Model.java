package ru.michanic.mymot.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Model extends RealmObject {

    @PrimaryKey
    private int id;

    private String code;
    private int class_id;
    private int m_id;
    private boolean favourite;
    private int first_year;
    private int last_year;
    private String name;
    private String preview_picture;
    private int sort;
    private String volume_text;
    private float volume_value;


    // связи
    private Category category;
    private Manufacturer manufacturer;
    private Volume volume_type;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getM_id() {
        return m_id;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getClass_id() {
        return class_id;
    }

    public Category getCategory() {
        return category;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPreview_picture() {
        return preview_picture;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }


    public String getYears() {
        return first_year + " - " + (last_year == 0 ? "настоящее время" : last_year);
    }

    public String getAvitoSearchName() {
        return manufacturer.getName().toLowerCase() + "+" + name.replace(" ", "+").toLowerCase();
    }

    public String getAutoruSearchName() {
        return manufacturer.getCode() + "/" + code + "/";
        //return manufacturer.getName().toUpperCase() + "%23" + code.toUpperCase();
    }

}
