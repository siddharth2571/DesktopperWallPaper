package com.pemikir.desktopper.BingWallpaperModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iconflux-android on 11/5/2015.
 */
public class Image {

    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("fullstartdate")
    @Expose
    private String fullstartdate;
    @SerializedName("enddate")
    @Expose
    private String enddate;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlbase")
    @Expose
    private String urlbase;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("copyrightlink")
    @Expose
    private String copyrightlink;
    @SerializedName("wp")
    @Expose
    private Boolean wp;
    @SerializedName("hsh")
    @Expose
    private String hsh;
    @SerializedName("drk")
    @Expose
    private Integer drk;
    @SerializedName("top")
    @Expose
    private Integer top;
    @SerializedName("bot")
    @Expose
    private Integer bot;
    @SerializedName("hs")
    @Expose
    private List<H> hs = new ArrayList<H>();
    @SerializedName("msg")
    @Expose
    private List<Object> msg = new ArrayList<Object>();

    /**
     * @return The startdate
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * @param startdate The startdate
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    /**
     * @return The fullstartdate
     */
    public String getFullstartdate() {
        return fullstartdate;
    }

    /**
     * @param fullstartdate The fullstartdate
     */
    public void setFullstartdate(String fullstartdate) {
        this.fullstartdate = fullstartdate;
    }

    /**
     * @return The enddate
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate The enddate
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

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
     * @return The urlbase
     */
    public String getUrlbase() {
        return urlbase;
    }

    /**
     * @param urlbase The urlbase
     */
    public void setUrlbase(String urlbase) {
        this.urlbase = urlbase;
    }

    /**
     * @return The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * @param copyright The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * @return The copyrightlink
     */
    public String getCopyrightlink() {
        return copyrightlink;
    }

    /**
     * @param copyrightlink The copyrightlink
     */
    public void setCopyrightlink(String copyrightlink) {
        this.copyrightlink = copyrightlink;
    }

    /**
     * @return The wp
     */
    public Boolean getWp() {
        return wp;
    }

    /**
     * @param wp The wp
     */
    public void setWp(Boolean wp) {
        this.wp = wp;
    }

    /**
     * @return The hsh
     */
    public String getHsh() {
        return hsh;
    }

    /**
     * @param hsh The hsh
     */
    public void setHsh(String hsh) {
        this.hsh = hsh;
    }

    /**
     * @return The drk
     */
    public Integer getDrk() {
        return drk;
    }

    /**
     * @param drk The drk
     */
    public void setDrk(Integer drk) {
        this.drk = drk;
    }

    /**
     * @return The top
     */
    public Integer getTop() {
        return top;
    }

    /**
     * @param top The top
     */
    public void setTop(Integer top) {
        this.top = top;
    }

    /**
     * @return The bot
     */
    public Integer getBot() {
        return bot;
    }

    /**
     * @param bot The bot
     */
    public void setBot(Integer bot) {
        this.bot = bot;
    }

    /**
     * @return The hs
     */
    public List<H> getHs() {
        return hs;
    }

    /**
     * @param hs The hs
     */
    public void setHs(List<H> hs) {
        this.hs = hs;
    }

    /**
     * @return The msg
     */
    public List<Object> getMsg() {
        return msg;
    }

    /**
     * @param msg The msg
     */
    public void setMsg(List<Object> msg) {
        this.msg = msg;
    }

}
