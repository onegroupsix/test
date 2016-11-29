package com.example.asus.burnnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.JsonBigBrother;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Asus on 2016/11/15.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
//    ArrayList<Integer> heights;
    ArrayList<JsonBigBrother> arrayList;
    Tiao tiao=null;

    public RecyclerAdapter(Context context, ArrayList<JsonBigBrother> arrayList,Tiao tiao) {
        this.context = context;
        this.arrayList = arrayList;
        this.tiao=tiao;
    }
public void addArraylist(ArrayList<JsonBigBrother> arrayList){
    this.arrayList=arrayList;
}
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0){
            View view=LayoutInflater.from(context).inflate(R.layout.danaokuoitem,parent,false);
            NaoKuo naoKuo =new NaoKuo(view);
            return naoKuo;
        }
        else {
            //绑定布局
            View view = LayoutInflater.from(context).inflate(R.layout.recycleritem, parent, false);
            //初始化优化类
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NaoKuo){
            Picasso.with(context).load(arrayList.get(position).getImg()).into(((NaoKuo)holder).imageView);
            ((NaoKuo)holder).textView.setText(arrayList.get(position).getTou());
            ((NaoKuo)holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tiao.zouni(position);
                }
            });
        }else {
            ((MyViewHolder)holder).tv1.setText(arrayList.get(position).getTou());
            ((MyViewHolder)holder).tv2.setText(arrayList.get(position).getGaikuo());
            Picasso.with(context).load(arrayList.get(position).getImg()).into(((MyViewHolder)holder).imageView);
            ((MyViewHolder)holder).dog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tiao.zouni(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
       if (position==0){
           return 0;
       }
        else {
           return 1;
       }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
//自己写的优化类
    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tv1,tv2;
        ImageView imageView;
    LinearLayout dog;
        public MyViewHolder(View itemView) {
            super(itemView);
            dog= (LinearLayout) itemView.findViewById(R.id.dog);
            tv1= (TextView) itemView.findViewById(R.id.item_biaoti);
            tv2= (TextView) itemView.findViewById(R.id.item_neirong);
            imageView= (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
    class NaoKuo extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public NaoKuo(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.naokuotu);
            textView= (TextView) itemView.findViewById(R.id.naokuozi);
        }
    }
    public interface Tiao{
        void zouni(int p);
    }
}
