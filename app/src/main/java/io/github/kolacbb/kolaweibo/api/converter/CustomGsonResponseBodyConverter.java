package io.github.kolacbb.kolaweibo.api.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.List;

import io.github.kolacbb.kolaweibo.api.models.WBBaseBean;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhangd on 2016/9/20.
 */
final class  CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<WBBaseBean<T>> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<WBBaseBean<T>> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        WBBaseBean<T> base = this.gson.fromJson(response, WBBaseBean.class);
        return base.getStatuses();
    }
}
