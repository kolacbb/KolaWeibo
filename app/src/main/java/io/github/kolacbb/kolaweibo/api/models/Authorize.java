package io.github.kolacbb.kolaweibo.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kola on 2016/9/17.
 */
public class Authorize implements Serializable {

    @SerializedName("code")
    private String code;
    @SerializedName("state")
    private String state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
