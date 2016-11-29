package com.example.asus.burnnews.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.burnnews.Activity.MainActivity;
import com.example.asus.burnnews.Activity.WebActivity;
import com.example.asus.burnnews.Adapter.RecyclerAdapter;
import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.JJFly;
import com.example.asus.burnnews.bean.JsonBigBrother;
import com.example.asus.burnnews.bean.RecycleViewGridDivier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.R.attr.bitmap;


public class Main_Fragment extends Fragment {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                sr.setRefreshing(true);
            }
            else {
                sr.setRefreshing(false);
                recyclerAdapter.notifyDataSetChanged();
            }
        }
    };
    int page=1;
    Context context;
    String leibie;
    boolean hehe=true;
    ArrayList<JsonBigBrother> arrayList=new ArrayList<JsonBigBrother>();
    RecyclerAdapter recyclerAdapter;
    int temp;
    String fuck=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    RecyclerView recyclerView;
    SwipeRefreshLayout sr;
    LinearLayoutManager layoutManager;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //绑定布局
        View view=inflater.inflate(R.layout.fragmentlayout,container,false);
        //Recyclerview各种嗨
        recyclerView= (RecyclerView) view.findViewById(R.id.rv);
        sr= (SwipeRefreshLayout) view.findViewById(R.id.sr);
        Bundle bundle = getArguments();
        leibie=bundle.getString("头");
        //酷炫的web跳转
        RecyclerAdapter.Tiao tiao=new RecyclerAdapter.Tiao() {
            @Override
            public void zouni(int p) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("web",arrayList.get(p).getWeb());
                intent.putExtra("tou",arrayList.get(p).getTou());
                intent.putExtra("gaikuo",arrayList.get(p).getGaikuo());
                intent.putExtra("date",arrayList.get(p).getDate());
                intent.putExtra("img",arrayList.get(p).getImg());
                startActivity(intent);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                fuck=request("http://apis.baidu.com/showapi_open_bus/channel_news/search_news",leibie,page+"");
                arrayList=getJsonBB(fuck);
                recyclerAdapter.addArraylist(arrayList);
                Message message1 = new Message();
                message1.what=0;
                handler.sendMessage(message1);
            }
        }).start();
        hehe=true;
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new RecycleViewGridDivier(context));//设置item外框
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new RecyclerAdapter(context,arrayList,tiao);
        recyclerView.setAdapter(recyclerAdapter);

        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        arrayList=getJsonBB(request("http://apis.baidu.com/showapi_open_bus/channel_news/search_news",leibie,"1"));
                        recyclerAdapter.addArraylist(arrayList);
                        Message message = new Message();
                        message.what=0;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && temp + 1 == recyclerAdapter.getItemCount()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what=1;
                                handler.sendMessage(message);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                page+=1;
                                arrayList.addAll(getJsonBB(request("http://apis.baidu.com/showapi_open_bus/channel_news/search_news",leibie,page+"")));
                                recyclerAdapter.addArraylist(arrayList);
                                Message messaget = new Message();
                                message.what=0;
                                handler.sendMessage(messaget);
                            }
                        }).start();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                temp = layoutManager.findLastVisibleItemPosition();
            }
        });
        return view;
    }
    public String request(String httpUrl, String httpArg,String page) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sb = new StringBuffer();
        httpUrl = httpUrl + "?" +"channelName="+ httpArg+"&page="+page;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey",  "ff6734b7320a6c9905e7aa8bb6b6fcaf");
            connection.connect();
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
                sb.append("\r\n");
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //得到数据源
    public ArrayList<JsonBigBrother> getJsonBB(String json){
        ArrayList<JsonBigBrother> arrayList = new ArrayList<>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            jsonObject=jsonObject.getJSONObject("showapi_res_body");
            jsonObject=jsonObject.getJSONObject("pagebean");
            JSONArray jsonArray =jsonObject.getJSONArray("contentlist");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                JSONArray jsonArray1=jsonObject1.getJSONArray("imageurls");
                JSONObject jsonObject2=null;
                if (jsonArray1.length()!=0) {
                    jsonObject2 = (JSONObject) jsonArray1.get(0);
                }
                if (jsonObject2!=null) {
                    arrayList.add(new JsonBigBrother(jsonObject1.getString("title"), jsonObject1.getString("desc"), jsonObject2.getString("url"), jsonObject1.getString("link"),jsonObject1.getString("pubDate")));
                }
                Log.e("Main_Fragment", arrayList.size() + "");
            }
        } catch (JSONException e) {
            return new ArrayList<>();
        }
        hehe=false;
        return arrayList;
    }
}
