package com.example.hyh_1.beijing_news.iml.imi_menu_detail;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.base.MenuDetailBasePager;
import com.example.hyh_1.beijing_news.domain.PhotoesBean;
import com.example.hyh_1.beijing_news.utils.L;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
//import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * 菜单中组图选项对应的页面
 * Created by hyh_1 on 2015/10/28.
 */
public class PhotosMenuDetailPager extends MenuDetailBasePager {

    private PhotoesBean photoesBean;
    private RecyclerView recyclerView;
    ImageLoader imageLoader=ImageLoader.getInstance();
   public DisplayImageOptions options;
    InnerHandler handler;
    public PhotosMenuDetailPager(MainActivity context) {
        super(context);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        //recyclerView.setItemAnimator(Sliding);
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(mActivity)
                .build();
        imageLoader.init(configuration);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();

        handler=new InnerHandler();

    }



    @Override
    protected View initChildView() {
        View view = View.inflate(mActivity, R.layout.photos_menu_detail_pager, null);
        recyclerView= (RecyclerView) view.findViewById(R.id.rv_photoes);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

       // Toast.makeText(mActivity, "请求数据", Toast.LENGTH_SHORT).show();

        initdataFromNet();

    }

    /**
     * 从网上获取数据
     */
    private void initdataFromNet() {
        OkHttpClient mOkHttpClient=new OkHttpClient();

        Request request=new Request.Builder()
                .url("http://192.168.10.145:8080/zhbj/photos/photos_1.json")
                .build();
        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                L.e(e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                photoesBean=PhotoesBean.objectFromData(response.body().string());
                handler.sendEmptyMessage(1);

            }
        });
    }


    class InnerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==1){
                recyclerView.setAdapter(new Adapter());
            }
        }
    }

  class Adapter extends RecyclerView.Adapter<Adapter.MViewHolder>{

      @Override
      public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          return new MViewHolder(View.inflate(mActivity,R.layout.photoes,null));
      }

      @Override
      public void onBindViewHolder(MViewHolder holder, int position) {
          PhotoesBean.DataEntity.NewsEntity newsEntity = photoesBean.data.news.get(position);
          holder.text.setText(newsEntity.title);
          imageLoader.displayImage(newsEntity.listimage, holder.icon,options);
      }

      @Override
      public int getItemCount() {
          return photoesBean.data.news.size();
      }



      class MViewHolder extends RecyclerView.ViewHolder{

          public ImageView icon;
          private TextView text;
          public MViewHolder(View itemView) {
              super(itemView);
              icon= (ImageView) itemView.findViewById(R.id.iv_icon_photoes);
              text= (TextView) itemView.findViewById(R.id.tv_text_photoes);
          }
      }
  }
}
