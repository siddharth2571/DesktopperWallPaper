package com.pemikir.desktopper.BingWallpaperModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iconflux-android on 11/5/2015.
 */ //-----------------------------------com.pemikir.desktopper.BingWallpaperModel.H.java-----------------------------------
public class H {

    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("locx")
    @Expose
    private Integer locx;
    @SerializedName("locy")
    @Expose
    private Integer locy;

    /**
     * @return The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return The locx
     */
    public Integer getLocx() {
        return locx;
    }

    /**
     * @param locx The locx
     */
    public void setLocx(Integer locx) {
        this.locx = locx;
    }

    /**
     * @return The locy
     */
    public Integer getLocy() {
        return locy;
    }

    /**
     * @param locy The locy
     */
    public void setLocy(Integer locy) {
        this.locy = locy;
    }

}
