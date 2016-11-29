package com.example.asus.burnnews.bean;

import android.graphics.Bitmap;

/**
 * Created by Asus on 2016/11/17.
 */

public class JsonBigBrother {
    private String tou;
    private String gaikuo;
    private String img;
    private String web;
    private String date;

    public JsonBigBrother(String tou, String gaikuo, String img, String web, String date) {
        this.tou = tou;
        this.gaikuo = gaikuo;
        this.img = img;
        this.web = web;
        this.date = date;
    }

    public String getTou() {
        return tou;
    }

    public void setTou(String tou) {
        this.tou = tou;
    }

    public String getGaikuo() {
        return gaikuo;
    }

    public void setGaikuo(String gaikuo) {
        this.gaikuo = gaikuo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
