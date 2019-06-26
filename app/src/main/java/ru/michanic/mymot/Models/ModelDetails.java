package ru.michanic.mymot.Models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelDetails {

    private String preview_text;
    private List<String> images;
    private List<String> video_reviews;
    private List<LinkedTreeMap<String,String>> parameters;

    public String getPreview_text() {
        return preview_text;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getVideo_reviews() {
        return video_reviews;
    }

    public List<LinkedTreeMap<String,String>> getParameters() {
        return parameters;
    }
}
