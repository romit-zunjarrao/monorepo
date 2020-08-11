package com.rz.movieguide.model;

import android.util.Log;

import com.squareup.moshi.Json;

public class Video {
    public static final String SITE_YOUTUBE = "YouTube";
    static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%1$s";
    static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%1$s/0.jpg";
    private static final String TAG = "Video";
    private String id;

    @Json(name = "key")
    private String videoId;
    private String name;
    private String site;
    private String size;
    private String type;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl(){
        if(SITE_YOUTUBE.equalsIgnoreCase(site)) {
            Log.d(TAG, "getUrl: " +YOUTUBE_VIDEO_URL + videoId);
//            return YOUTUBE_VIDEO_URL + videoId;
            return String.format(YOUTUBE_VIDEO_URL,videoId);

        }
        return "";
    }

    public String getThumbnailUrl(){
        if(SITE_YOUTUBE.equalsIgnoreCase(site)){
            Log.d(TAG, "getThumbnailUrl: " + YOUTUBE_THUMBNAIL_URL + videoId);
//            return YOUTUBE_THUMBNAIL_URL + videoId;
            return String.format(YOUTUBE_THUMBNAIL_URL,videoId);
        }
        return "";
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", videoId='" + videoId + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
