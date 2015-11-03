package com.example.hyh_1.beijing_news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by 黄洞洞 on 2015/11/1.
 * 支持下拉刷新，和下拉加载更多
 *需要从布局文件中引入
 */
public class Pull_To_Refresh_ListView extends ListView{
    public Pull_To_Refresh_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 可刷新的头部和尾部的状态值
     */
    public enum Status {

        /************************头部刷新控件的状态*****************************/
        /**
         * 没有刷新，用手往回推送的状态
         */
        HEAD_PUSH_TO_BACK,
        /**
         * 可以松手刷新状态
         */
        HEAD_RELEASE_TO_REFRESH,
        /**
         * 可以或者正在向下拉状态
         */
        HEAD_PULLDOWN_TO_FEFRESH,
        /**
         * 正在刷新状态
         */
        HEAD_REFRESHING,

        /*******************尾部刷新控件的状态*************************/

        /**
         * 没有刷新，用手往回推送的状态
         */
        FOOT_PUSH_TO_BACK,
        /**
         * 可以松手刷新状态
         */
        FOOT_RELEASE_TO_REFRESH,
        /**
         * 可以或者正在向下拉状态
         */
        FOOT_DRAGUP_TO_FEFRESH,
        /**
         * 正在刷新状态
         */
        FOOT_REFRESHING,

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);//加上这一行，使得点击事件有效

        





        return true;
    }
}
