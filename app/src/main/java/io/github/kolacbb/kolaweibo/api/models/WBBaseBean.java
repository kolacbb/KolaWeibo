package io.github.kolacbb.kolaweibo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangd on 2016/9/19.
 */
public class WBBaseBean<T> implements Serializable {
    @SerializedName("statuses")
    @Expose
    private T statuses;
    @SerializedName("advertises")
    @Expose
    private List<Object> advertises = new ArrayList<Object>();
    @SerializedName("ad")
    @Expose
    private List<Object> ad = new ArrayList<Object>();
    @SerializedName("hasvisible")
    @Expose
    private boolean hasvisible;
    @SerializedName("previous_cursor")
    @Expose
    private long previousCursor;
    @SerializedName("next_cursor")
    @Expose
    private long nextCursor;
    @SerializedName("total_number")
    @Expose
    private int totalNumber;
    @SerializedName("interval")
    @Expose
    private int interval;
    @SerializedName("uve_blank")
    @Expose
    private int uveBlank;
    @SerializedName("since_id")
    @Expose
    private long sinceId;
    @SerializedName("max_id")
    @Expose
    private long maxId;
    @SerializedName("has_unread")
    @Expose
    private int hasUnread;

    public T getStatuses() {
        return statuses;
    }

    public void setStatuses(T statuses) {
        this.statuses = statuses;
    }

    public List<Object> getAdvertises() {
        return advertises;
    }

    public void setAdvertises(List<Object> advertises) {
        this.advertises = advertises;
    }

    public List<Object> getAd() {
        return ad;
    }

    public void setAd(List<Object> ad) {
        this.ad = ad;
    }

    public boolean isHasvisible() {
        return hasvisible;
    }

    public void setHasvisible(boolean hasvisible) {
        this.hasvisible = hasvisible;
    }

    public long getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(long previousCursor) {
        this.previousCursor = previousCursor;
    }

    public long getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getUveBlank() {
        return uveBlank;
    }

    public void setUveBlank(int uveBlank) {
        this.uveBlank = uveBlank;
    }

    public long getSinceId() {
        return sinceId;
    }

    public void setSinceId(long sinceId) {
        this.sinceId = sinceId;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getHasUnread() {
        return hasUnread;
    }

    public void setHasUnread(int hasUnread) {
        this.hasUnread = hasUnread;
    }
}
