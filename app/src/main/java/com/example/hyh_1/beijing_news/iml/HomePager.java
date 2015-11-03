package com.example.hyh_1.beijing_news.iml;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by hyh_1 on 2015/10/28.
 */
public class HomePager extends BasePager {

    public HomePager(Activity context) {
        super(context);
    }

    @Override
    public View initChildView() {
        TextView tv=new TextView(mActivity);
        tv.setText(this.getClass().getSimpleName());
        setTitle("主页");
        setLeftBt(View.GONE);
        return tv;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(this.getClass().getSimpleName(),"ddddddd");
    }
}
