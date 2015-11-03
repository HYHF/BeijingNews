package com.example.hyh_1.beijing_news.domain;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by hyh_1 on 2015/11/1.
 */
public class MData2 {
    private boolean more_available;
    private List<ItemsEntity> items;

    public void setMore_available(boolean more_available) {
        this.more_available = more_available;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public boolean getMore_available() {
        return more_available;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }


    @Override
    public String toString() {
        return "MData2{" +
                "more_available=" + more_available +
                ", items=" + items +
                '}';
    }

    /*---------------------------------------------------------------------*/
    public  class ItemsEntity {

        public int id;
        public String created_at;
        public String type;
        public String type_display;
        public String title;
        public String text;
        public String text_html;
        public String category;
        public String category_display;
        public UserEntity user;
        public Object reply_to;
        public boolean user_has_liked;
        public boolean user_can_delete;
        public int likes__count;
        public int replies__count;
        public String share_url;
        public List<String> images;
        public List<TagsEntity> tags;

        public ItemsEntity pare(JSONObject jsonObject){

            if(jsonObject!=null){
                this.id=jsonObject.optInt("id");
                this.created_at=jsonObject.optString("created_at");
                this.type=jsonObject.optString("type");
                this.type_display=jsonObject.optString("type_display");
                this.title=jsonObject.optString("title");
                this.text=jsonObject.optString("text");
                this.text_html=jsonObject.optString("text_html");
                this.category=jsonObject.optString("category");
                this.category_display=jsonObject.optString("category_display");
                //this.user=

            }

            return this;
        }



        @Override
        public String toString() {
            return "ItemsEntity{" +
                    "id=" + id +
                    ", created_at='" + created_at + '\'' +
                    ", type='" + type + '\'' +
                    ", type_display='" + type_display + '\'' +
                    ", title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    ", text_html='" + text_html + '\'' +
                    ", category='" + category + '\'' +
                    ", category_display='" + category_display + '\'' +
                    ", user=" + user +
                    ", reply_to=" + reply_to +
                    ", user_has_liked=" + user_has_liked +
                    ", user_can_delete=" + user_can_delete +
                    ", likes__count=" + likes__count +
                    ", replies__count=" + replies__count +
                    ", share_url='" + share_url + '\'' +
                    ", images=" + images +
                    ", tags=" + tags +
                    '}';
        }

        /*-------------------------------------------------------------------------*/

        public  class UserEntity {
            public int id;
            public String first_name;
            public String tagline;
            public String profile_image;
            public String share_url;

            public UserEntity pase(JSONObject jsonObject){
                if(jsonObject!=null){

                    this.id=jsonObject.optInt("id");
                    this.first_name=jsonObject.optString("first_name");
                    this.tagline=jsonObject.optString("tagline");
                    this.profile_image=jsonObject.optString("profile_image");
                    this.share_url=jsonObject.optString("share_url");
                }

                return this;
            }

            @Override
            public String toString() {
                return "UserEntity{" +
                        "id=" + id +
                        ", first_name='" + first_name + '\'' +
                        ", tagline='" + tagline + '\'' +
                        ", profile_image='" + profile_image + '\'' +
                        ", share_url='" + share_url + '\'' +
                        '}';
            }
        }

        /*------------------------------------------------------------------------------------*/
        public  class TagsEntity {

            public int id;
            public String title;

            public TagsEntity pase(JSONObject jsonObject){
                if(jsonObject!=null){
                    this.id=jsonObject.optInt("id");
                    this.title=jsonObject.optString("title");
                }
                return this;
            }
            @Override
            public String toString() {
                return "TagsEntity{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        '}';
            }
        }
    }
}
