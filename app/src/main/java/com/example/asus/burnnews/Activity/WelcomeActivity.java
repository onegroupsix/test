package com.example.asus.burnnews.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.burnnews.R;

import java.util.ArrayList;

/**
 * Created by Asus on 2016/11/15.
 */

public class WelcomeActivity extends AppCompatActivity{
    ViewPager wel_vp;
    View view1,view2,view3;
    ArrayList<View> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        wel_vp = (ViewPager) findViewById(R.id.wel_vp);
        SharedPreferences sp = getSharedPreferences("sixfly", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.getString("屌勒", "MDZZ").equals("已经屌过了")){
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.welcome1, null);
            view2 = inflater.inflate(R.layout.welcome2, null);
            view3 = inflater.inflate(R.layout.welcome3, null);
            arrayList = new ArrayList<>();
            arrayList.add(view1);
            arrayList.add(view2);
            arrayList.add(view3);
            //监听，点击就跳
            arrayList.get(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            PagerAdapter pagerAdapter = new PagerAdapter() {
                @Override
                public int getCount() {
                    return arrayList.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(arrayList.get(position));
                    return arrayList.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(arrayList.get(position));
                }
            };
            wel_vp.setAdapter(pagerAdapter);
            editor.putString("屌勒","已经屌过了");
            editor.commit();

        }
    }
}
