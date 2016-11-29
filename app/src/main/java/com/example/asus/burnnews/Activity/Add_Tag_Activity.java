package com.example.asus.burnnews.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.FlowLayout;
import com.example.asus.burnnews.bean.JJFly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Asus on 2016/11/21.
 */

public class Add_Tag_Activity extends AppCompatActivity{
    JJFly jjFly=MainActivity.jjFly;
    LayoutInflater mInflater;
    FlowLayout idFlowlayout1;
    FlowLayout idFlowlayout2;
    ArrayList<String> okarr=new ArrayList<>(),zongarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tag);
        idFlowlayout1= (FlowLayout) findViewById(R.id.id_flowlayout1);
        idFlowlayout2= (FlowLayout) findViewById(R.id.id_flowlayout2);
        JiHeGoGoGo();
        Intent intent = getIntent();
        for (String s:intent.getStringArrayListExtra("已添加")){
            okarr.add(s);
        }
        mInflater = LayoutInflater.from(this);
        initFlowlayout2();
    }

    public void initFlowlayout2() {
        for (int i = 0; i < zongarr.size(); i++) {
            final RelativeLayout rl2 = (RelativeLayout) mInflater.inflate(R.layout.flow_layout, idFlowlayout2, false);
            TextView tv2 = (TextView) rl2.findViewById(R.id.tv);
            tv2.setText(zongarr.get(i));
            rl2.setTag(i);
            idFlowlayout2.addView(rl2);
            rl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) v.getTag();
                    addViewToFlowlayout1(i);
                    okarr.add(zongarr.get(i));
                    rl2.setBackgroundResource(R.drawable.flow_layout_disable_bg);
                    rl2.setClickable(false);
                }
            });
            for (String s:okarr){
                if (s.equals(zongarr.get(i))){
                    addViewToFlowlayout1(i);
                    rl2.setBackgroundResource(R.drawable.flow_layout_disable_bg);
                    rl2.setClickable(false);
                }
            }
        }
    }
    public void addViewToFlowlayout1(int i){
        RelativeLayout rl1 = (RelativeLayout) mInflater.inflate(R.layout.flow_layout, idFlowlayout1, false);
        ImageView iv = (ImageView) rl1.findViewById(R.id.iv);
        iv.setVisibility(View.VISIBLE);
        TextView tv1 = (TextView) rl1.findViewById(R.id.tv);
        tv1.setText(zongarr.get(i));
        rl1.setTag(i);
        idFlowlayout1.addView(rl1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                idFlowlayout1.removeView(v);
                ArrayList<String> temp = new ArrayList<String>();
                for (String s : okarr){
                    if (zongarr.get(i).equals(s)){
                        temp.add(s);
                    }
                }
                for (String s:temp){
                    okarr.remove(s);
                }
                View view = idFlowlayout2.getChildAt(i);
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.flow_layout_bg);
            }
        });
    }
    private void JiHeGoGoGo(){
        zongarr=new ArrayList<>();
        zongarr.add("国内焦点");
        zongarr.add("国际焦点");
        zongarr.add("军事焦点");
        zongarr.add("财经焦点");
        zongarr.add("互联网焦点");
        zongarr.add("房产焦点");
        zongarr.add("汽车焦点");
        zongarr.add("体育焦点");
        zongarr.add("娱乐焦点");
        zongarr.add("游戏焦点");
        zongarr.add("教育焦点");
        zongarr.add("女人焦点");
        zongarr.add("科技焦点");
        zongarr.add("社会焦点");
        zongarr.add("国内最新");
        zongarr.add("国际最新");
        zongarr.add("军事最新");
        zongarr.add("财经最新");
        zongarr.add("电影最新");
        zongarr.add("娱乐最新");
    }

    @Override
    protected void onStop() {
        super.onStop();
        jjFly.addarr(okarr);
        SharedPreferences sp=getApplicationContext().getSharedPreferences("sixfly", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        Set<String> set = new HashSet<String>();
        for (String s:okarr){
            set.add(s);
        }
        editor.putStringSet("集合",set);
        editor.commit();
    }
}
