package com.sty.listview.news.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shi Tianyi on 2017/10/29/0029.
 */

public class NewsOpenHelper extends SQLiteOpenHelper{
    public NewsOpenHelper(Context context) {
        super(context, "mynews", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table news (_id integer primary key, title varchar(200), des varchar(300), " +
                "icon_url varchar(200), news_url varchar(200), type integer, time varchar(100), comment integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
