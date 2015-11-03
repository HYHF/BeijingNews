package com.example.hyh_1.beijing_news.iml;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.base.BasePager;
import com.example.hyh_1.beijing_news.base.MenuDetailBasePager;
import com.example.hyh_1.beijing_news.domain.MenuTabBean;
import com.example.hyh_1.beijing_news.fragment.LeftMenuFragment;
import com.example.hyh_1.beijing_news.iml.imi_menu_detail.InteractMenuDetailPager;
import com.example.hyh_1.beijing_news.iml.imi_menu_detail.NewsMenuDetailPager;
import com.example.hyh_1.beijing_news.iml.imi_menu_detail.PhotosMenuDetailPager;
import com.example.hyh_1.beijing_news.iml.imi_menu_detail.TopicMenuDetailPager;
import com.example.hyh_1.beijing_news.utils.CacheUtil;
import com.example.hyh_1.beijing_news.utils.NetUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh_1 on 2015/10/28.
 */
public class NewsCenterPager extends BasePager {

    MenuTabBean menuTabBean;
    private List<MenuDetailBasePager> memuDetailPagers;
    public NewsCenterPager(Activity context) {
        super(context);
    }

    @Override
    public View initChildView() {
        TextView tv=new TextView(mActivity);
        tv.setText(this.getClass().getSimpleName());
        setTitle("新闻中心");
        setLeftBt(View.VISIBLE);

        return tv;
    }

    @Override
    public void initData() {
        //使得菜单可以被打开
        SlidingMenu menu = ((MainActivity) mActivity).getMenu();
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        memuDetailPagers=new ArrayList<>();

        String string = CacheUtil.getString(mActivity, NetUtil.newCenter, null);
        if(string!=null){
            //从jso数据中初始化对象
            try {
                processData(new JSONObject(string));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        initDataFromNet();
    }


    private void initDataFromNet() {

        NetUtil.getInstance(mActivity).doWithJsonObject(NetUtil.newCenter, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //处理数据
                    processData(response);

                    //保存缓存
                    CacheUtil.putString(mActivity, NetUtil.newCenter, response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "无法连接网络", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * 处理数据
     * @param response
     * @throws JSONException
     */
    private void processData(JSONObject response) throws JSONException {
        //从jso数据中初始化对象
        menuTabBean = new MenuTabBean().init(response);
        //将数据传输给菜单
        LeftMenuFragment leftMenuFragment = (LeftMenuFragment) ((MainActivity) mActivity).getSupportFragmentManager().findFragmentByTag("menu_fragment");
        leftMenuFragment.setData(menuTabBean);

        //初始化菜单对应的详细页面
        initPagers();
    }

    /**
     * 初始化菜单详情各个页面
     */
    private void initPagers() {
        memuDetailPagers.add(new NewsMenuDetailPager((MainActivity) mActivity,menuTabBean));
        memuDetailPagers.add(new TopicMenuDetailPager((MainActivity)mActivity));
        memuDetailPagers.add(new PhotosMenuDetailPager((MainActivity)mActivity));
        memuDetailPagers.add(new InteractMenuDetailPager((MainActivity)mActivity));
        switchPager(((MainActivity) mActivity).getLeftMenu().selectedIndex);
    }

    /**
     * 根据位置切换页面
     * @param position
     */
    public void switchPager(int position){


        flContent.removeAllViews();
        flContent.addView(memuDetailPagers.get(position).rootView);
        setTitle(menuTabBean.data.get(position).title);

        //从网上初始化数据
        memuDetailPagers.get(position).initData();
    }
}
