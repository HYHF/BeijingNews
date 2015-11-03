package com.example.hyh_1.beijing_news.iml.imi_menu_tag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.activity.NewsDetailActivity;
import com.example.hyh_1.beijing_news.base.MenuTagDetailBasePager;
import com.example.hyh_1.beijing_news.domain.MenuTabBean;
import com.example.hyh_1.beijing_news.domain.MenuTabDetailBean;
import com.example.hyh_1.beijing_news.utils.CacheUtil;
import com.example.hyh_1.beijing_news.utils.DensityUtil;
import com.example.hyh_1.beijing_news.utils.NetUtil;
import com.example.hyh_1.beijing_news.view.PullToRefreshListView;
import com.example.hyh_1.beijing_news.view.TabHeadViewPager;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hyh_1 on 2015/10/30.
 */
public class MenuTagPager extends MenuTagDetailBasePager {
    private static final String READCATCHE = "readcatche";
    private MenuTabBean.DataBean.Children dataBean;
    private PullToRefreshListView lvContent;
    private MenuTabDetailBean menuTabDetailBean;
    private TabHeadViewPager vpHead;
    private LinearLayout llPointGroup;
    private TextView tvTagHeaddesc;
    private MHandler handler;
    private String moreUrl;

    AnimationAdapter animationAdapter;
    MBaseAdapter adapter = new MBaseAdapter();
    /**
     * 是否正在下拉刷新
     */
    private boolean isPullToRefresh = false;

    /**
     * 是否在上拉加载更多
     */
    private boolean isLoadingMore = false;

    /**
     * 图片处理的框架对象
     */
    private ImageLoader imageLoader;

    /**
     * viewpager图片显示的参数
     */
    DisplayImageOptions options;

    /**
     * viewpager图片显示的参数
     */
    DisplayImageOptions options2;

    public MenuTagPager(MainActivity context, MenuTabBean.DataBean.Children dataBean) {
        super(context);
        this.dataBean = dataBean;
        //if(dataBean!=null);
        //为imageLoader设置参数,此处为默认的
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(mActivity).build();


        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(5))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        options2 = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(5))
                .showImageForEmptyUri(R.drawable.news_pic_default)
                .showImageOnFail(R.drawable.news_pic_default)
                .showImageOnLoading(R.drawable.news_pic_default)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .build();


        imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);

        handler = new MHandler();

    }

    @Override
    protected View initChildView() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        lvContent = (PullToRefreshListView) view.findViewById(R.id.lv_tab_pager);
        View head = View.inflate(mActivity, R.layout.tab_detail_head, null);
        vpHead = (TabHeadViewPager) head.findViewById(R.id.vp_tab_head);
        llPointGroup = (LinearLayout) head.findViewById(R.id.ll_tab_head_pointgroup);
        tvTagHeaddesc = (TextView) head.findViewById(R.id.tv_tab_head_desc);

        View headerView = View.inflate(mActivity, R.layout.refresh_head, null);
        View footerView = View.inflate(mActivity, R.layout.refresh_footer, null);

        //为listview设置下拉刷新的头部
        lvContent.setRefreshHeader(headerView);

        //为listview设置上拉刷新的尾部
        lvContent.setRefreshFooter(footerView);


        //确保head被加在最上面
        lvContent.addHeaderView(head);
        //设置下滑刷新的监听
        lvContent.setRefreshListener(new MRefreshListener(headerView));

        lvContent.setOnItemClickListener(new MonItemClickListener());


        return view;
    }


    @Override
    public void initData() {
        super.initData();

        //先从缓存中获取
        String string = CacheUtil.getString(mActivity, NetUtil.baseUrl + dataBean.url, null);
        if (!TextUtils.isEmpty(string)) {
            try {
                processData(new JSONObject(string));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        initDataFromNet();
    }

    /**
     * 初始化vpHead
     */
    public void init() {
        //初始化头部描述
        tvTagHeaddesc.setText(menuTabDetailBean.data.topnews.get(0).title);
        vpHead.setCurrentItem(0);
    }

    /**
     * 从网上加载更多的数据
     *
     * @param more
     */
    private void loadMoreFromNet(String more) {

        if(TextUtils.isEmpty(more)){
            Toast.makeText(mActivity, "没有更多的数据了", Toast.LENGTH_SHORT).show();
            lvContent.finishDragRefresh();
            return;
        }
        isLoadingMore = true;
        NetUtil.getInstance(mActivity).doWithJsonObject(NetUtil.baseUrl + more, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lvContent.finishDragRefresh();
                processData(response);
                //缓存数据
                CacheUtil.putString(mActivity, NetUtil.baseUrl + dataBean.url, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "获取更多数据失败", Toast.LENGTH_SHORT).show();
                lvContent.finishDragRefresh();
            }
        });
    }

    /**
     * 从网上获取数据
     */
    private void initDataFromNet() {
        NetUtil.getInstance(mActivity).doWithJsonObject(NetUtil.baseUrl + dataBean.url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("%%%%%%%%%%%%5", response.toString());

                processData(response);

                if (isPullToRefresh) {
                    lvContent.finishPullRefresh();
                    isPullToRefresh = false;
                }
                //缓存数据
                CacheUtil.putString(mActivity, NetUtil.baseUrl + dataBean.url, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "获取失败", Toast.LENGTH_SHORT).show();


                if (isPullToRefresh) {
                    lvContent.finishPullRefresh();
                    isPullToRefresh = false;
                }
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void pullToRefresh() {
        initDataFromNet();
        isPullToRefresh = true;
    }

    /**
     * 解析数据
     *
     * @param response
     */
    private void processData(JSONObject response) {

        if (isLoadingMore) {
            isLoadingMore = false;

            MenuTabDetailBean mtdb = new MenuTabDetailBean().parse(response);
            moreUrl=mtdb.data.more;
            if (menuTabDetailBean != null) {

                menuTabDetailBean.data.news.addAll(mtdb.data.news);
                //if()
                animationAdapter.notifyDataSetChanged();
                lvContent.setSelection(lvContent.getLastVisiblePosition());
            }

        } else {


            menuTabDetailBean = new MenuTabDetailBean().parse(response);
            moreUrl=menuTabDetailBean.data.more;

            //listView 的动画
            animationAdapter = new SwingBottomInAnimationAdapter(adapter);
            animationAdapter.setAbsListView(lvContent);
            lvContent.setAdapter(animationAdapter);


            vpHead.setAdapter(new MPagerAdapter());
            vpHead.setOnPageChangeListener(new MOnpagerChangeListener());

            llPointGroup.removeAllViews();
            //动态添加指示点

            for (int i = 0; i < menuTabDetailBean.data.topnews.size(); i++) {

                ImageView iv = new ImageView(mActivity);
                iv.setBackgroundResource(R.drawable.tag_header_point_bg_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mActivity, 5), DensityUtil.dip2px(mActivity, 5));
                if (i == 0) {
                    iv.setEnabled(true);
                } else {
                    iv.setEnabled(false);
                    params.leftMargin = DensityUtil.dip2px(mActivity, 10);
                }

                llPointGroup.addView(iv, params);
            }


            //初始化头部描述
            tvTagHeaddesc.setText(menuTabDetailBean.data.topnews.get(0).title);
            vpHead.setCurrentItem(0);

            //发轮播消息
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(1, 2000);

            vpHead.setOnTouchListener(new MOnTouchListener());

        }


    }

    class MonItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realId=position-2;

            String mid=menuTabDetailBean.data.news.get(realId).id;

           String  catcheIds = CacheUtil.getString(mActivity, READCATCHE, "");

            if(!catcheIds.contains(mid)){

                String values="";
                //判断是否第一次进入
                if(catcheIds.equals("")){//第一次
                    values=mid+",";
                }else{//不是第一次
                    values=catcheIds+mid+",";
                }
                //保存id
                CacheUtil.putString(mActivity,READCATCHE,values);
                animationAdapter.notifyDataSetChanged();

            }


            Intent intent=new Intent(mActivity, NewsDetailActivity.class);
            intent.setData(Uri.parse(menuTabDetailBean.data.news.get(realId).url));
            mActivity.startActivity(intent);

        }
    }

    class MRefreshListener implements PullToRefreshListView.RefreshListener {

        private ProgressBar pb;
        private ImageView iv;
        private TextView tvTitle;
        private TextView tvTime;
        RotateAnimation raP;
        RotateAnimation raR;

        public MRefreshListener(View headerView) {
            pb = (ProgressBar) headerView.findViewById(R.id.pb_refreshHeader);
            iv = (ImageView) headerView.findViewById(R.id.iv_refreshHeader);
            tvTitle = (TextView) headerView.findViewById(R.id.tv_refreshHeader_title);
            tvTime = (TextView) headerView.findViewById(R.id.tv_refreshHeader_time);
            pb.setVisibility(View.GONE);
            initAnim();
        }

        /**
         * 初始化动画
         */
        private void initAnim() {

            raP = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            raP.setDuration(500);
            raP.setFillAfter(true);

            raR = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            raR.setDuration(500);
            raR.setFillAfter(true);

        }

        @Override
        public void onPull() {
            pb.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
        }


        @Override
        public void onRelease() {
            tvTitle.setText("手松刷新");
            iv.clearAnimation();
            iv.startAnimation(raR);
        }

        @Override
        public void onPushBack() {

            tvTitle.setText("下拉刷新");
            iv.clearAnimation();
            iv.startAnimation(raP);
        }

        @Override
        public void onRefreshing() {
            tvTitle.setText("正在刷新");
            iv.clearAnimation();
            iv.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            pullToRefresh();
        }

        @Override
        public void onHeaderRefreshFinished() {

        }

        @Override
        public void onFooterRefreshFinished() {

        }

        @Override
        public void onLoadingMore() {
            if (menuTabDetailBean != null) {

                Log.e("dddddddddddddddddddd", "onLoadingMore :"+moreUrl);
                loadMoreFromNet(moreUrl);
            }
        }
    }


    class MOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendEmptyMessageDelayed(1, 2000);
                    break;
            }

            return false;
        }
    }


    /**
     * 处理轮播图
     */
    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {

                vpHead.setCurrentItem((vpHead.getCurrentItem() + 1) % (menuTabDetailBean.data.topnews.size()));
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        }
    }

    class MOnpagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            int childCount = llPointGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = llPointGroup.getChildAt(i);

                if (i == position) {
                    childAt.setEnabled(true);
                } else {
                    childAt.setEnabled(false);
                }
            }

            tvTagHeaddesc.setText(menuTabDetailBean.data.topnews.get(position).title);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return menuTabDetailBean.data.news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                convertView = View.inflate(mActivity, R.layout.tab_detail_pager_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            imageLoader.displayImage(menuTabDetailBean.data.news.get(position).listimage, holder.ivtabdetailitemicon, options2);
            Log.e("显示图片：", menuTabDetailBean.data.news.get(position).listimage);
            holder.tvtabdetailitemtime.setText(menuTabDetailBean.data.news.get(position).pubdate);
            holder.tvtabdetailitemtitle.setText(menuTabDetailBean.data.news.get(position).title);

            String catcheids = CacheUtil.getString(mActivity, READCATCHE, "");
            if(catcheids.contains(menuTabDetailBean.data.news.get(position).id)){

                holder.tvtabdetailitemtitle.setTextColor(Color.GRAY);
            }else {
                holder.tvtabdetailitemtitle.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }

    public static class ViewHolder {
        public final ImageView ivtabdetailitemicon;
        public final TextView tvtabdetailitemtitle;
        public final TextView tvtabdetailitemtime;
        //public final ImageView ivtabdetailcomment;
        public final View root;

        public ViewHolder(View root) {
            ivtabdetailitemicon = (ImageView) root.findViewById(R.id.iv_tab_detail_item_icon);
            tvtabdetailitemtitle = (TextView) root.findViewById(R.id.tv_tab_detail_item_title);
            tvtabdetailitemtime = (TextView) root.findViewById(R.id.tv_tab_detail_item_time);
            // ivtabdetailcomment = (ImageView) root.findViewById(R.id.iv_tab_detail_comment);
            this.root = root;
        }
    }

    /**
     * 顶部轮播图的viewpager的adapter
     */
    class MPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return menuTabDetailBean.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //加载相应的顶部轮播的图片

            ImageView iv = new ImageView(mActivity);
            imageLoader.displayImage(menuTabDetailBean.data.topnews.get(position).topimage, iv, options);

            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }

}
