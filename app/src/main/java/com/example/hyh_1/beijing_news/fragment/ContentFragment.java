package com.example.hyh_1.beijing_news.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.base.BasePager;
import com.example.hyh_1.beijing_news.iml.GoverPager;
import com.example.hyh_1.beijing_news.iml.HomePager;
import com.example.hyh_1.beijing_news.iml.NewsCenterPager;
import com.example.hyh_1.beijing_news.iml.SettingPager;
import com.example.hyh_1.beijing_news.iml.SmartServicePager;
import com.example.hyh_1.beijing_news.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh_1 on 2015/10/27.
 */
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.rg_main)
    RadioGroup rgMain;
    @ViewInject(R.id.rb_main_home)
    RadioButton rbHome;
    @ViewInject(R.id.rb_main_smalt)
    RadioButton rbSmart;
    @ViewInject(R.id.rb_main_gover)
    RadioButton rbGover;
    @ViewInject(R.id.rb_main_setting)
    RadioButton rbSetting;
    @ViewInject(R.id.rb_main_news)
    RadioButton rbNews;
    @ViewInject(R.id.vp_main)
    NoScrollViewPager vp;

    private List<BasePager> pagers;
    @Override
    public View initChildView() {
        View view = View.inflate(mActivity, R.layout.content_main, null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        pagers=new ArrayList<>();
        initPagers();
        vp.setAdapter(new MOpagerAdapter());
        //默认选中首页
        rgMain.setOnCheckedChangeListener(new MOnCheckedChangeListener());
        rbHome.setChecked(true);
    }

    /**
     * 初始化子页面
     */
    private void initPagers() {
        pagers.add(new HomePager(mActivity));
        pagers.add(new NewsCenterPager(mActivity));
        pagers.add(new SmartServicePager(mActivity));
        pagers.add(new GoverPager(mActivity));
        pagers.add(new SettingPager(mActivity));
    }

    /**
     * 获取新闻页面
     * @param
     * @return
     */
    public NewsCenterPager getPager(){

        return (NewsCenterPager) pagers.get(1);
    }

    /**
     * 选项改变
     */
    private  class MOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            //初始化菜单不可以打开
            SlidingMenu menu = ((MainActivity) mActivity).getMenu();
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

            switch (checkedId){
                case R.id.rb_main_home:
                    vp.setCurrentItem(0);
                    pagers.get(0).initData();
                    break;
                case R.id.rb_main_news:
                    vp.setCurrentItem(1);
                    pagers.get(1).initData();
                    break;
                case R.id.rb_main_smalt:
                    vp.setCurrentItem(2);
                    pagers.get(2).initData();
                    break;
                case R.id.rb_main_gover:
                    vp.setCurrentItem(3);
                    pagers.get(3).initData();
                    break;
                case R.id.rb_main_setting:
                    vp.setCurrentItem(4);
                    pagers.get(4).initData();
                    break;
            }
        }
    }

    private  class MOpagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            View rootView=pager.getRootView();
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View)object);
        }
    }
}
