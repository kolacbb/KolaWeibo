package io.github.kolacbb.kolaweibo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangd on 2016/9/19.
 */
public class FriendTimeLine implements Serializable {

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("idstr")
    @Expose
    private String idstr;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("source_allowclick")
    @Expose
    private long sourceAllowclick;
    @SerializedName("source_type")
    @Expose
    private long sourceType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("favorited")
    @Expose
    private boolean favorited;
    @SerializedName("truncated")
    @Expose
    private boolean truncated;
    @SerializedName("in_reply_to_status_id")
    @Expose
    private String inReplyToStatusId;
    @SerializedName("in_reply_to_user_id")
    @Expose
    private String inReplyToUserId;
    @SerializedName("in_reply_to_screen_name")
    @Expose
    private String inReplyToScreenName;
    @SerializedName("pic_urls")
    @Expose
    private List<Pic_urls> picUrls = new ArrayList<Pic_urls>();
    @SerializedName("geo")
    @Expose
    private Object geo;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("retweeted_status")
    @Expose
    private RetweetedStatus retweetedStatus;
    @SerializedName("reposts_count")
    @Expose
    private long repostsCount;
    @SerializedName("comments_count")
    @Expose
    private long commentsCount;
    @SerializedName("attitudes_count")
    @Expose
    private long attitudesCount;
    @SerializedName("isLongText")
    @Expose
    private boolean isLongText;
    @SerializedName("mlevel")
    @Expose
    private long mlevel;
    @SerializedName("visible")
    @Expose
    private Visible visible;
    @SerializedName("biz_feature")
    @Expose
    private long bizFeature;
    @SerializedName("hasActionTypeCard")
    @Expose
    private long hasActionTypeCard;
    @SerializedName("darwin_tags")
    @Expose
    private List<Object> darwinTags = new ArrayList<Object>();
    @SerializedName("hot_weibo_tags")
    @Expose
    private List<Object> hotWeiboTags = new ArrayList<Object>();
    @SerializedName("text_tag_tips")
    @Expose
    private List<Object> textTagTips = new ArrayList<Object>();
    @SerializedName("rid")
    @Expose
    private String rid;
    @SerializedName("userType")
    @Expose
    private long userType;
    @SerializedName("positive_recom_flag")
    @Expose
    private long positiveRecomFlag;
    @SerializedName("gif_ids")
    @Expose
    private String gifIds;
    @SerializedName("is_show_bulletin")
    @Expose
    private long isShowBulletin;

    /**
     *
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     *     The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The mid
     */
    public String getMid() {
        return mid;
    }

    /**
     *
     * @param mid
     *     The mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     *
     * @return
     *     The idstr
     */
    public String getIdstr() {
        return idstr;
    }

    /**
     *
     * @param idstr
     *     The idstr
     */
    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    /**
     *
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     *     The sourceAllowclick
     */
    public long getSourceAllowclick() {
        return sourceAllowclick;
    }

    /**
     *
     * @param sourceAllowclick
     *     The source_allowclick
     */
    public void setSourceAllowclick(long sourceAllowclick) {
        this.sourceAllowclick = sourceAllowclick;
    }

    /**
     *
     * @return
     *     The sourceType
     */
    public long getSourceType() {
        return sourceType;
    }

    /**
     *
     * @param sourceType
     *     The source_type
     */
    public void setSourceType(long sourceType) {
        this.sourceType = sourceType;
    }

    /**
     *
     * @return
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     *     The favorited
     */
    public boolean getFavorited() {
        return favorited;
    }

    /**
     *
     * @param favorited
     *     The favorited
     */
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    /**
     *
     * @return
     *     The truncated
     */
    public boolean getTruncated() {
        return truncated;
    }

    /**
     *
     * @param truncated
     *     The truncated
     */
    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    /**
     *
     * @return
     *     The inReplyToStatusId
     */
    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     *
     * @param inReplyToStatusId
     *     The in_reply_to_status_id
     */
    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     *
     * @return
     *     The inReplyToUserId
     */
    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     *
     * @param inReplyToUserId
     *     The in_reply_to_user_id
     */
    public void setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     *
     * @return
     *     The inReplyToScreenName
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     *
     * @param inReplyToScreenName
     *     The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     *
     * @return
     *     The picUrls
     */
    public List<Pic_urls> getPicUrls() {
        return picUrls;
    }

    /**
     *
     * @param picUrls
     *     The pic_urls
     */
    public void setPicUrls(List<Pic_urls> picUrls) {
        this.picUrls = picUrls;
    }

    /**
     *
     * @return
     *     The geo
     */
    public Object getGeo() {
        return geo;
    }

    /**
     *
     * @param geo
     *     The geo
     */
    public void setGeo(Object geo) {
        this.geo = geo;
    }

    /**
     *
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     *     The retweetedStatus
     */
    public RetweetedStatus getRetweetedStatus() {
        return retweetedStatus;
    }

    /**
     *
     * @param retweetedStatus
     *     The retweeted_status
     */
    public void setRetweetedStatus(RetweetedStatus retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    /**
     *
     * @return
     *     The repostsCount
     */
    public long getRepostsCount() {
        return repostsCount;
    }

    /**
     *
     * @param repostsCount
     *     The reposts_count
     */
    public void setRepostsCount(long repostsCount) {
        this.repostsCount = repostsCount;
    }

    /**
     *
     * @return
     *     The commentsCount
     */
    public long getCommentsCount() {
        return commentsCount;
    }

    /**
     *
     * @param commentsCount
     *     The comments_count
     */
    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     *
     * @return
     *     The attitudesCount
     */
    public long getAttitudesCount() {
        return attitudesCount;
    }

    /**
     *
     * @param attitudesCount
     *     The attitudes_count
     */
    public void setAttitudesCount(long attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    /**
     *
     * @return
     *     The isLongText
     */
    public boolean getIsLongText() {
        return isLongText;
    }

    /**
     *
     * @param isLongText
     *     The isLongText
     */
    public void setIsLongText(boolean isLongText) {
        this.isLongText = isLongText;
    }

    /**
     *
     * @return
     *     The mlevel
     */
    public long getMlevel() {
        return mlevel;
    }

    /**
     *
     * @param mlevel
     *     The mlevel
     */
    public void setMlevel(long mlevel) {
        this.mlevel = mlevel;
    }

    /**
     *
     * @return
     *     The visible
     */
    public Visible getVisible() {
        return visible;
    }

    /**
     *
     * @param visible
     *     The visible
     */
    public void setVisible(Visible visible) {
        this.visible = visible;
    }

    /**
     *
     * @return
     *     The bizFeature
     */
    public long getBizFeature() {
        return bizFeature;
    }

    /**
     *
     * @param bizFeature
     *     The biz_feature
     */
    public void setBizFeature(long bizFeature) {
        this.bizFeature = bizFeature;
    }

    /**
     *
     * @return
     *     The hasActionTypeCard
     */
    public long getHasActionTypeCard() {
        return hasActionTypeCard;
    }

    /**
     *
     * @param hasActionTypeCard
     *     The hasActionTypeCard
     */
    public void setHasActionTypeCard(long hasActionTypeCard) {
        this.hasActionTypeCard = hasActionTypeCard;
    }

    /**
     *
     * @return
     *     The darwinTags
     */
    public List<Object> getDarwinTags() {
        return darwinTags;
    }

    /**
     *
     * @param darwinTags
     *     The darwin_tags
     */
    public void setDarwinTags(List<Object> darwinTags) {
        this.darwinTags = darwinTags;
    }

    /**
     *
     * @return
     *     The hotWeiboTags
     */
    public List<Object> getHotWeiboTags() {
        return hotWeiboTags;
    }

    /**
     *
     * @param hotWeiboTags
     *     The hot_weibo_tags
     */
    public void setHotWeiboTags(List<Object> hotWeiboTags) {
        this.hotWeiboTags = hotWeiboTags;
    }

    /**
     *
     * @return
     *     The textTagTips
     */
    public List<Object> getTextTagTips() {
        return textTagTips;
    }

    /**
     *
     * @param textTagTips
     *     The text_tag_tips
     */
    public void setTextTagTips(List<Object> textTagTips) {
        this.textTagTips = textTagTips;
    }

    /**
     *
     * @return
     *     The rid
     */
    public String getRid() {
        return rid;
    }

    /**
     *
     * @param rid
     *     The rid
     */
    public void setRid(String rid) {
        this.rid = rid;
    }

    /**
     *
     * @return
     *     The userType
     */
    public long getUserType() {
        return userType;
    }

    /**
     *
     * @param userType
     *     The userType
     */
    public void setUserType(long userType) {
        this.userType = userType;
    }

    /**
     *
     * @return
     *     The positiveRecomFlag
     */
    public long getPositiveRecomFlag() {
        return positiveRecomFlag;
    }

    /**
     *
     * @param positiveRecomFlag
     *     The positive_recom_flag
     */
    public void setPositiveRecomFlag(long positiveRecomFlag) {
        this.positiveRecomFlag = positiveRecomFlag;
    }

    /**
     *
     * @return
     *     The gifIds
     */
    public String getGifIds() {
        return gifIds;
    }

    /**
     *
     * @param gifIds
     *     The gif_ids
     */
    public void setGifIds(String gifIds) {
        this.gifIds = gifIds;
    }

    /**
     *
     * @return
     *     The isShowBulletin
     */
    public long getIsShowBulletin() {
        return isShowBulletin;
    }

    /**
     *
     * @param isShowBulletin
     *     The is_show_bulletin
     */
    public void setIsShowBulletin(long isShowBulletin) {
        this.isShowBulletin = isShowBulletin;
    }

}