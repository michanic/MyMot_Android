package ru.michanic.mymot.Models;

import com.google.gson.annotations.Expose;

public class AppAbout {

    @Expose
    private String text;

    public String getText() {
        return text;
    }
}
