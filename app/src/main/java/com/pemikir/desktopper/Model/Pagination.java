package com.pemikir.desktopper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pagination {

    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("next")
    @Expose
    private Integer next;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("count")
    @Expose
    private Integer count;

    /**
     * @return The current
     */
    public Integer getCurrent() {
        return current;
    }

    /**
     * @param current The current
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * @return The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    /**
     * @return The next
     */
    public Integer getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(Integer next) {
        this.next = next;
    }

    /**
     * @return The perPage
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     * @param perPage The per_page
     */
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     * @return The pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}