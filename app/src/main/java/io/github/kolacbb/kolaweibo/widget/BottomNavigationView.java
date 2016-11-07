package io.github.kolacbb.kolaweibo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.github.kolacbb.kolaweibo.R;

/**
 * Created by Kola on 2016/10/16.
 */

public class BottomNavigationView extends LinearLayout implements View.OnClickListener, ValueAnimator.AnimatorUpdateListener{

    private ViewGroup.LayoutParams mRootViewParams;
    private View mRootView;

    private final View mFeedButton;
    private final View mDiscoverButton;
    private final View mNotificationButton;
    private final View mMessageButton;
    private final View mMoreButton;

    private OnButtonClickListener mClickListener;

    private ValueAnimator mAnimator;
    private static final int MAX_VIEW_HEIGHT_DP = 48;
    private int mMaxViewHeight;
    private int mCurViewHeight;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_bottom_navigation, this, true);
        mRootView = findViewById(R.id.bottom_navigation);
        mRootViewParams = mRootView.getLayoutParams();

        mMaxViewHeight = (int) (getResources().getDisplayMetrics().density * MAX_VIEW_HEIGHT_DP);
        mCurViewHeight = mMaxViewHeight;

        mFeedButton = findViewById(R.id.tab_bar_feed);
        mDiscoverButton = findViewById(R.id.tab_bar_discover);
        mNotificationButton = findViewById(R.id.tab_bar_notification);
        mMessageButton = findViewById(R.id.tab_bar_message);
        mMoreButton = findViewById(R.id.tab_bar_more);

        mFeedButton.setOnClickListener(this);
        mDiscoverButton.setOnClickListener(this);
        mNotificationButton.setOnClickListener(this);
        mMessageButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);

        mAnimator = new ValueAnimator();
        mAnimator.setDuration(300);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimator.addUpdateListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.removeUpdateListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mClickListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.tab_bar_feed:
                mClickListener.onFeedClicked(this);
                break;
            case R.id.tab_bar_discover:
                mClickListener.onDiscoverClicked(this);
                break;
            case R.id.tab_bar_notification:
                mClickListener.onNotificationClicked(this);
                break;
            case R.id.tab_bar_message:
                mClickListener.onMessageClicked(this);
                break;
            case R.id.tab_bar_more:
                mClickListener.onMoreClicked(this);
                break;
        }
    }

    public void show() {
        if (mCurViewHeight == mMaxViewHeight) {
            return;
        }
        mAnimator.cancel();
        mAnimator.setIntValues(mCurViewHeight, mMaxViewHeight);
        mAnimator.start();
    }

    public void dismiss() {
        if (0 == mCurViewHeight) {
            return;
        }
        mAnimator.cancel();
        mAnimator.setIntValues(mCurViewHeight, 0);
        mAnimator.start();
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        mClickListener = listener;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mRootViewParams.height = (int) animation.getAnimatedValue();
        mCurViewHeight = mRootViewParams.height;
        mRootView.setLayoutParams(mRootViewParams);
    }

    public interface OnButtonClickListener {
        void onFeedClicked(View v);
        void onDiscoverClicked(View v);
        void onNotificationClicked(View v);
        void onMessageClicked(View v);
        void onMoreClicked(View v);
    }
}
