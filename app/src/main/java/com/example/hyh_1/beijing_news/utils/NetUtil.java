package com.example.hyh_1.beijing_news.utils;

import android.app.Activity;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 *
 * 联网工具类
 * Created by 黄洞洞 on 2015/10/27.
 */
public class NetUtil {

    public static String baseUrl="http://192.168.10.145:8080/zhbj";
    public static String newCenter="http://192.168.10.145:8080/zhbj/categories.json";
    private static NetUtil netUtil;
    private static Object obj=new Object();
    //单例
    public static NetUtil getInstance(Activity context){

        if(netUtil==null){
            synchronized (obj){
                if(netUtil==null){
                    netUtil=new NetUtil(context);
                }
            }
        }
        return netUtil;
    }

    private RequestQueue mQueue;

    private NetUtil(Activity context) {

         mQueue= Volley.newRequestQueue(context);
    }

    /**
     * 获取请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){

        return mQueue;
    }

    /**
     * 处理请求字符串的请求
     * @param url
     * @param listener
     * @param errorlistener
     * @return
     */
    public StringRequest doWithString(String url,Response.Listener<String> listener,Response.ErrorListener errorlistener){

        StringRequest sr=new StringRequest(url,listener,errorlistener);
        mQueue.add(sr);

        return sr;
    }

    /**
     * 处理请求json数组
     *
     */
    public JsonArrayRequest doWithJsonArray(String url,Response.Listener<JSONArray> listener,Response.ErrorListener errorlistener){

        JsonArrayRequest jr=new JsonArrayRequest(url,listener,errorlistener);
        mQueue.add(jr);
        return jr;
    }

    /**
     * 处理请求json对象
     *
     */
    public JsonObjectRequest doWithJsonObject(String url,Response.Listener<JSONObject> listener,Response.ErrorListener errorlistener){

        JsonObjectRequest jo=new MJsonObjectRequest(url,null,listener,errorlistener);
        mQueue.add(jo);
        return jo;
    }


    /**
     * 重写的JsonObjectRequest类，解决乱码问题
     */
    class MJsonObjectRequest extends JsonObjectRequest{

        public MJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString =
                        new String(response.data,"utf-8");
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }

}
