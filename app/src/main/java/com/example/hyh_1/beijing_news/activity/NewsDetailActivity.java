package com.example.hyh_1.beijing_news.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.hyh_1.beijing_news.R;

import java.lang.reflect.Modifier;

public class NewsDetailActivity extends Activity {


    ImageButton ibnewdetailback;
    ImageButton ibnewdetailfont;
    ImageButton ibnewdetailshare;
    RelativeLayout rlnewsdetail;
    ProgressBar pbnewdetail;
    WebView wvnewdetail;
    private MOnclickListener mOnclickListener;
    private String[] items={"超大","大号","正常","小号","超小"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        initData();

    }

    private void initData() {

        Uri uri = getIntent().getData();

        wvnewdetail.loadUrl(uri.toString());
        WebSettings settings = wvnewdetail.getSettings();
        settings.setJavaScriptEnabled(true);

        wvnewdetail.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbnewdetail.setVisibility(View.GONE);
            }
        });

        //放大缩小按钮
        settings.setBuiltInZoomControls(true);

        //双击变大变小
        settings.setUseWideViewPort(true);

        mOnclickListener=new MOnclickListener();

        ibnewdetailfont.setOnClickListener(mOnclickListener);
        ibnewdetailback.setOnClickListener(mOnclickListener);
        ibnewdetailshare.setOnClickListener(mOnclickListener);

    }

    private void initView() {
        ibnewdetailback = (ImageButton) findViewById(R.id.ib_new_detail_back);
        ibnewdetailfont = (ImageButton) findViewById(R.id.ib_new_detail_font);
        ibnewdetailshare = (ImageButton) findViewById(R.id.ib_new_detail_share);
        rlnewsdetail = (RelativeLayout) findViewById(R.id.rl_news_detail);
        pbnewdetail = (ProgressBar) findViewById(R.id.pb_new_detail);
        wvnewdetail = (WebView) findViewById(R.id.wv_new_detail);
    }


    class MOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_new_detail_font:
                    AlertDialog dialog=new AlertDialog.Builder(NewsDetailActivity.this)
                            .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setTitle("选择字体大小")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int i;
                                }
                            })
                            .show();


                    /**
                     * 去哦很多订饭vd发撒法阿三的发生分
                     */
                break;
                case R.id.ib_new_detail_back:

                    break;
                case R.id.ib_new_detail_share:

                    break;
            }
        }
    }
}
