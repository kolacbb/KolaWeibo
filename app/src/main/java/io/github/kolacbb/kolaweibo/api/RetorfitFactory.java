package io.github.kolacbb.kolaweibo.api;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kola on 2016/9/17.
 */
public class RetorfitFactory {

    private static Retrofit mAuthRetrofit;
    private static Retrofit mWBRetrofit;
    private static Converter.Factory mConverterFactory;

    static {
        mConverterFactory = GsonConverterFactory.create();

        mAuthRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/oauth2")
                .addConverterFactory(mConverterFactory)
                .build();

        mWBRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/2")
                .addConverterFactory(mConverterFactory)
                .build();

    }

    public static Retrofit getAuthRetrofit() {
        return mAuthRetrofit;
    }

    public static Retrofit getWBRetrofit() {
        return mWBRetrofit;
    }
}
