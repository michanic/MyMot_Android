package ru.michanic.mymot.Models;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.michanic.mymot.Enums.SourceType;
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
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        String str = formatter.format(price);
        return str + Const.RUB;
    }

    public String getDetails() {
        return city + "\n" + date;
    }

    public SourceType getSourceType() {
        if (link.contains("avito")) {
            return SourceType.AVITO;
        } else {
            return SourceType.AUTO_RU;
        }
    }

}
