package com.pemikir.desktopper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iconflux-android on 10/31/2015.
 */
public class Image {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;
    @SerializedName("preview")
    @Expose
    private Preview preview;

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The thumb
     */
    public Thumb getThumb() {
        return thumb;
    }

    /**
     * @param thumb The thumb
     */
    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    /**
     * @return The preview
     */
    public Preview getPreview() {
        return preview;
    }

    /**
     * @param preview The preview
     */
    public void setPreview(Preview preview) {
        this.preview = preview;
    }


}
