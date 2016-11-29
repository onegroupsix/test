package com.example.asus.burnnews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Asus on 2016/11/22.
 */

public class MySQlite extends SQLiteOpenHelper {
    public MySQlite(Context context) {
        super(context, "shoucang", null, 1);
    }
    //创建表格
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "create table shoucang_attribute (tou varchar(10000),url varchar(10000),imgurl varchar(10000),gaikuo varchar(10000),date varchar(10000))";
        db.execSQL(sql);
    }
    //当数据库的版本发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
