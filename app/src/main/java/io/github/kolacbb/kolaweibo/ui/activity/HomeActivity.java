package io.github.kolacbb.kolaweibo.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.Constants;
import io.github.kolacbb.kolaweibo.ui.fragment.TimeLineFragment;
import io.github.kolacbb.kolaweibo.util.AccessTokenKeeper;
import io.github.kolacbb.kolaweibo.util.ToastUtils;

/**
 * Created by zhangd on 2016/9/20.
 */
public class HomeActivity extends BaseActivity {

    Oauth2AccessToken mToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        if (mToken.isSessionValid()) {
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container) != null) {

                String str = "1*2";
                for (int i = 0; i < str.length(); i++) {
                    char temp = str.charAt(i);

                    int diff = temp - '0';
                    if (diff >= 0 && diff < 10) {
                        // 这个char是数字 diff 就是这个数字
                    } else {
                        // 这个char是字符 char 就是这个字符
                    }
                }


                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }

                // Create a new Fragment to be placed in the activity layout
                TimeLineFragment firstFragment = new TimeLineFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                firstFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();
            }
        } else {
            AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            SsoHandler mSsoHandler = new SsoHandler(this, mAuthInfo);
            mSsoHandler.authorize(new AuthListener());
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
