package com.example.hyh_1.beijing_news.utils;

import android.app.Activity;
import android.view.ContextMenu;

/**
 * Created by hyh_1 on 2015/10/30.
 */
public class CacheUtil {

    Activity context;

    public CacheUtil(Activity context) {
        this.context=context;
    }


    public static void putString(Activity context,String key,String value){

        context.getSharedPreferences("bjn",0).edit().putString(key,value).commit();
    }

    public static String getString(Activity context,String key,String def){

       return context.getSharedPreferences("bjn",0).getString(key,def);
    }
}
