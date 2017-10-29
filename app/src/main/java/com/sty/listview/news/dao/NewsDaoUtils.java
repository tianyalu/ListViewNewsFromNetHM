package com.sty.listview.news.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sty.listview.news.bean.NewsBean;

import java.util.ArrayList;

/**
 * Created by Shi Tianyi on 2017/10/29/0029.
 */

public class NewsDaoUtils {
    private NewsOpenHelper newsOpenHelper;

    public NewsDaoUtils(Context context){
        //创建一个帮助类对象
        newsOpenHelper = new NewsOpenHelper(context);
    }

    //删除数据库中缓存的旧数据
    public void delete(){
        //通过帮助类对象获取一个数据库操作对象
        SQLiteDatabase db = newsOpenHelper.getReadableDatabase();
        db.delete("news", null, null);
        db.close();
    }

    //向数据库中添加新闻数据
    public void saveNews(ArrayList<NewsBean> list){
        //通过帮助类对象获取一个数据库操作对象
        SQLiteDatabase db = newsOpenHelper.getReadableDatabase();
        for(NewsBean newsBean : list){
            ContentValues values = new ContentValues();
            values.put("_id", newsBean.id);
            values.put("title", newsBean.title);
            values.put("des", newsBean.des);
            values.put("icon_url", newsBean.iconUrl);
            values.put("news_url", newsBean.newsUrl);
            values.put("type", newsBean.type);
            values.put("time", newsBean.time);
            values.put("comment", newsBean.comment);

            db.insert("news", null, values);
        }
        db.close();
    }
    //从数据库中获取缓存的数据
    public ArrayList<NewsBean> getNews(){
        ArrayList<NewsBean> list = new ArrayList<>();
        //通过帮助类获取一个数据库操作对象
        SQLiteDatabase db = newsOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news", null); //查询获取数据
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){

                NewsBean newsBean = new NewsBean();
                newsBean.id = cursor.getInt(0);
                newsBean.title = cursor.getString(1);
                newsBean.des = cursor.getString(2);
                newsBean.iconUrl = cursor.getString(3);
                newsBean.newsUrl = cursor.getString(4);
                newsBean.type = cursor.getInt(5);
                newsBean.time = cursor.getString(6);
                newsBean.comment = cursor.getInt(7);

                list.add(newsBean);
            }
        }
        db.close();
        cursor.close();

        return list;
    }
}
