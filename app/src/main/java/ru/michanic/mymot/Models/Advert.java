package ru.michanic.mymot.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Advert extends RealmObject {

    @PrimaryKey
    private String id;

    private boolean active;
    private String city;
    private String date;
    private boolean favourite;
    private String link;
    private String phone;
    private String previewImage;
    private int price;
    private String title;

    public Advert() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
