package io.github.kolacbb.kolaweibo.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kolab on 2016/10/22.
 */

public class Pic_urls {
    @SerializedName("thumbnail_pic")
    private String thumbnail_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }
}
