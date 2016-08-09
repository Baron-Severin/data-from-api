package com.rudie.severin.data_from_api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by erikrudie on 8/9/16.
 */
public class Giphy {

    @SerializedName("url")
    private String url;
    @SerializedName("images")
    private GiphyImages giphyImages;

    public Giphy(String url, GiphyImages giphyImages) {
        this.url = url;
        this.giphyImages = giphyImages;
    }

    public String getUrl() {
        return url;
    }

    public GiphyImages getGiphyImages() {
        return giphyImages;
    }
}
