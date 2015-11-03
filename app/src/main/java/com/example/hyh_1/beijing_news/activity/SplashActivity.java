package com.example.hyh_1.beijing_news.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.hyh_1.beijing_news.R;

public class SplashActivity extends Activity {



    ImageView ivsplashicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView();

        initAnim();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {

        RotateAnimation ra=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);

        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(2000);

        ScaleAnimation sa=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);

        AnimationSet set=new AnimationSet(true);

        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);

        ivsplashicon.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if( SplashActivity.this.getSharedPreferences("valus",0).getBoolean("isFirst",false))
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
               else startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        ivsplashicon = (ImageView) findViewById(R.id.iv_splash_icon);
    }
}
