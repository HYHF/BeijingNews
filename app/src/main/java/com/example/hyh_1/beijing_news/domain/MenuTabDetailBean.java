package com.example.hyh_1.beijing_news.domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh_1 on 2015/10/30.
 */
public class MenuTabDetailBean  {

    public Data data;
    public String retcode;

    public  MenuTabDetailBean parse(JSONObject jsonobject){
       MenuTabDetailBean menuTabDetailBean=null;

        if(jsonobject!=null){
            menuTabDetailBean=new MenuTabDetailBean();

            JSONObject obj = jsonobject.optJSONObject("data");
            menuTabDetailBean.data=new Data().parse(obj);
            menuTabDetailBean.retcode=jsonobject.optString("retcode");
        }
        return menuTabDetailBean;
    }

    @Override
    public String toString() {
        return "MenuTabDetailBean{" +
                "data=" + data +
                ", retcode='" + retcode + '\'' +
                '}';
    }

    public class Data{
        public String countcommenturl;
        public String more;
        public List<News> news;
        public String title;
       // public List topic;
        public List<News> topnews;

        public  Data parse(JSONObject jsonObject){
            Data data=null;
            if(jsonObject!=null){
                data=new Data();
                data.countcommenturl=jsonObject.optString("countcommenturl");
                data.more=jsonObject.optString("more");

                JSONArray array = jsonObject.optJSONArray("news");
                if(array!=null){
                    List<News> mnews=new ArrayList<>();
                    for (int i = 0; i <array.length() ; i++) {
                        mnews.add(new News().parse(array.optJSONObject(i)));
                    }
                    data.news=mnews;
                }

                data.title=jsonObject.optString("title");

                JSONArray array2 = jsonObject.optJSONArray("topnews");
                if(array2!=null){
                    List<News> mtopnews=new ArrayList<>();
                    for (int i = 0; i <array2.length() ; i++) {
                        mtopnews.add(new News().parse(array2.optJSONObject(i)));
                    }
                    data.topnews=mtopnews;
                }


            }
            return data;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "countcommenturl='" + countcommenturl + '\'' +
                    ", more='" + more + '\'' +
                    ", news=" + news +
                    ", title='" + title + '\'' +
                    ", topnews=" + topnews +
                    '}';
        }

        public class News{
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public String id;
            public String listimage;
            public String pubdate;
            public String title;
            public String type;
            public String url;
            public String topimage;

            public  News parse(JSONObject jsonObject){
                News news=null;
                if(jsonObject!=null){
                    news=new News();
                    news.comment=jsonObject.optBoolean("comment");
                    news.commentlist=jsonObject.optString("commentlist");
                    news.id=jsonObject.optString("id");
                    news.listimage=jsonObject.optString("listimage");
                    news.pubdate=jsonObject.optString("pubdate");
                    news.title=jsonObject.optString("title");
                    news.url=jsonObject.optString("url");
                    news.topimage=jsonObject.optString("topimage");
                }
                return news;
            }


            @Override
            public String toString() {
                return "News{" +
                        "comment=" + comment +
                        ", commentlist='" + commentlist + '\'' +
                        ", commenturl='" + commenturl + '\'' +
                        ", id='" + id + '\'' +
                        ", listimage='" + listimage + '\'' +
                        ", pubdate='" + pubdate + '\'' +
                        ", title='" + title + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", topimage='" + topimage + '\'' +
                        '}';
            }
        }

    }
}
