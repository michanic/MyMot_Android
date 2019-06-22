package ru.michanic.mymot.Models;

public class SectionModelItem {

    String sectionTitle = null;
    Model model = null;

    public SectionModelItem(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public SectionModelItem(Model model) {
        this.model = model;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }


    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
