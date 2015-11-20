package com.pemikir.desktopper.BingWallpaperModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class BingWallpaperModel {

    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("tooltips")
    @Expose
    private Tooltips tooltips;

    /**
     * @return The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * @return The tooltips
     */
    public Tooltips getTooltips() {
        return tooltips;
    }

    /**
     * @param tooltips The tooltips
     */
    public void setTooltips(Tooltips tooltips) {
        this.tooltips = tooltips;
    }

}

