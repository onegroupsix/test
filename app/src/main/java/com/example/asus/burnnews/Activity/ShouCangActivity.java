package com.example.asus.burnnews.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.burnnews.Adapter.MySCRAdapter;
import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.DiaoBaoLe;
import com.example.asus.burnnews.bean.JsonBigBrother;
import com.example.asus.burnnews.db.SQLHelp;

import java.util.ArrayList;

/**
 * Created by Asus on 2016/11/23.
 */

public class ShouCangActivity extends AppCompatActivity{
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mySCRAdapter.notifyDataSetChanged();
        }
    };
    MySCRAdapter mySCRAdapter;
    ArrayList<JsonBigBrother> arrayList;
    RecyclerView shoucang_rv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myshoucang);
        shoucang_rv= (RecyclerView) findViewById(R.id.shoucang_rv);
        SQLHelp sqlHelp =new SQLHelp(this);
        arrayList=sqlHelp.QiangLu();
        DiaoBaoLe diaoBaoLe = new DiaoBaoLe() {
            @Override
            public void boom() {
                Message message= new Message();
                message.what=0;
                handler.sendMessage(message);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        shoucang_rv.setLayoutManager(linearLayoutManager);
        shoucang_rv.setItemAnimator(new DefaultItemAnimator());
        mySCRAdapter =new MySCRAdapter(this,arrayList,diaoBaoLe);
        shoucang_rv.setAdapter(mySCRAdapter);

    }
}
