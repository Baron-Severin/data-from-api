package com.rudie.severin.data_from_api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by erikrudie on 8/9/16.
 */
public class GiphyImages {

    @SerializedName("original_still")
    private OriginalStill originalStill;

    public GiphyImages(OriginalStill originalStill) {
        this.originalStill = originalStill;
    }

    public OriginalStill getOriginalStill() {
        return originalStill;
    }
}
