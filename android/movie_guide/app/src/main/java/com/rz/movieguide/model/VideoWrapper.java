package com.rz.movieguide.model;

import com.squareup.moshi.Json;

import java.util.List;

public class VideoWrapper {

    @Json(name = "results")
    List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "VideoWrapper{" +
                "videos=" + videos +
                '}';
    }
}
