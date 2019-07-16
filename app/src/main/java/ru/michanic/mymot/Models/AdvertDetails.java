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

}
