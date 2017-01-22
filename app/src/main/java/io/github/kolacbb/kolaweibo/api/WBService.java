package io.github.kolacbb.kolaweibo.api;

import java.util.List;

import io.github.kolacbb.kolaweibo.api.models.AccessToken;
import io.github.kolacbb.kolaweibo.api.models.Authorize;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.api.models.WBBaseBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kola on 2016/9/17.
 */
public interface WBService {

    @GET("authorize")
    Call<Authorize> getAuthCode(@Query("q") String words);

    @GET("access_token")
    Call<AccessToken> getAccessToken(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("grant_type") String grantType,
            @Query("code") String code,
            @Query("redirect_uri") String redirectUri);

    @GET("statuses/friends_timeline.json")
    Call<List<FriendTimeLine>> getFriendsTimeLine(
            @Query("access_token") String accessToken, // 采用OAuth授权方式为必填参数，OAuth授权后获得。
            @Query("since_id") Long sinceId, // 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
            @Query("max_id") Long maxId, // 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
            @Query("count") Integer count, // 单页返回的记录条数，最大不超过100，默认为20。
            @Query("page") Integer page, // 返回结果的页码，默认为1。
            @Query("base_app") Integer baseApp, // 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
            @Query("feature") Integer feature, // 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
            @Query("trim_user") Integer trimUser // 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
    );
}
