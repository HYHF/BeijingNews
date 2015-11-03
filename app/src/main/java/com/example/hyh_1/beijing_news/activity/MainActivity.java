package com.example.hyh_1.beijing_news.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.fragment.ContentFragment;
import com.example.hyh_1.beijing_news.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    public static final String CONTENT_FRAGMENT = "content_fragment";
    public static final String MENU_FRAGMENT = "menu_fragment";
    private SlidingMenu slidingMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置左侧菜单
        setBehindContentView(R.layout.left_menu);
        // 设置模式：左侧菜单+主页面
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);

        // 设置滑动模式：全屏
        //slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        // 设置主页面打开为200
        slidingMenu.setBehindOffset(280);
        initFragment();
    }

    private void initFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fl_main, new ContentFragment(), CONTENT_FRAGMENT);
        transaction.replace(R.id.fl_menu, new LeftMenuFragment(), MENU_FRAGMENT);

        transaction.commit();
    }

    /**
     * 获取菜单
     * @return
     */
    public SlidingMenu getMenu(){
        return slidingMenu;
    }


    /**
     * 获取内容页面
     * @return
     */
    public ContentFragment getContent(){

       return (ContentFragment) getSupportFragmentManager().findFragmentByTag(CONTENT_FRAGMENT);
    }

    /**
     * 获取菜单页面
     * @return
     */
    public LeftMenuFragment getLeftMenu(){

        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(MENU_FRAGMENT);
    }

}
