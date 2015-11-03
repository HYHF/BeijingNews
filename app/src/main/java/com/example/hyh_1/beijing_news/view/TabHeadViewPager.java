package com.example.hyh_1.beijing_news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hyh_1 on 2015/10/30.
 */
public class TabHeadViewPager extends ViewPager {
    public TabHeadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    private int downX;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                downX= (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                int disX= (int) (ev.getRawX()-downX);

                if(disX>0&&getCurrentItem()!=0){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
