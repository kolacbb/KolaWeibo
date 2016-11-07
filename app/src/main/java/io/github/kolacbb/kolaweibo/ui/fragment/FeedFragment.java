package io.github.kolacbb.kolaweibo.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.RetrofitFactory;
import io.github.kolacbb.kolaweibo.api.WBService;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.ui.activity.HomeActivity;
import io.github.kolacbb.kolaweibo.ui.adapter.FriendTimeLineAdapter;
import io.github.kolacbb.kolaweibo.util.AccessTokenKeeper;
import io.github.kolacbb.kolaweibo.util.ToastUtils;
import io.github.kolacbb.kolaweibo.widget.AutoRefreshRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends BaseFragment
        implements FriendTimeLineAdapter.OnUserClickListener,
        FriendTimeLineAdapter.OnCommentClickListener,
        FriendTimeLineAdapter.OnRepostClickListener,
        FriendTimeLineAdapter.OnLikeClickListener{

    public static final String TAG = FeedFragment.class.getSimpleName();

    private Toolbar mToolBar;
    private AutoRefreshRecyclerView mAutoRecView;
    private FriendTimeLineAdapter mTimeLineAdapter;

    private int mTouchSlop;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mToolBar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        //ActionBar actionBar = activity.getSupportActionBar();

        mAutoRecView = (AutoRefreshRecyclerView) mRootView.findViewById(R.id.auto_refresh_recycler_view);
        mTimeLineAdapter = new FriendTimeLineAdapter();
        mAutoRecView.setAdapter(mTimeLineAdapter);
        //mAutoRecView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mAutoRecView.setItemAnimator(new DefaultItemAnimator());
        mAutoRecView.setOnRefreshListener(new AutoRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mAutoRecView.setOnLoadingListener(new AutoRefreshRecyclerView.OnLoadingListener() {
            @Override
            public void onLoading() {
                Log.e("拉取微博", "new");
                loadPreviousData();
            }
        });
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mAutoRecView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) >= mTouchSlop) {
                    ((HomeActivity) getActivity()).showBottomNavigation(dy < 0);
                }
            }
        });

        mTimeLineAdapter.setCommentClickListener(this);
        mTimeLineAdapter.setLikeClickListener(this);
        mTimeLineAdapter.setRepostClickListener(this);
        mTimeLineAdapter.setUserClickListener(this);
    }

    private void loadData() {
        WBService api = RetrofitFactory.getWBRetrofit().create(WBService.class);
        long sinceId = mTimeLineAdapter.getFirstWBId();
        Call<List<FriendTimeLine>> call = api.getFriendsTimeLine(AccessTokenKeeper.readAccessToken(getActivity().getApplicationContext()).getToken(), sinceId, null, null, null, null, null, null);
        call.enqueue(new Callback<List<FriendTimeLine>>() {
            @Override
            public void onResponse(Call<List<FriendTimeLine>> call, Response<List<FriendTimeLine>> response) {
                Gson gson = new Gson();

                List<FriendTimeLine> timeLines = gson.fromJson(gson.toJson(response.body()), new TypeToken<List<FriendTimeLine>>() {
                }.getType());
                ToastUtils.show(timeLines.size() + "");

                mTimeLineAdapter.addToFront(timeLines);
                mTimeLineAdapter.notifyDataSetChanged();
                mAutoRecView.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<FriendTimeLine>> call, Throwable throwable) {
                ToastUtils.show("sibai" + throwable.getMessage());
                mAutoRecView.setRefreshing(false);
            }
        });
    }

    private void loadPreviousData() {
        WBService api = RetrofitFactory.getWBRetrofit().create(WBService.class);
        long lastId = mTimeLineAdapter.getLastWBId();
        Call<List<FriendTimeLine>> call = api.getFriendsTimeLine(AccessTokenKeeper.readAccessToken(getActivity().getApplicationContext()).getToken(), null, lastId, 10, null, null, null, null);
        call.enqueue(new Callback<List<FriendTimeLine>>() {
            @Override
            public void onResponse(Call<List<FriendTimeLine>> call, Response<List<FriendTimeLine>> response) {
                Gson gson = new Gson();

                List<FriendTimeLine> timeLines = gson.fromJson(gson.toJson(response.body()), new TypeToken<List<FriendTimeLine>>() {
                }.getType());
                ToastUtils.show(timeLines.size() + "");

                if (timeLines.size() < 10) {
                    mAutoRecView.setLoadingComplete(true);
                }

                if (timeLines.size() > 0) {
                    timeLines.remove(0);
                }
                mTimeLineAdapter.addToRear(timeLines);
                mTimeLineAdapter.notifyDataSetChanged();
                mAutoRecView.setLoading(false);

            }

            @Override
            public void onFailure(Call<List<FriendTimeLine>> call, Throwable throwable) {
                ToastUtils.show("sibai" + throwable.getMessage());
                mAutoRecView.setLoading(false);
            }
        });
    }

    @Override
    public void onCommentClick(View view, int position) {
        ToastUtils.show("Comment" + position);
    }

    @Override
    public void onLikeClick(View view, int position) {
        ToastUtils.show("Like" + position);
    }

    @Override
    public void onRepostClick(View view, int position) {
        ToastUtils.show("Repost" + position);
    }

    @Override
    public void onUserClick(View view, int position) {
        ToastUtils.show("User" + position);
    }
}
