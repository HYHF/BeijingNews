package com.example.hyh_1.beijing_news.test;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hyh_1.beijing_news.utils.ImageUtil;
import com.example.hyh_1.beijing_news.utils.NetUtil;

/**
 * Created by hyh_1 on 2015/10/27.
 */
public class TestVolly {

    public void testVolly(){

        //NetUtil.getInstance().initRequest< StringRequest>("",new Response.Listener<String>())
        StringRequest rq=new StringRequest("", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

       // ImageUtil.Buidler.newInstance().loadImage()
    }
}
