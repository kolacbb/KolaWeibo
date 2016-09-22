package io.github.kolacbb.kolaweibo.util;

import android.content.SharedPreferences;

/**
 * Created by zhangd on 2016/9/19.
 */
public class SpUtil {

    private static SharedPreferences mSp;

    public static void init(SharedPreferences sp) {
        mSp = sp;
    }

    public static void saveOrUpdate(String key, String json) {
        mSp.edit().putString(key, json).apply();
    }

    public static String find(String key) {
        return mSp.getString(key, null);
    }

    public static void delete(String key) {
        mSp.edit().remove(key).apply();
    }

    public static void clearAll() {
        mSp.edit().clear().apply();

    }
}