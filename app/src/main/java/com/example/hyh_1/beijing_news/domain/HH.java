package com.example.hyh_1.beijing_news.domain;

import java.util.List;

/**
 * Created by hyh_1 on 2015/11/1.
 */
public class HH {


    public boolean more_available;
    public List<ItemsEntity> items;/*---------------------------------------------------------------------*/
    public static class ItemsEntity {
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
    /*-------------------------------------------------------------------------*/

        public static class UserEntity {
            public int id;
            public String first_name;
            public String tagline;
            public String profile_image;
            public String share_url;

        }
        /*------------------------------------------------------------------------------------*/
        public static class TagsEntity {

            public int id;
            public String title;
        }
    }

}
