package com.example.hyh_1.beijing_news.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.activity.MainActivity;

/**
 * Created by hyh_1 on 2015/10/28.
 */
public abstract class BasePager {

    protected Activity mActivity;
    private View rootView;
    private TextView tvTitle;
    private ImageButton ibMenu;
    public ImageButton ibSwitch;
    public FrameLayout flContent;
    public BasePager(Activity context) {
        this.mActivity=context;
        initView();

    }

    public void initView(){

        rootView= View.inflate(mActivity, R.layout.basepager_layout,null);
        tvTitle= (TextView) rootView.findViewById(R.id.tv_basepager_title);
        ibMenu= (ImageButton) rootView.findViewById(R.id.ib_basepager_menu);
        flContent= (FrameLayout) rootView.findViewById(R.id.fl_basepager);
        ibSwitch= (ImageButton) rootView.findViewById(R.id.ib_basepager_switch);
        ibMenu.setOnClickListener(new MOnClickListener());
        flContent.addView(initChildView());

    }

    protected void setLeftBt(int visibility){
        ibMenu.setVisibility(visibility);
    }
    protected void setTitle(String title){
        tvTitle.setText(title);
    }

    public abstract View initChildView();


    public View getRootView(){

        return rootView;
    }


    public void initData(){

    }

    private class MOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_basepager_menu://关闭或者打开菜单
                    MainActivity mainActivity=(MainActivity)mActivity;
                    mainActivity.getMenu().toggle();
                    break;
            }
        }
    }

}
