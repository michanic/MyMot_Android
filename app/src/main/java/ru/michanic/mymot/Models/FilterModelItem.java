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

    public final int type;

    public FilterModelItem(String sectionTitle) {
        this.type = SECTION_TITLE;
        this.title = sectionTitle;
    }

    public FilterModelItem(String title, boolean checked) {
        this.type = SIMPLE_CELL;
        this.title = title;
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
}
