package com.xuanzhui.animations.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.xuanzhui.animations.R;

/**
 * 翻转效果
 */
public class FlipCardAnimView extends DialogFragment {
    private View flipView;

    public FlipCardAnimView() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flip_card_anim_view, container, false);
        flipView = view.findViewById(R.id.flipView);

        // When rotating large views, it is recommended to adjust the camera distance accordingly.
        flipView.setCameraDistance(60000);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 进入翻转
        final ObjectAnimator rotateOb1 = ObjectAnimator.ofFloat(flipView, "rotationY", 0, 90).setDuration(500);
        final ObjectAnimator scaleXOb = ObjectAnimator.ofFloat(flipView, "scaleX", 0.1F, 1F).setDuration(500);
        final ObjectAnimator scaleYOb = ObjectAnimator.ofFloat(flipView, "scaleY", 0.1F, 1F).setDuration(500);
        AnimatorSet animStart = new AnimatorSet();
        animStart.playTogether(rotateOb1, scaleXOb, scaleYOb);
        animStart.setInterpolator(new AccelerateInterpolator());

        // 结束翻转到正面
        final ObjectAnimator rotateOb2 = ObjectAnimator.ofFloat(flipView, "rotationY", -90, 0).setDuration(500);
        rotateOb2.setInterpolator(new DecelerateInterpolator());

        animStart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (getContext() == null) {
                    return;
                }
                flipView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.arc_succ_color));
                rotateOb2.start();
            }
        });
        flipView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.arc_fail_color));
        animStart.start();

        rotateOb2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

}
