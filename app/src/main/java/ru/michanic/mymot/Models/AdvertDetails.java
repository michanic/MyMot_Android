package ru.michanic.mymot.Models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class AdvertDetails {

    private List<String> images;
    private String text;
    private List<LinkedTreeMap<String,String>> parameters;
    private String date;
    private String saleHash;
    private String warning;
    private String csrfToken;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LinkedTreeMap<String, String>> getParameters() {
        return parameters;
    }

    public void setParameters(List<LinkedTreeMap<String, String>> parameters) {
        this.parameters = parameters;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSaleHash() {
        return saleHash;
    }

    public void setSaleHash(String saleHash) {
        this.saleHash = saleHash;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getCsrfToken() {
        return csrfToken;
    }
}
