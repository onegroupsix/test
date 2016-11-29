package com.example.asus.burnnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.burnnews.Activity.WebActivity;
import com.example.asus.burnnews.R;
import com.example.asus.burnnews.bean.DiaoBaoLe;
import com.example.asus.burnnews.bean.JsonBigBrother;
import com.example.asus.burnnews.db.SQLHelp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Asus on 2016/11/23.
 */

public class MySCRAdapter extends RecyclerView.Adapter<MySCRAdapter.MyViewHolder2>{
    Context context;
    ArrayList<JsonBigBrother> arrayList;
    DiaoBaoLe diaoBaoLe;

    public MySCRAdapter(Context context, ArrayList<JsonBigBrother> arrayList,DiaoBaoLe diaoBaoLe) {
        this.context = context;
        this.arrayList = arrayList;
        this.diaoBaoLe=diaoBaoLe;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.myshoucang_item,parent,false);
        MyViewHolder2 myViewHolder2 =new MyViewHolder2(view);
        return myViewHolder2;
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, final int position) {
        holder.tv1.setText(arrayList.get(position).getTou());
        holder.tv2.setText(arrayList.get(position).getGaikuo());
        holder.tv3.setText(arrayList.get(position).getDate());
        Picasso.with(context).load(arrayList.get(position).getImg()).into(((MyViewHolder2)holder).tutu);
        holder.kuangzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, WebActivity.class);

                intent.putExtra("web",arrayList.get(position).getWeb());
                intent.putExtra("tou",arrayList.get(position).getTou());
                intent.putExtra("gaikuo",arrayList.get(position).getGaikuo());
                intent.putExtra("date",arrayList.get(position).getDate());
                intent.putExtra("img",arrayList.get(position).getImg());
                context.startActivity(intent);
            }
        });
        holder.kuangzi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SQLHelp sqlHelp =new SQLHelp(context);
                sqlHelp.delete(arrayList.get(position));
                arrayList.remove(position);
                diaoBaoLe.boom();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //自己写的优化类
    class MyViewHolder2 extends  RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3;
        ImageView tutu;
        LinearLayout kuangzi;
        public MyViewHolder2(View itemView) {
            super(itemView);
            kuangzi= (LinearLayout) itemView.findViewById(R.id.kuangzi);
            tv1= (TextView) itemView.findViewById(R.id.t1);
            tv2= (TextView) itemView.findViewById(R.id.t2);
            tv3= (TextView) itemView.findViewById(R.id.t3);
            tutu= (ImageView) itemView.findViewById(R.id.tutu);
        }
    }
}
