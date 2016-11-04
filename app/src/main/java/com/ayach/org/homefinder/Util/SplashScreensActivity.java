package com.ayach.org.homefinder.Util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.View.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by aziz on 03/01/2016.
 */
public class SplashScreensActivity extends Activity {
    Animation anim;
    private CircleImageView mLogo;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);


        mLogo = (CircleImageView) findViewById(R.id.logoico);
        welcomeText = (TextView) findViewById(R.id.welcome_text);

        mLogo.setAlpha(1.0F);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                visibleText();
            }

            @Override
            public void onAnimationEnd(Animation animation) {




            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /** Animation depends on category.
     * */



    private void visibleText() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
        alphaAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent il = new Intent(SplashScreensActivity.this, MainActivity.class);
                startActivity(il);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
