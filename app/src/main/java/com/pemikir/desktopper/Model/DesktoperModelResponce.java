package com.pemikir.desktopper.Model;

/**
 * Created by iconflux-android on 10/31/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class DesktoperModelResponce {

    @SerializedName("response")
    @Expose
    public List<Response> response = new ArrayList<Response>();
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("pagination")
    @Expose
    public Pagination pagination;

    /**
     * @return The response
     */
    public List<Response> getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(List<Response> response) {
        this.response = response;
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

    /**
     * @return The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * @param pagination The pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}

