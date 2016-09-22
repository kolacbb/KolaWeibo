package io.github.kolacbb.kolaweibo.util;

import android.util.Log;

/**
 * Created by zhangd on 2016/9/20.
 */
public class Logger {

    private String mTag;

    public Logger(String tag) {
        mTag = tag;
    }

    public void info(String str) {
        Log.i(mTag, str);
    }

    public void error(String str) {
        Log.e(mTag, str);
    }


}
