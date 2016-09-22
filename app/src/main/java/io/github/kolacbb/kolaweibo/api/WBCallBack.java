package io.github.kolacbb.kolaweibo.api;

import io.github.kolacbb.kolaweibo.api.models.WBBaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhangd on 2016/9/19.
 */
public class WBCallBack<T> implements Callback<WBBaseBean<T>> {

    @Override
    public void onResponse(Call<WBBaseBean<T>> call, Response<WBBaseBean<T>> response) {

    }

    @Override
    public void onFailure(Call<WBBaseBean<T>> call, Throwable throwable) {

    }
}
