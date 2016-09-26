package io.github.kolacbb.kolaweibo.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.RetrofitFactory;
import io.github.kolacbb.kolaweibo.api.WBService;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.ui.adapter.TimeLineAdapter;
import io.github.kolacbb.kolaweibo.util.ToastUtils;
import io.github.kolacbb.kolaweibo.widget.DividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecView;
    private TimeLineAdapter mTimeLineAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        init(v);
        mRefreshLayout.setRefreshing(true);
        loadData();
        return v;
    }

    private void init(View v) {
        // init view
        mRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_layout);
        mRecView = (RecyclerView) v.findViewById(R.id.rec_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecView.setLayoutManager(mLayoutManager);
        mRecView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mTimeLineAdapter = new TimeLineAdapter(new ArrayList<FriendTimeLine>());
        mRecView.setAdapter(mTimeLineAdapter);

        // init listener
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        WBService api = RetrofitFactory.getWBRetrofit().create(WBService.class);
        Call<List<FriendTimeLine>> call = api.getFriendsTimeLine("2.00cAn7YFtRNyqC5cca9867310tLMa8", null, null, null, null, null, null, null);
        call.enqueue(new Callback<List<FriendTimeLine>>() {
            @Override
            public void onResponse(Call<List<FriendTimeLine>> call, Response<List<FriendTimeLine>> response) {
                Gson gson = new Gson();

                List<FriendTimeLine> timeLines = gson.fromJson(gson.toJson(response.body()), new TypeToken<List<FriendTimeLine>>() {
                }.getType());

                mTimeLineAdapter.setData(timeLines);
                mTimeLineAdapter.notifyDataSetChanged();
                //mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<FriendTimeLine>> call, Throwable throwable) {
                ToastUtils.show("sibai" + throwable.getMessage());
                //mRefreshLayout.setRefreshing(false);
            }
        });
    }
}
