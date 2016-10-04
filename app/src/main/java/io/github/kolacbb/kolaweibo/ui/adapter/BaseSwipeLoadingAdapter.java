package io.github.kolacbb.kolaweibo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import io.github.kolacbb.kolaweibo.widget.LoadingView;

/**
 * Created by zhangli on 2016/10/3.
 */

public abstract class BaseSwipeLoadingAdapter<T> extends RecyclerView.Adapter {
    private static final int TYPE_LOADING = 0x31;

    private LinkedList<T> mData = new LinkedList<>();
    private boolean mHasLoadingView = true;

    private LoadingView mLoadingView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            mLoadingView = new LoadingView(parent.getContext());
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLoadingView.setLayoutParams(params);
            //parent.addView(mLoadingView);
            return new LoadingVH(mLoadingView);
        }
        return onCreateVH(parent, viewType);
    }

    public abstract RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mHasLoadingView && position == getItemCount() - 1) {
            return;
        }
        onBindVH(holder, position);
    }

    public abstract void onBindVH(RecyclerView.ViewHolder holder, int position);

    public void setData(LinkedList<T> date) {
        mData = date;
    }

    public void addToFront(List<T> list) {
        mData.addAll(0, list);
    }

    public void addToRear(List<T> list) {
        mData.addAll(list);
    }

    public void remove(T t) {
        mData.remove(t);
    }

    public T get(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public void setShowLoadingView(boolean show) {
        mHasLoadingView = show;
    }

    public void setLoading(boolean loading) {
        if (mLoadingView != null) {
            mLoadingView.setLoading(loading);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_LOADING;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mHasLoadingView ? mData.size() + 1 : mData.size();
    }

    class LoadingVH extends RecyclerView.ViewHolder {
        //public ContentLoadingProgressBar progressBar;
        public LoadingVH(View itemView) {
            super(itemView);
            //progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.loading_bar);
        }
    }

    interface OnItemClickedListener {
        void onItemClicked(View v, int position);
    }
}
