package com.example.hyh_1.beijing_news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hyh_1.beijing_news.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    List<ImageView> imgs;ViewPager vpguide;
    Button btguide;
    LinearLayout llguidepointgroup;
    ImageView ivguidered;
    private int[] ids={R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    //private int dis=dp2pix(20);
    private int wW;
    private int wH;
    int dis_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        initData();
    }

    private void initData() {
        imgs=new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ImageView iv=new ImageView(this);
            iv.setBackgroundResource(ids[i]);
            imgs.add(iv);
        }

        vpguide.setAdapter(new MPagerAdapter());
        vpguide.setOnPageChangeListener(new MOnpagerChangeListener());

        LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(dp2pix(10),dp2pix(10));
        //添加灰色的点
        for(int i=0;i<imgs.size();i++){
            View point=new View(this);
            point.setBackgroundResource(R.drawable.point_normal);
            param.rightMargin=dp2pix(10);
            llguidepointgroup.addView(point,param);
        }

        ivguidered.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivguidered.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                dis_2 = llguidepointgroup.getChildAt(1).getLeft() - llguidepointgroup.getChildAt(0).getLeft();
            }
        });

        btguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideActivity.this.getSharedPreferences("valus",0).edit().putBoolean("isFirst",true).commit();
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    private void initView() {
        vpguide = (ViewPager) findViewById(R.id.vp_guide);
        btguide = (Button) findViewById(R.id.bt_guide);
        llguidepointgroup = (LinearLayout) findViewById(R.id.ll_guide_point_group);
        ivguidered = (ImageView) findViewById(R.id.iv_guide_red);
    }


    class MOnpagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float marginLeft=dis_2*(positionOffset+position);

           RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(dp2pix(10),dp2pix(10));
            param.leftMargin= (int) marginLeft;
            ivguidered.setLayoutParams(param);
            ivguidered.requestLayout();


        }

        @Override
        public void onPageSelected(int position) {
            if(position==imgs.size()-1){
                btguide.setVisibility(View.VISIBLE);
            }else{
                btguide.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class MPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView iv=imgs.get(position);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    public int dp2pix(int dp){

        WindowManager wm=getWindowManager();
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return (int) (dp*outMetrics.density+0.5f);
    }
}
