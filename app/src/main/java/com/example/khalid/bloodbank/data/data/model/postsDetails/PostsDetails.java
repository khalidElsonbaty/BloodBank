
package com.example.khalid.bloodbank.data.data.model.postsDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsDetails {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PostDetailsData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PostDetailsData getData() {
        return data;
    }

    public void setData(PostDetailsData data) {
        this.data = data;
    }

}
