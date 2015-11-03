package com.example.hyh_1.beijing_news.utils;

import android.app.AlertDialog;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片请求处理工具类
 * Created by hyh_1 on 2015/10/27.
 */
public class ImageUtil {


   public static class Buidler{

        private static Buidler buidler;
        private ImageLoader imageLoader;
        private Buidler(){

            imageLoader = ImageLoader.getInstance();
        }
        public static Buidler newInstance(){
            if(buidler==null){
                synchronized (Buidler.class){
                    if(buidler==null){
                        buidler=new Buidler();
                    }
                }
            }
            return buidler;
        }


        public Buidler loadImage(){
            //imageLoader.di
            return this;
            //ImageDownloader.Scheme.FILE.wrap()
          /*  ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder()
                    .memoryCache(new MemoryCache() {
                    })
                    .diskCache(new DiskCache() {
                        @Override
                        public File getDirectory() {
                            return null;
                        }

                        @Override
                        public File get(String s) {
                            return null;
                        }

                        @Override
                        public boolean save(String s, InputStream inputStream, IoUtils.CopyListener copyListener) throws IOException {
                            return false;
                        }

                        @Override
                        public boolean save(String s, Bitmap bitmap) throws IOException {
                            return false;
                        }

                        @Override
                        public boolean remove(String s) {
                            return false;
                        }

                        @Override
                        public void close() {

                        }

                        @Override
                        public void clear() {

                        }
                    })
                    .build();*/
        }

    }
}
