package com.example.asus.burnnews.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.JsonBigBrother;
import com.example.asus.burnnews.db.SQLHelp;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Asus on 2016/11/17.
 */

public class WebActivity extends AppCompatActivity{
    WebView webView;
    Toolbar toolbar;
    ImageView wuxing,fanhui,webfenxiang;
    SQLHelp sqlHelp;
    JsonBigBrother jsonBigBrother;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity);
        webfenxiang= (ImageView) findViewById(R.id.webfenxiang);
        fanhui= (ImageView) findViewById(R.id.webfanhui);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        wuxing= (ImageView) findViewById(R.id.wuxing);
        webView= (WebView) findViewById(R.id.webview);
        toolbar= (Toolbar) findViewById(R.id.webtuerba);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress==100){
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        sqlHelp =new SQLHelp(this);
        Intent intent=getIntent();
        String temp=intent.getStringExtra("web");
        jsonBigBrother=new JsonBigBrother(intent.getStringExtra("tou"),intent.getStringExtra("gaikuo"),intent.getStringExtra("img"),intent.getStringExtra("web"),intent.getStringExtra("date"));
        wuxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(wuxing, sqlHelp.addshoucang(jsonBigBrother), Snackbar.LENGTH_LONG).show();
            }
        });
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webfenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare2();
            }
        });
        webView.loadUrl(temp);

    }

    @Override
    protected void onStop() {
        super.onStop();
        sqlHelp.guanbi();
    }
    //分享的方法
    private void showShare2() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(jsonBigBrother.getTou());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(jsonBigBrother.getWeb());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(jsonBigBrother.getGaikuo());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(jsonBigBrother.getWeb());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("屌勒");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("BurnNews");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(jsonBigBrother.getWeb());

// 启动分享GUI
        oks.show(this);
    }
}
