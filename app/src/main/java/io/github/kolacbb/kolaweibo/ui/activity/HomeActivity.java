package io.github.kolacbb.kolaweibo.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.Constants;
import io.github.kolacbb.kolaweibo.ui.fragment.BaseFragment;
import io.github.kolacbb.kolaweibo.ui.fragment.DiscoverFragment;
import io.github.kolacbb.kolaweibo.ui.fragment.FeedFragment;
import io.github.kolacbb.kolaweibo.ui.fragment.MessageFragment;
import io.github.kolacbb.kolaweibo.ui.fragment.MoreFragment;
import io.github.kolacbb.kolaweibo.ui.fragment.NotificationFragment;
import io.github.kolacbb.kolaweibo.util.AccessTokenKeeper;
import io.github.kolacbb.kolaweibo.util.ToastUtils;

/**
 * Created by zhangd on 2016/9/20.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    Oauth2AccessToken mToken;

    private View mFeedButton;
    private View mDiscoverButton;
    private View mNoticationButton;
    private View mMessageButton;
    private View mMoreButton;

    private FeedFragment mFeedFragment;
    private DiscoverFragment mDiscoverFragment;
    private NotificationFragment mNotificationFragment;
    private MessageFragment mMessageFragment;
    private MoreFragment mMoreFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initView();

        mToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        if (mToken.isSessionValid()) {
            loadData(savedInstanceState);
        } else {
            AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            SsoHandler mSsoHandler = new SsoHandler(this, mAuthInfo);
            mSsoHandler.authorize(new AuthListener());
        }
    }

    private void initView() {
        // init view
        mFeedButton = findViewById(R.id.tab_bar_feed);
        mDiscoverButton = findViewById(R.id.tab_bar_discover);
        mNoticationButton = findViewById(R.id.tab_bar_notification);
        mMessageButton = findViewById(R.id.tab_bar_message);
        mMoreButton = findViewById(R.id.tab_bar_more);

        mFeedButton.setOnClickListener(this);
        mDiscoverButton.setOnClickListener(this);
        mNoticationButton.setOnClickListener(this);
        mMessageButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);


    }

    private void loadData(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            return;
        }

//        mFeedFragment = new FeedFragment();
//        mDiscoverFragment = new DiscoverFragment();
//        mNotificationFragment = new NotificationFragment();
//        mMessageFragment = new MessageFragment();
//        mMoreFragment = new MoreFragment();


        mFeedFragment = new FeedFragment();

        mFeedFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mFeedFragment, FeedFragment.TAG)
                //.addToBackStack(null)
                .commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_bar_feed:
                showFragment(FeedFragment.TAG);
                break;
            case R.id.tab_bar_discover:
                showFragment(DiscoverFragment.TAG);
                break;
            case R.id.tab_bar_notification:

                break;
            case R.id.tab_bar_message:

                break;
            case R.id.tab_bar_more:

                break;
        }
    }

    public void showFragment(String tag) {
        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (baseFragment != null && baseFragment.isVisible()) {
            return;
        }

        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        // 隐藏其他Fragment
        if (mFeedFragment != null && mFeedFragment.isVisible()) {
            transaction.hide(mFeedFragment);
        }

        if (mDiscoverFragment != null && mDiscoverFragment.isVisible()) {
            transaction.hide(mDiscoverFragment);
        }

        if (tag.equals(FeedFragment.TAG)) {
            if (baseFragment == null) {
                mFeedFragment = new FeedFragment();
                baseFragment = mFeedFragment;
            }

        } else if (tag.equals(DiscoverFragment.TAG)) {
            if (baseFragment == null) {
                mDiscoverFragment = new DiscoverFragment();
                baseFragment = mDiscoverFragment;
            }
        }

        // 添加Fragment 到事物
        if (baseFragment.isAdded()) {
            transaction.show(baseFragment);
        } else {
            transaction.add(R.id.fragment_container, baseFragment, tag).commit();
        }


    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String phoneNum = mToken.getPhoneNum();
            if (mToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), mToken);
                ToastUtils.show("授权成功");
                loadData(null);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                ToastUtils.show(message);
            }
        }

        @Override
        public void onCancel() {
            ToastUtils.show("取消授权");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtils.show("Auth exception : " + e.getMessage());
        }
    }
}
