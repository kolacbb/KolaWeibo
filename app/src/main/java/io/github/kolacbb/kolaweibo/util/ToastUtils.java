package io.github.kolacbb.kolaweibo.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by zhangd on 2016/9/13.
 */
public class ToastUtils {

    private static ToastUtils mInstance;
    private static Toast mToast;
    private static Context mCtx;

    private static final int LENGTH_LIMIT = 8;

    public static ToastUtils getInstance() {
        synchronized (ToastUtils.class) {
            if (mInstance == null) {
                mInstance = new ToastUtils();
            }
        }
        return mInstance;
    }

    public void setContext(Context context) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("ToastUtils::setContext must be in UI thread");
        }
        mCtx = context;
    }

    public static void show(final CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        if (!TextUtils.isEmpty(text) && text.length() > LENGTH_LIMIT) {
            duration = Toast.LENGTH_LONG;
        }
        Toast.makeText(mCtx, text, duration).show();
    }
}
