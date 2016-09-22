package io.github.kolacbb.kolaweibo;

import android.app.Application;
import android.preference.PreferenceManager;

import io.github.kolacbb.kolaweibo.util.SpUtil;
import io.github.kolacbb.kolaweibo.util.ToastUtils;

/**
 * Created by zhangd on 2016/9/19.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.getInstance().setContext(this);
        SpUtil.init(PreferenceManager.getDefaultSharedPreferences(this));
    }
}
