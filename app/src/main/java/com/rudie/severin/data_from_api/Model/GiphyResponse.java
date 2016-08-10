package com.rudie.severin.data_from_api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by erikrudie on 8/9/16.
 */
public class GiphyResponse {

    @SerializedName("data")
    private Giphy giphies;

    public GiphyResponse(Giphy giphies) {
        this.giphies = giphies;
    }

    public Giphy getGiphies() {
        return giphies;
    }
}
