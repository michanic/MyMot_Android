package ru.michanic.mymot.Models;

import java.util.List;

public class ModelDetails {

    private String preview_text;
    private List<String> images;
    private List<String> video_reviews;

    public String getPreview_text() {
        return preview_text;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getVideo_reviews() {
        return video_reviews;
    }
}
