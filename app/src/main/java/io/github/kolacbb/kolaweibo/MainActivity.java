package io.github.kolacbb.kolaweibo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;

import io.github.kolacbb.kolaweibo.api.RetrofitFactory;
import io.github.kolacbb.kolaweibo.api.WBService;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.api.models.WBBaseBean;
import io.github.kolacbb.kolaweibo.ui.WBAuthActivity;
import io.github.kolacbb.kolaweibo.util.AccessTokenKeeper;
import io.github.kolacbb.kolaweibo.util.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(getApplicationContext());
        if (TextUtils.isEmpty(token.getToken())) {
            //finish();
            startActivity(new Intent(this, WBAuthActivity.class));
        } else {
            //ToastUtils.show(token.getToken());
            //Log.e("token", token.getToken());
            WBService api = RetrofitFactory.getWBRetrofit().create(WBService.class);

            Call<List<FriendTimeLine>> call = api.getFriendsTimeLine(token.getToken());
            call.enqueue(new Callback<List<FriendTimeLine>>() {
                @Override
                public void onResponse(Call<List<FriendTimeLine>> call, Response<List<FriendTimeLine>> response) {

                    Gson gson = new Gson();
                    ToastUtils.show("chenggong la ");
                    Log.e("lalal", gson.toJson(response));
                }

                @Override
                public void onFailure(Call<List<FriendTimeLine>> call, Throwable throwable) {
                    ToastUtils.show("sibai" + throwable.getMessage());
                }
            });
        }
    }
}
