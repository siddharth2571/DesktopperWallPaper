package com.pemikir.desktopper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bytes")
    @Expose
    private Integer bytes;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("review_state")
    @Expose
    private String reviewState;
    @SerializedName("uploader")
    @Expose
    private String uploader;
    @SerializedName("user_count")
    @Expose
    private Integer userCount;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("palette")
    @Expose
    private List<String> palette = new ArrayList<String>();
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The bytes
     */
    public Integer getBytes() {
        return bytes;
    }

    /**
     * @param bytes The bytes
     */
    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return The reviewState
     */
    public String getReviewState() {
        return reviewState;
    }

    /**
     * @param reviewState The review_state
     */
    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    /**
     * @return The uploader
     */
    public String getUploader() {
        return uploader;
    }

    /**
     * @param uploader The uploader
     */
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    /**
     * @return The userCount
     */
    public Integer getUserCount() {
        return userCount;
    }

    /**
     * @param userCount The user_count
     */
    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * @return The likesCount
     */
    public Integer getLikesCount() {
        return likesCount;
    }

    /**
     * @param likesCount The likes_count
     */
    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    /**
     * @return The palette
     */
    public List<String> getPalette() {
        return palette;
    }

    /**
     * @param palette The palette
     */
    public void setPalette(List<String> palette) {
        this.palette = palette;
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

}