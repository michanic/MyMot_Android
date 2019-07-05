package ru.michanic.mymot.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.michanic.mymot.Protocols.Const;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public String getPriceString() {
        return price + Const.RUB;
    }

    public String getDetails() {
        return city + "\n" + date;
    }
}
