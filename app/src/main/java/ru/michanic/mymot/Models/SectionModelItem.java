package ru.michanic.mymot.Models;

public class SectionModelItem {

    public static final int SECTION_TITLE = 0;
    public static final int LIST_MODEL = 1;
    public static final int SIMPLE_CELL = 2;
    public static final int PRICE_CELL = 3;

    public static final String PRICE_FROM_NAME = "От";
    public static final String PRICE_FOR_NAME = "До";

    String sectionTitle = null;
    Model model = null;
    String propertyTitle = null;
    String propertyValue = null;

    public final int type;

    public SectionModelItem(String sectionTitle) {
        this.type = SECTION_TITLE;
        this.sectionTitle = sectionTitle;
    }

    public SectionModelItem(Model model) {
        this.type = LIST_MODEL;
        this.model = model;
    }

    public SectionModelItem(String propertyTitle, String propertyValue) {
        this.propertyTitle = propertyTitle;
        if (propertyValue != null) {
            this.type = PRICE_CELL;
            this.propertyValue = propertyValue;
        } else {
            this.type = SIMPLE_CELL;
        }
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
