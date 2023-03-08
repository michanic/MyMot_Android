package ru.michanic.mymot.Models;

import java.util.List;

public class FilterModelItem {

    public static final int SECTION_TITLE = 0;
    public static final int SIMPLE_CELL = 1;
    public static final int CATEGORY_CELL = 2;

    String title = null;
    Category category = null;
    boolean checked = false;
    List<Model> models;
    Manufacturer manufacturer;

    public final int type;

    public FilterModelItem(String sectionTitle) {
        this.type = SECTION_TITLE;
        this.title = sectionTitle;
        this.checked = checked;
    }

    public FilterModelItem(boolean checked) {
        this.type = SIMPLE_CELL;
        this.title = "Все мотоциклы";
        this.checked = checked;
    }

    public FilterModelItem(Manufacturer manufacturer, boolean checked) {
        this.type = SIMPLE_CELL;
        this.title = "Все мотоциклы " + manufacturer.getName();
        this.manufacturer = manufacturer;
        this.checked = checked;
    }

    public FilterModelItem(Category category, List<Model> models) {
        this.type = CATEGORY_CELL;
        this.title = category.getName();
        this.category = category;
        this.models = models;
    }

    public String getTitle() {
        return title;
    }

    public List<Model> getModels() {
        return models;
    }

    public boolean isChecked() {
        return checked;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }
}
