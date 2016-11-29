package com.example.asus.burnnews.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.liao.GifView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.asus.burnnews.Adapter.FPAdapter;
import com.example.asus.burnnews.Adapter.MySCRAdapter;
import com.example.asus.burnnews.Adapter.RecyclerAdapter;
import com.example.asus.burnnews.Fragment.Main_Fragment;
import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.JJFly;
import com.example.asus.burnnews.bean.JiHe;
import com.example.asus.burnnews.bean.JsonBigBrother;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0) {
                biaoqian = (ArrayList<String>) msg.obj;
                setpager();
            }
            else{
                SharedPreferences sp= getSharedPreferences("sixfly", Context.MODE_PRIVATE);
                Picasso.with(MainActivity.this).load(sp.getString("tx","")).into(touxiang);
                zhuxiao.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
            }
        }
    };
    static JJFly jjFly,jjFly2;
    BottomNavigationBar bottomNavigationBar;
    Toolbar toolbar;
    FPAdapter fpAdapter;
    DrawerLayout dl;
    GifView niupigu;
    ViewPager mian_vp;
    TabLayout main_tl;
    ArrayList<Fragment> ment;
    ArrayList<String> biaoqian;
    LinearLayout forus,main_redian,main_sousuo,main_zixun,myshoucang,sousuol;
    ImageView main_add,fenxiang,touxiang;
    TextView login,zhuxiao;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bid();
        dibu();
        settx();
        jianting();
        happytoolbar();
        setchouti();
        Niupigu();
        vft();
        setfm();
        setpager();
        sousuojiemian();
        //回调Play
        jjFly=new JJFly() {
            @Override
            public void addarr(ArrayList<String> arrayList) {
                Message message = new Message();
                message.what=0;
                message.obj=arrayList;
                handler.sendMessage(message);
            }
        };
        jjFly2=new JJFly() {
            @Override
            public void addarr(ArrayList<String> arrayList) {
                Message message = new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        };
    }
    //绑定ID
    private void Bid(){
        editText= (EditText) findViewById(R.id.ed);
        button= (Button) findViewById(R.id.shousuojian);
        sousuol= (LinearLayout) findViewById(R.id.sousuol);
        zhuxiao= (TextView) findViewById(R.id.zhuxiao);
        touxiang= (ImageView) findViewById(R.id.touxiang);
        login= (TextView) findViewById(R.id.login);
        fenxiang= (ImageView) findViewById(R.id.fenxiang);
        myshoucang= (LinearLayout) findViewById(R.id.myshoucang);
        main_add= (ImageView) findViewById(R.id.main_add);
        main_tl= (TabLayout) findViewById(R.id.main_tl);
        dl= (DrawerLayout) findViewById(R.id.dl);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        niupigu= (GifView) findViewById(R.id.niupigu);
        mian_vp= (ViewPager) findViewById(R.id.main_vp);
        forus= (LinearLayout) findViewById(R.id.forus);
        main_zixun= (LinearLayout) findViewById(R.id.main_zixun);
        main_redian= (LinearLayout) findViewById(R.id.main_redian);
        main_sousuo= (LinearLayout) findViewById(R.id.main_sousuo);
    }
    //兔儿粑
    private void happytoolbar(){
        setSupportActionBar(toolbar);
    }
    //drawerlayout相关
    private void setchouti(){
        android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle=new android.support.v7.app.ActionBarDrawerToggle(this,dl,toolbar,R.string.cao,R.string.cao);
        actionBarDrawerToggle.syncState();//建立抽屉与Toolbar之间的联动
        dl.addDrawerListener(actionBarDrawerToggle);//对抽屉增加监听
    }
    //GIF
    private void Niupigu(){
        niupigu.setGifImage(R.drawable.niupigu);
        niupigu.setShowDimension(400,410);
        niupigu.setGifImageType(GifView.GifImageType.COVER);
    }
    //tablayout
    private void vft(){
        biaoqian=new ArrayList<>();
        SharedPreferences sp=getSharedPreferences("sixfly",Context.MODE_PRIVATE);
        HashSet<String> hashSet= (HashSet<String>) sp.getStringSet("集合",new HashSet<String>());
        if (hashSet.size()!=0){
            for (String s:hashSet){
                biaoqian.add(s);
            }
        }
        else {
            biaoqian.add("军事焦点");
            biaoqian.add("汽车焦点");
            biaoqian.add("体育焦点");
        }
        main_tl.setupWithViewPager(mian_vp);
    }
    //监听
    private void jianting(){
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
// 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//进行搜索操作的方法，在该方法中可以加入Editext的非空判断
                    search();
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_Fragment main_fragment =new Main_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("头",editText.getText().toString());
                main_fragment.setArguments(bundle);
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.sousuol,main_fragment,"Main_Fragment");
                transaction.commit();
            }
        });
        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp= getSharedPreferences("sixfly", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =sp.edit();
                editor.putString("tx",null);
                editor.commit();
                touxiang.setImageResource(R.mipmap.touxiang);
                zhuxiao.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        myshoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShouCangActivity.class);
                startActivity(intent);
            }
        });
        main_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_Tag_Activity.class);
                intent.putExtra("已添加",biaoqian);
                startActivity(intent);
            }
        });
        forus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForUsActivity.class);
                startActivity(intent);
            }
        });
        mian_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //欢乐的fragment和viewpager系列
    private void setpager(){
        ment=new ArrayList<Fragment>();
        for (int i = 0; i < biaoqian.size(); i++) {
            Main_Fragment nmf = new Main_Fragment();
            ment.add(nmf);
            Bundle bundle = new Bundle();
            bundle.putString("头", biaoqian.get(i));
            nmf.setArguments(bundle);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.commitAllowingStateLoss();
        fpAdapter = new FPAdapter(fragmentManager,ment,biaoqian);
        mian_vp.setAdapter(fpAdapter);
        main_tl.setupWithViewPager(mian_vp);
    }
    //fragment各种嗨
    private void setfm(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Main_Fragment main_fragment = new Main_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("头","国际焦点");
        main_fragment.setArguments(bundle);
        transaction.replace(R.id.main_redian,main_fragment);
        transaction.commitAllowingStateLoss();
        main_redian.setVisibility(View.GONE);
        main_sousuo.setVisibility(View.GONE);
    }
    //分享的方法
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("蓝瘦");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("香菇");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
    //设置头像
    private void settx(){
        SharedPreferences sp= getSharedPreferences("sixfly",Context.MODE_PRIVATE);
        if (sp.getString("tx","")==null||sp.getString("tx","")==""||sp.getString("tx","")=="0"){
            touxiang.setImageResource(R.mipmap.touxiang);
            login.setVisibility(View.VISIBLE);
            zhuxiao.setVisibility(View.GONE);
        }else {
            Picasso.with(this).load(sp.getString("tx","")).into(touxiang);
            login.setVisibility(View.GONE);
            zhuxiao.setVisibility(View.VISIBLE);
        }

    }
    //搜索界面
    void sousuojiemian(){
        //获取FragmentManager管理器
        fragmentManager = getSupportFragmentManager();
        //获取事物管理器
        transaction = fragmentManager.beginTransaction();
        Main_Fragment main_fragment =new Main_Fragment();
        Bundle bundle=new Bundle();
        bundle.putString("头","666");
        main_fragment.setArguments(bundle);
        transaction.replace(R.id.sousuol,main_fragment,"Main_Fragment");
        //提交
        transaction.commit();
    }
    //搜索功能

    private void search() {

        String searchContext = editText.getText().toString().trim();
        if (TextUtils.isEmpty(searchContext)) {
            Toast.makeText(this, "输入框为空，请输入", Toast.LENGTH_SHORT).show();
        } else {
// 调用搜索的方法
            button.callOnClick();
        }
    }
    //底部导航栏
    void dibu(){
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.diaodibu);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ss, "资讯").setActiveColorResource(R.color.bilifen))
                .addItem(new BottomNavigationItem(R.drawable.rd, "热点").setActiveColorResource(R.color.bilifen))
                .addItem(new BottomNavigationItem(R.drawable.zz, "搜索").setActiveColorResource(R.color.bilifen))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch(position){
                    case 0:
                        main_zixun.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        main_redian.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        main_sousuo.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
                switch(position){
                    case 0:
                        main_zixun.setVisibility(View.GONE);
                        break;
                    case 1:
                        main_redian.setVisibility(View.GONE);
                        break;
                    case 2:
                        main_sousuo.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}
