package io.github.kolacbb.kolaweibo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

import io.github.kolacbb.kolaweibo.api.RetrofitFactory;
import io.github.kolacbb.kolaweibo.api.WBService;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.api.models.WBBaseBean;
import io.github.kolacbb.kolaweibo.ui.WBAuthActivity;
import io.github.kolacbb.kolaweibo.ui.adapter.TimeLineAdapter;
import io.github.kolacbb.kolaweibo.util.AccessTokenKeeper;
import io.github.kolacbb.kolaweibo.util.ToastUtils;
import io.github.kolacbb.kolaweibo.widget.DividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    TimeLineAdapter mTimeLineAdapter;
    List<FriendTimeLine> mTimeLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(getApplicationContext());
//        if (TextUtils.isEmpty(token.getToken())) {
//            //finish();
//            System.out.println(token.getToken() + "   asds");
//            startActivity(new Intent(this, WBAuthActivity.class));
//        } else {
            //ToastUtils.show(token.getToken());
            //Log.e("token", token.getToken());
            System.out.println(token.getToken() + "   asds");
            WBService api = RetrofitFactory.getWBRetrofit().create(WBService.class);

            Call<List<FriendTimeLine>> call = api.getFriendsTimeLine("2.00cAn7YFtRNyqC5cca9867310tLMa8", null, null, null, null, null, null, null);
            call.enqueue(new Callback<List<FriendTimeLine>>() {
                @Override
                public void onResponse(Call<List<FriendTimeLine>> call, Response<List<FriendTimeLine>> response) {

                    Gson gson = new Gson();
                    ToastUtils.show("chenggong la ");
                    Log.e("lalal", gson.toJson(response.body()));

                    List<FriendTimeLine> timeLines = gson.fromJson(gson.toJson(response.body()), new TypeToken<List<FriendTimeLine>>() {
                    }.getType());


                    mTimeLineAdapter.setData(timeLines);
                    mTimeLineAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<FriendTimeLine>> call, Throwable throwable) {
                    ToastUtils.show("sibai" + throwable.getMessage());
                }
            });
      //  }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rec_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mTimeLines = new ArrayList<>();
        mTimeLineAdapter = new TimeLineAdapter(mTimeLines);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }
}
