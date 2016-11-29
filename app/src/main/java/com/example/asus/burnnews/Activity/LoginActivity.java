package com.example.asus.burnnews.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.JJFly;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import static com.example.asus.burnnews.Activity.MainActivity.jjFly2;

/**
 * Created by Asus on 2016/11/24.
 */

public class LoginActivity extends AppCompatActivity{
    ImageView qq,sina,login_fanhui;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getBaseContext());
        setContentView(R.layout.login);
        login_fanhui= (ImageView) findViewById(R.id.login_fanhui);
        button= (Button) findViewById(R.id.login_but);
        qq= (ImageView) findViewById(R.id.qq);
        sina= (ImageView) findViewById(R.id.sina);
        jiantingplay();
    }
    void jiantingplay(){
        login_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "没有此用户", Toast.LENGTH_SHORT).show();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lusanfang(QQ.NAME);
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lusanfang(SinaWeibo.NAME);
            }
        });
    }
    void lusanfang(String s){
        Platform platform= ShareSDK.getPlatform(getBaseContext(), s);
        platform.SSOSetting(false);
        platform.showUser(null);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                SharedPreferences sp= getSharedPreferences("sixfly", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =sp.edit();
                editor.putString("tx",platform.getDb().getUserIcon());
                editor.putString("mz",platform.getDb().getUserName());
                editor.commit();
                jjFly2.addarr(new ArrayList<String>());
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "发生了不可描述的错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        if (platform.isValid()){
            platform.removeAccount();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       ShareSDK.stopSDK();
    }
}
