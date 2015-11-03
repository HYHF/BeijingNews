package com.example.hyh_1.beijing_news.iml.imi_menu_detail;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.base.MenuDetailBasePager;

/**
 * 菜单中互动选项对应的页面
 * Created by hyh_1 on 2015/10/28.
 */
public class InteractMenuDetailPager extends MenuDetailBasePager {

    public InteractMenuDetailPager(MainActivity context) {
        super(context);
    }

    @Override
    protected View initChildView() {
        TextView tv=new TextView(mActivity);
        tv.setText(this.getClass().getSimpleName());
        tv.setGravity(Gravity.CENTER);

        return tv;
    }
}
