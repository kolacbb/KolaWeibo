package io.github.kolacbb.kolaweibo.widget;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.kolacbb.kolaweibo.R;

/**
 * 下拉刷新 Refreshing
 * 上拉加载 Loading
 * Created by zhangd on 2016/10/2.
 */
public class KRecyclerView extends FrameLayout {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private OnRefreshListener mRefreshListener;
    private OnLoadingListener mLoadingListener;
    private RecyclerView.LayoutManager mLayoutManager;

    public KRecyclerView(Context context) {
        this(context, null);
    }

    public KRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_recycler, this, true);
        // init view
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.rec_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = RecyclerView.NO_POSITION;
                if (mLayoutManager instanceof LinearLayoutManager) {
                    position = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                }

                if (mAdapter.getItemCount() - 3 < position) {
                    if (mLoadingListener != null) {
                        mLoadingListener.onLoading();
                    }
                }
            }
        });
    }

    /**
     * 为RecyclerView 设置Adapter
     * @param adapter adapter
     * */
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setRefreshEnable(boolean enable) {
        mRefreshLayout.setEnabled(enable);
    }

    public void setLoadingEnable(boolean enable) {
        mAdapter.setLoadingEnable(enable);
    }

    /**
     * 设置下拉刷新显示状态
     * @param refreshing true 为显示刷新控件
     * */
    public void setRefreshing(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

//    /**
//     * 设置上拉加载显示状态
//     * @param loading true 为显示加载控件
//     * */
//    public void setLoading(boolean loading) {
//
//    }

    /**
     * 设置下拉刷新监听
     * @param listener 下拉刷新监听
     * */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    /**
     * 设置上拉加载监听
     * @param listener 上拉加载监听
     * */
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

    public static abstract class Adapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_HEADER = 0x32;
        private static final int TYPE_LOADING = 0x34;

        private View mHeaderView;
        private View mLoadingView;
        private LoadingViewHolder mLoadingHolder;

        private boolean mHasLoading = false;

        private int mViewCount = 0;

        private List<T> mData = new ArrayList<>();


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new SingleViewHolder(mHeaderView);
            } else if (viewType == TYPE_LOADING) {
                mLoadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_loading_recycler, null, false);
                mLoadingHolder = new LoadingViewHolder(mLoadingView);
                return mLoadingHolder;
            }
            return onCreateKViewHolder(parent, viewType);
        }

        public abstract RecyclerView.ViewHolder onCreateKViewHolder(ViewGroup parent, int viewType);

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            System.out.println(position);
            if (mHeaderView != null) {
                onBindKViewHolder(holder, position - 1);
                return;
            }

            if (mLoadingHolder != null) {
                return;
            }

            onBindKViewHolder(holder, position);

        }

        public abstract void onBindKViewHolder(RecyclerView.ViewHolder holder, int position);

        @Override
        public int getItemCount() {
            int count = mData.size();
            if (mHeaderView != null) {
                count += 1;
            }
            if (mLoadingHolder != null) {
                count += 1;
            }
            return count;
        }

        public T getData(int position) {
            return mData.get(position);
        }

        public void setData(List<T> data) {
            mData = data;
        }

        public void addToFront(List<T> data) {
            mViewCount += data.size();
            mData.addAll(0, data);
        }

        public void addToRear(List<T> data) {
            mData.addAll(data);
        }

        public void remove(T t) {
            if (mData.contains(t)) {
                mData.remove(t);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView != null && position == 0) {
                return TYPE_HEADER;
            } else if (mHasLoading && position == mViewCount - 1) {
                return TYPE_LOADING;
            }
            return super.getItemViewType(position);
        }

        public void setHeaderView(View headerView) {
            mHeaderView = headerView;
        }

        public void setLoadingEnable(boolean show) {
            if (show) {
                mHasLoading = true;
            }
        }

        public void setLoading(boolean loading) {
            if (mLoadingHolder != null) {
                if (loading) {
                    mLoadingHolder.getProgressBar().show();
                } else {
                    mLoadingHolder.getProgressBar().hide();
                }
            }
        }

        class SingleViewHolder extends RecyclerView.ViewHolder {

            public SingleViewHolder(View itemView) {
                super(itemView);
            }
        }

        class LoadingViewHolder extends RecyclerView.ViewHolder {
            public ContentLoadingProgressBar progressBar;
            public LoadingViewHolder(View itemView) {
                super(itemView);
                progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.loading_bar);
            }

            public ContentLoadingProgressBar getProgressBar() {
                return progressBar;
            }
        }

    }

}
