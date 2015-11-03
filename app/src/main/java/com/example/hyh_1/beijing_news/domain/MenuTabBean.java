package com.example.hyh_1.beijing_news.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh_1 on 2015/10/28.
 */
public class MenuTabBean {

    public List<DataBean> data;
    public List<MenuTabExtendBean> extend;
    public String retcode;

    public MenuTabBean init(JSONObject jsonObject) throws JSONException {

        MenuTabBean menuTabBean=null;
        if(jsonObject!=null){
            menuTabBean = new MenuTabBean();

            JSONArray array = jsonObject.optJSONArray("data");
            if(array!=null){
                List<DataBean> mdata=new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    mdata.add(new DataBean().init(array.optJSONObject(i)));
                }

                menuTabBean.data=mdata;
            }

            menuTabBean.retcode=jsonObject.optString("retcode");
        }



        return menuTabBean;
    }

    public class DataBean {

        public List<Children> children;
        public String id;
        public String title;
        public String type;
        public String url;


        public DataBean init(JSONObject jsonObject) {
            DataBean dataBean = null;
            if (jsonObject != null) {
                dataBean = new DataBean();

                //解析children
                JSONArray array = jsonObject.optJSONArray("children");
                if (array != null) {
                    List<Children> childrens = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        childrens.add(new Children().init(array.optJSONObject(i)));
                    }

                    dataBean.children = childrens;
                }

                dataBean.id = jsonObject.optString("id");
                dataBean.title = jsonObject.optString("title");
                dataBean.type = jsonObject.optString("type");
                dataBean.url = jsonObject.optString("url");
            }

            return dataBean;
        }

        public class Children {
            public String id;
            public String title;
            public String type;
            public String url;

            public Children init(JSONObject jsonObject) {
                Children children = null;

                if (jsonObject != null) {
                    children = new Children();

                    children.id = jsonObject.optString("id");
                    children.title = jsonObject.optString("title");
                    children.type = jsonObject.optString("type");
                    children.url = jsonObject.optString("url");
                }
                return children;
            }
        }
    }

    class MenuTabExtendBean {
        String extra;
    }
}
