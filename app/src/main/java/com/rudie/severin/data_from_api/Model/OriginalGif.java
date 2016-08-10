package com.rudie.severin.data_from_api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by erikrudie on 8/9/16.
 */
public class OriginalGif {

    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;

    public OriginalGif(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
