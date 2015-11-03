package com.example.hyh_1.beijing_news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.zip.Inflater;

/**
 * Created by 黄洞洞 on 2015/10/31.
 */
public class PullToRefreshListView extends ListView {

    private View mRefreshHeader;
    private int headHeight;
    private int headWidth;
    private RefreshListener refreshListener;
    private View mRefreshFooter;
    private int footerHeight;

    /**
     * 判断是否正在加载更多，防止过多的加载
     */
    private boolean isLoadingMore=false;
    /**
     * 默认当前状态为下拉刷新状态
     */
    private Status mStatus = Status.PULLDOWN_TO_FEFRESH;

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * Indicates the current status of the header. Each status will be set only once
     * during the lifetime of a refreshing.
     */
    public enum Status {
        /**
         * 用手往回推送
         */
        PUSH_TO_BACK,
        /**
         * 可以松手刷新状态
         */
        RELEASE_TO_REFRESH,
        /**
         * 向下拉状态
         */
        PULLDOWN_TO_FEFRESH,
        /**
         * 正在刷新状态
         */
        REFRESHING,
    }

    /**
     * 引入头部的布局
     *
     * @param
     */
    public void setRefreshHeader(View headerView) {
        mRefreshHeader = headerView;
        mRefreshHeader.measure(0, 0);
        headWidth = mRefreshHeader.getMeasuredWidth();
        headHeight = mRefreshHeader.getMeasuredHeight();
        addHeaderView(mRefreshHeader);

        mRefreshHeader.setPadding(0, -headHeight, 0, 0);
    }

    /**
     * 引入上拉加载更多的布局
     */
    public void setRefreshFooter(View footerView){
        this.mRefreshFooter=footerView;
        this.addFooterView(mRefreshFooter);
        mRefreshFooter.measure(0,0);
        footerHeight=mRefreshFooter.getMeasuredHeight();

        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


                if(!isLoadingMore&&scrollState==SCROLL_STATE_IDLE&&getLastVisiblePosition()==getAdapter().getCount()-1){
                    mRefreshFooter.setPadding(0,0,0,0);
                    Log.e("&&&&&&&&&&&&&&&7","正在加载更多");
                    //防止过多的加载
                    isLoadingMore=true;
                    if(refreshListener!=null){
                        refreshListener.onLoadingMore();
                    }

                }
               // Log.e("&&&&&&&&&&&&&&&7",scrollState+"");

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    /**
     * 只有当第一个孩子完全现实的时候，才能进行刷新，根据返回值来判断是否能够刷新
     * true：能
     * false：不能
     * @return
     */
    private boolean compareXY() {
        int[] listViewXY = new int[2];
        this.getLocationOnScreen(listViewXY);

        int[] headerXY = new int[2];
        mRefreshHeader.getLocationOnScreen(headerXY);
        return headerXY[1] >(listViewXY[1]-headHeight);
    }


    private int downY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                Log.e("****=**downY",downY+"downY");
                break;
            case MotionEvent.ACTION_MOVE:

                if(!compareXY()){
                    break;
                }

                if(mStatus==Status.REFRESHING)
                    break;

                Log.e("****=**downY",downY+"");
                if (downY == 0) {
                    downY = (int) ev.getRawY();
                }

                int disY = (int) (ev.getRawY() - downY);

                if (mRefreshHeader != null) {
                    int padding=-headHeight+disY;
                    if(padding<-headHeight){
                        padding=-headHeight;
                    }else if(padding>headHeight){
                        padding=headHeight;
                    }
                    //正在向下拉，没有到释放刷新的阶段
                    if(padding<=10&&mStatus==Status.PULLDOWN_TO_FEFRESH){

                        doWithStatus();
                    }

                    if(padding<=10&&mStatus==Status.PUSH_TO_BACK){
                        mStatus=Status.PULLDOWN_TO_FEFRESH;
                    }
                    //这里写10 是为了更好的效果，当回推的时候状态改变时，不会被部分遮挡
                    //判断没有刷新的状态下，用手往回推的情况
                    if(padding<=10&&mStatus==Status.RELEASE_TO_REFRESH){
                        mStatus=Status.PUSH_TO_BACK;
                        doWithStatus();
                    }else if(padding>10&&mStatus==Status.PULLDOWN_TO_FEFRESH){
                        mStatus=Status.RELEASE_TO_REFRESH;
                        doWithStatus();
                    }

                    //当没有刷新，但用手往回推送时，使得listview不能滑动，防止滑动影响效果
                    if(mStatus==Status.RELEASE_TO_REFRESH){
                        this.setEnabled(false);
                    }else{
                        this.setEnabled(true);
                    }
                    mRefreshHeader.setPadding(0,padding, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                //使得listview可用，可滚动
                this.setEnabled(true);
                //downY = 0;
                if(mStatus==Status.RELEASE_TO_REFRESH){
                    mStatus=Status.REFRESHING;
                    doWithStatus();
                }else if(mStatus!=Status.REFRESHING){
                    mRefreshHeader.setPadding(0,-headHeight, 0, 0);
                }
                break;
        }

        return true;
    }


    /**
     * 根据相应的状态做相应的动作，主要是掉不同的接口
     */
    private void doWithStatus() {
        switch (mStatus){
            case PULLDOWN_TO_FEFRESH:
                if(refreshListener!=null){
                    refreshListener.onPull();
                }
                break;
            case PUSH_TO_BACK:
                if(refreshListener!=null)
                    refreshListener.onPushBack();

                break;
            case RELEASE_TO_REFRESH:
                if(refreshListener!=null){
                    refreshListener.onRelease();
                }
                break;
            case REFRESHING:
                mRefreshHeader.setPadding(0,0,0, 0);
                if(refreshListener!=null){
                    refreshListener.onRefreshing();
                }
                break;

        }
    }

    /**
     * 当刷新无论成功还是失败，调用这个方法，还原listview,针对下拉的情况
     */
    public void finishPullRefresh(){
        mRefreshHeader.setPadding(0,-headHeight, 0, 0);
        mStatus=Status.PULLDOWN_TO_FEFRESH;
        if(refreshListener!=null){
            refreshListener.onHeaderRefreshFinished();
        }
    }

    /**
     * 当上拉加载更多完成时，外界调用他
     */
    public void finishDragRefresh(){
        if(mRefreshFooter!=null){
            isLoadingMore=false;
            mRefreshFooter.setPadding(0,-footerHeight,0,0);
        }

    }


    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface RefreshListener{
        void onPull();
        void onRelease();
        void onPushBack();
        void onRefreshing();
        void onHeaderRefreshFinished();
        void onFooterRefreshFinished();
        void onLoadingMore();

    }


}
