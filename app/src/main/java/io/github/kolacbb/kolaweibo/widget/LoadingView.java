package io.github.kolacbb.kolaweibo.widget;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import io.github.kolacbb.kolaweibo.R;

/**
 * Created by zhangli on 2016/10/3.
 */

public class LoadingView extends FrameLayout {
    private FrameLayout mRootView;
    public ContentLoadingProgressBar mProgressBar;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_loading_recycler, this, true);
        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.loading_bar);
        mRootView = (FrameLayout) findViewById(R.id.root_frame);
        mRootView.setVisibility(GONE);
    }

    public void setLoading(boolean loading) {
        if (loading) {
            mRootView.setVisibility(VISIBLE);
        } else {
            mRootView.setVisibility(GONE);
        }
    }
}
