package io.github.kolacbb.kolaweibo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import io.github.kolacbb.kolaweibo.widget.BottomNavigationView;

/**
 * Created by zhangd on 2016/9/20.
 */
public class HomeActivity extends AppCompatActivity {

    private String TAG = HomeActivity.class.getSimpleName();

    Oauth2AccessToken mToken;

    private BottomNavigationView mBottomNavigation;

    private FeedFragment mFeedFragment;
    private DiscoverFragment mDiscoverFragment;
    private NotificationFragment mNotificationFragment;
    private MessageFragment mMessageFragment;
    private MoreFragment mMoreFragment;

    private BaseFragment mOldFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initView();
        mToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        if (mToken.isSessionValid()) {
            Log.e(TAG, "token: " + mToken.getToken());
            loadData(savedInstanceState);
        } else {
            AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            SsoHandler mSsoHandler = new SsoHandler(this, mAuthInfo);
            mSsoHandler.authorize(new AuthListener());
        }
    }

    private void initView() {

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        mBottomNavigation.setOnButtonClickListener(new BottomNavigationView.OnButtonClickListener() {
            @Override
            public void onFeedClicked(View v) {
                showFragment(FeedFragment.TAG);
            }

            @Override
            public void onDiscoverClicked(View v) {
                showFragment(DiscoverFragment.TAG);
            }

            @Override
            public void onNotificationClicked(View v) {
                showFragment(NotificationFragment.TAG);
            }

            @Override
            public void onMessageClicked(View v) {
                showFragment(MessageFragment.TAG);
            }

            @Override
            public void onMoreClicked(View v) {
                showFragment(MoreFragment.TAG);
            }
        });
    }

    private void loadData(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            return;
        }
        showFragment(FeedFragment.TAG);
    }

    public void showFragment(String tag) {
        // 从事务中获取指定tag的fragment
        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);

        // 若是该fragment已经为当前页面，则返回
        if (baseFragment != null && baseFragment.isVisible()) {
            return;
        }

        // 开启事务
        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        // 隐藏其他Fragment
        if (mOldFragment != null && mOldFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(mOldFragment).commit();
            //transaction.hide(mFeedFragment);
            Log.e(TAG, "showFragment: old fragment is hide");
        }

        if (tag.equals(FeedFragment.TAG)) {
            if (baseFragment == null) {
                baseFragment = new FeedFragment();
                Log.e(TAG, "showFragment: new feed fragment");
            }
        } else if (tag.equals(DiscoverFragment.TAG)) {
            if (baseFragment == null) {
                baseFragment = new DiscoverFragment();
                Log.e(TAG, "showFragment: new discover fragment");
            }
        } else if (tag.equals(NotificationFragment.TAG)) {
            if (baseFragment == null) {
                baseFragment = new NotificationFragment();
                Log.e(TAG, "showFragment: new notification fragment");
            }
        } else if (tag.equals(MessageFragment.TAG)) {
            if (baseFragment == null) {
                baseFragment = new MessageFragment();
                Log.e(TAG, "showFragment: new message fragment");
            }
        } else if (tag.equals(MoreFragment.TAG)) {
            if (baseFragment == null) {
                baseFragment = new MoreFragment();
                Log.e(TAG, "showFragment: new more fragment");
            }
        }

        // 添加Fragment 到事物
        if (baseFragment == null) {
            return;
        }

        if (baseFragment.isAdded()) {
            transaction.show(baseFragment).commit();
            Log.e(TAG, "showFragment: base fragment is show");
        } else {
            transaction.add(R.id.fragment_container, baseFragment, tag).commitAllowingStateLoss();
            Log.e(TAG, "showFragment: base fragment is added");
        }
        mOldFragment = baseFragment;
    }

    public void showBottomNavigation(boolean show) {
        if (show) {
            mBottomNavigation.show();
        } else {
            mBottomNavigation.dismiss();
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
