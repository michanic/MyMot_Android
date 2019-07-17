package ru.michanic.mymot.Models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.michanic.mymot.Protocols.Const;

public class ModelDetails {

    private String preview_text;
    private List<String> images;
    private List<String> video_reviews;
    private List<LinkedTreeMap<String,String>> parameters;

    public String getPreview_text() {
        return preview_text;
    }

    public List<String> getImages() {
        List<String> fullImages = new ArrayList<>();
        for (String path: images) {
            fullImages.add(Const.DOMAIN + path);
        }
        return fullImages;
    }

    public List<String> getVideo_reviews() {
        return video_reviews;
    }

    public List<LinkedTreeMap<String,String>> getParameters() {
        return parameters;
    }
}
