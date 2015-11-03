package com.example.hyh_1.beijing_news.base;

import android.app.Activity;
import android.view.View;

import com.example.hyh_1.beijing_news.activity.MainActivity;

/**
 * Created by hyh_1 on 2015/10/28.
 */
public abstract class MenuDetailBasePager {

   public MainActivity mActivity;
    public View rootView;
    public MenuDetailBasePager(MainActivity context) {
        this.mActivity=context;
        rootView=initChildView();
    }

    protected abstract View initChildView();


    public void initData(){


    }
}
