package com.rudie.severin.data_from_api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by erikrudie on 8/9/16.
 */
public class GiphyImages {

    @SerializedName("original_still")
    private OriginalStill originalStill;
    @SerializedName("original")
    private OriginalGif originalGif;

    public GiphyImages(OriginalStill originalStill, OriginalGif originalGif) {
        this.originalStill = originalStill;
        this.originalGif = originalGif;
    }

    public OriginalStill getOriginalStill() {
        return originalStill;
    }

    public OriginalGif getOriginalGif() {
        return originalGif;
    }
}
