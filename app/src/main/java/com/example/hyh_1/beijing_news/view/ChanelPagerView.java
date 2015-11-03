package com.example.hyh_1.beijing_news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.hyh_1.beijing_news.utils.DensityUtil;


/**
 * Created by hyh_1 on 2015/10/15.
 */
public class ChanelPagerView extends RelativeLayout {
    private final Context context;
    private final AttributeSet attrs;

    public ChanelPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int raw;
        if(getChildCount()%4>0){
            raw=getChildCount()/4+1;
        }else{
            raw=getChildCount()/4;
        }

        int mleft=0;
        int mtop=0;
        int mright=0;
        int mbottom=0;
        for (int i=0;i<raw;i++){
            for(int j=0;j<4;j++){
                if(i*4+j<getChildCount()){
                    getChildAt(i*4+j).getLayoutParams().height= DensityUtil.dip2px(context, 35);
                    getChildAt(i*4+j).getLayoutParams().width=DensityUtil.dip2px(context,80);
                    mleft=(getChildAt(i*4+j).getWidth())*j+(((getWidth()-(4*(getChildAt(i*4+j).getWidth())))/5))*(j+1);
                    mtop=i*getChildAt(i*4+j).getHeight()+(i+1)*40;
                    mright=(j+1)*((getChildAt(i*4+j).getWidth()))+(((getWidth()-(4*(getChildAt(i*4+j).getWidth())))/5))*(j+1);
                    mbottom=(i+1)*getChildAt(i*4+j).getHeight()+(i+1)*40;
                    getChildAt(i*4+j).layout(mleft,mtop,mright,mbottom);
                }

            }
        }
    }
}
