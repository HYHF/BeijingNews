package com.example.hyh_1.beijing_news.iml.imi_menu_detail;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.base.MenuDetailBasePager;
import com.example.hyh_1.beijing_news.base.MenuTagDetailBasePager;
import com.example.hyh_1.beijing_news.domain.MenuTabBean;
import com.example.hyh_1.beijing_news.iml.imi_menu_tag.MenuTagPager;
import com.example.hyh_1.beijing_news.utils.NetUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单中新闻选项对应的页面
 * Created by hyh_1 on 2015/10/28.
 */
public class NewsMenuDetailPager extends MenuDetailBasePager {

    private MenuTabBean menuTabBean;
    private ViewPager vpContent;
    private List<MenuTagDetailBasePager> pagers;
    List<MenuTabBean.DataBean.Children> children;
    TabPageIndicator tabPageIndicator;
    public NewsMenuDetailPager(MainActivity context,MenuTabBean menuTabBean) {
        super(context);
        this.menuTabBean=menuTabBean;

        initPagers();
    }


    /**
     * 根据数据创建页面
     */
    private void initPagers() {

        if(menuTabBean!=null){
            pagers=new ArrayList<>();

            children = menuTabBean.data.get(0).children;
            for (int i = 0; i <children.size() ; i++) {

                pagers.add(new MenuTagPager(mActivity,children.get(i)));
            }

        }

        //viewpager创建适配器
        vpContent.setAdapter(new MPagerAdapter());
        tabPageIndicator.setViewPager(vpContent);
        tabPageIndicator.setOnPageChangeListener(new MPagerChangerListener());
    }

    @Override
    protected View initChildView() {

        View view = View.inflate(mActivity, R.layout.newmenu_detail_pager, null);
        vpContent= (ViewPager) view.findViewById(R.id.vp_newmenu);
        tabPageIndicator= (TabPageIndicator) view.findViewById(R.id.tabPagerIndicator);
        return view;
    }
    class MPagerChangerListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainActivity mActivity = (MainActivity) NewsMenuDetailPager.this.mActivity;
            SlidingMenu menu = mActivity.getMenu();

            if(position==0){
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else{

                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }

            //调用ziview的方法，使子view中的viewpager初始化为第一个页面
            pagers.get(position).init();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return children.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MenuTagDetailBasePager pager = pagers.get(position);
            View rootView = pager.rootView;
            container.addView(rootView);

            pager.initData();


            return rootView;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).title;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
