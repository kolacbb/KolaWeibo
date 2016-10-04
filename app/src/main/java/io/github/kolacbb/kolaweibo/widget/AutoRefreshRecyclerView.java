package io.github.kolacbb.kolaweibo.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.ui.adapter.BaseSwipeLoadingAdapter;

/**
 * Created by zhangd on 2016/10/3.
 */

public class AutoRefreshRecyclerView extends FrameLayout {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnRefreshListener mRefreshListener;
    private OnLoadingListener mLoadingListener;

    private boolean mIsAutoRefresh = true;
    private boolean mIsLoading = false;
    private boolean mIsLoadingComplete = false;

    public AutoRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public AutoRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.auto_refresh_recycler_view, this, true);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mIsAutoRefresh && mRefreshListener != null) {
            mRefreshLayout.setRefreshing(true);
            mRefreshListener.onRefresh();
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        if (adapter instanceof BaseSwipeLoadingAdapter) {
            final BaseSwipeLoadingAdapter loadingAdapter = (BaseSwipeLoadingAdapter) adapter;
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int position = RecyclerView.NO_POSITION;
                    if (mLayoutManager instanceof LinearLayoutManager) {
                        position = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    }

                    if (recyclerView.getAdapter().getItemCount() - 1 > 0
                            && loadingAdapter.getItemCount() - 2 <= position
                            && mLoadingListener != null
                            && !mIsLoading
                            && !mIsLoadingComplete) {
                        loadingAdapter.setLoading(true);
                        mIsLoading = true;
                        mLoadingListener.onLoading();
                    }
                }
            });
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        mIsAutoRefresh = autoRefresh;
    }

    public void setRefreshing(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

    public void setLoading(boolean loading) {
        BaseSwipeLoadingAdapter adapter = (BaseSwipeLoadingAdapter) mRecyclerView.getAdapter();
        adapter.setLoading(loading);
        mIsLoading = loading;
    }

    public void setLoadingComplete(boolean enable) {
        mIsLoadingComplete = enable;
    }

    /**
     * 设置下拉刷新监听
     *
     * @param listener 下拉刷新监听
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    /**
     * 设置上拉加载监听
     *
     * @param listener 上拉加载监听
     */
    public void setOnLoadingListener(OnLoadingListener listener) {
        mLoadingListener = listener;
    }


    // 下拉刷新监听
    public interface OnRefreshListener {
        void onRefresh();
    }

    // 上拉加载监听
    public interface OnLoadingListener {
        void onLoading();
    }

}
