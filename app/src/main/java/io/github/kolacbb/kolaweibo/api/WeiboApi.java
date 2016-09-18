package io.github.kolacbb.kolaweibo.api;

import io.github.kolacbb.kolaweibo.api.models.AccessToken;
import io.github.kolacbb.kolaweibo.api.models.Authorize;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kola on 2016/9/17.
 */
public interface WeiboApi {

    @GET("authorize")
    Call<Authorize> getAuthCode(@Query("q") String words);

    @GET("access_token")
    Call<AccessToken> getAccessToken(@Query("client_id") String clientId,
                                     @Query("client_secret") String clientSecret,
                                     @Query("grant_type") String grantType,
                                     @Query("code") String code,
                                     @Query("redirect_uri") String redirectUri);

}
