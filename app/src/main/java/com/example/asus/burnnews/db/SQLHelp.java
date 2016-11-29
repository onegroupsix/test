package com.example.asus.burnnews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asus.burnnews.bean.JsonBigBrother;

import java.util.ArrayList;

/**
 * Created by Asus on 2016/11/22.
 */

public class SQLHelp {
    //声明数据库类对象
    MySQlite mySQlite =null;
    //声明数据库帮助类
    SQLiteDatabase db = null;
    public SQLHelp(Context context){
        mySQlite = new MySQlite(context);
        //拿到帮助类的实体
        db=mySQlite.getReadableDatabase();
    }
    public String delete(JsonBigBrother jsonBigBrother){
        String sql="select * from shoucang_attribute where tou = ?";
        String name="";
        Cursor cursor = db.rawQuery(sql,new String[]{jsonBigBrother.getTou()});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            name=cursor.getString(cursor.getColumnIndex("tou"));
            cursor.moveToNext();
        }
        if (name.equals(jsonBigBrother.getTou())){
            String del="delete from shoucang_attribute where tou = '"+jsonBigBrother.getTou()+"'";
            db.execSQL(del);
            cursor.close();
            db.close();
            return "删除成功";
        }
        else {
            return "没有此新闻";
        }
    }
    public String addshoucang(JsonBigBrother jsonBigBrother){
        String sql="select * from shoucang_attribute where tou = ?";
        String name="";
        Cursor cursor = db.rawQuery(sql,new String[]{jsonBigBrother.getTou()});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            name=cursor.getString(cursor.getColumnIndex("tou"));
            cursor.moveToNext();
        }
        if (!name.equals(jsonBigBrother.getTou())){
            String add="insert into shoucang_attribute values('"+jsonBigBrother.getTou()+"','"+jsonBigBrother.getWeb()+"','"+jsonBigBrother.getImg()+"','"+jsonBigBrother.getGaikuo()+"','"+jsonBigBrother.getDate()+"')";
            db.execSQL(add);
            cursor.close();
            return "添加成功";
        }
        else {
            return "收藏中已有此新闻";
        }
    }
    public ArrayList<JsonBigBrother> QiangLu(){
        ArrayList<JsonBigBrother> arrayList = new ArrayList<>();
        String sql="select * from shoucang_attribute";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(new JsonBigBrother(cursor.getString(cursor.getColumnIndex("tou")),cursor.getString(cursor.getColumnIndex("gaikuo")),cursor.getString(cursor.getColumnIndex("imgurl")),cursor.getString(cursor.getColumnIndex("url")),cursor.getString(cursor.getColumnIndex("date"))));
            cursor.moveToNext();
        }
        return arrayList;
    }
    public void guanbi(){
        db.close();
    }
}
