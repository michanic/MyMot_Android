package ru.michanic.mymot.Models;

public class YoutubeVideo {

    String videoId;

    public YoutubeVideo(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getPreviewPath() {
        return "https://img.youtube.com/vi/" + videoId + "/mqdefault.jpg";
    }

    public String getVideoPath() {
        return "https://www.youtube.com/watch?v=" + videoId;
    }
}
