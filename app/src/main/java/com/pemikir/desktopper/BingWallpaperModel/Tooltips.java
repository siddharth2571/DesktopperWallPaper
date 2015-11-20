package com.pemikir.desktopper.BingWallpaperModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iconflux-android on 11/5/2015.
 */
public class Tooltips {

    @SerializedName("loading")
    @Expose
    private String loading;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("walle")
    @Expose
    private String walle;
    @SerializedName("walls")
    @Expose
    private String walls;

    /**
     * @return The loading
     */
    public String getLoading() {
        return loading;
    }

    /**
     * @param loading The loading
     */
    public void setLoading(String loading) {
        this.loading = loading;
    }

    /**
     * @return The previous
     */
    public String getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    /**
     * @return The next
     */
    public String getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * @return The walle
     */
    public String getWalle() {
        return walle;
    }

    /**
     * @param walle The walle
     */
    public void setWalle(String walle) {
        this.walle = walle;
    }

    /**
     * @return The walls
     */
    public String getWalls() {
        return walls;
    }

    /**
     * @param walls The walls
     */
    public void setWalls(String walls) {
        this.walls = walls;
    }

}
