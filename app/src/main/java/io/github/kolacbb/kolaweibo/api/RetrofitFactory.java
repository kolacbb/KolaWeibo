package io.github.kolacbb.kolaweibo.api;

import java.io.IOException;

import io.github.kolacbb.kolaweibo.api.converter.CustomGsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Kola on 2016/9/17.
 */
public class RetrofitFactory {

    private static Retrofit mAuthRetrofit;
    private static Retrofit mWBRetrofit;
    private static Converter.Factory mConverterFactory;

    static {
        mConverterFactory = CustomGsonConverterFactory.create();

        mAuthRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/oauth2/")
                .addConverterFactory(mConverterFactory)
                .build();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();


        /**
         * 若要添加其他converter 须将要添加的Converter放在Json converter 之前
         * */
        mWBRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/2/")
                .addConverterFactory(mConverterFactory)
                .client(okHttpClient)
                .build();

    }

    public static Retrofit getAuthRetrofit() {
        return mAuthRetrofit;
    }

    public static Retrofit getWBRetrofit() {
        return mWBRetrofit;
    }
}
