package io.github.kolacbb.kolaweibo.widget;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kola on 2016/10/15.
 */

public class AnimatorCreator {

    public static ValueAnimator getExpandAnimator(final View view) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        ValueAnimator animator = new ValueAnimator();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.height = (int) animation.getAnimatedValue();
                view.setLayoutParams(params);
            }
        });
        return animator;
    }


}
