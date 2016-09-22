package io.github.kolacbb.kolaweibo.api.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zhangd on 2016/9/20.
 */
public class CustomGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static CustomGsonConverterFactory create() {
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        return new CustomGsonConverterFactory(gson);
    }

    private CustomGsonConverterFactory(Gson gson) {
        if(gson == null) {
            throw new NullPointerException("gson == null");
        } else {
            this.gson = gson;
        }
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter adapter = this.gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter(this.gson, adapter);
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter adapter = this.gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter(this.gson, adapter);
    }
}
