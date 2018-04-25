package com.zerone.catering.adapter.table;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zerone.catering.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/3.
 */

public class TableAdapter extends BaseAdapter{
    private Context con;
    private List<Map<String,Object>> list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;
    private int checkedposition;

    public TableAdapter(Context context,List<Map<String,Object>> list){
        this.con = context;
        this.list = list;
        inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checkedposition = -99;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if(position>=getCount()){
            return null;
        }
        return position;
    }

    public int getCheckedposition(){
        return this.checkedposition;
    }

    public void setCheckedposition(int checkedposition){
        this.checkedposition = checkedposition;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder{
        public TextView tablename;
        public TextView maxseat;
        public TextView table_time;
        public TextView using;
        public ImageView yuyue_icon_1;
        public ImageView yuyue_icon_2;
        public LinearLayout table_item_box_zhuti;
        public RelativeLayout table_item_box;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.table_item, null);
            holder = new ViewHolder();
            holder.tablename = (TextView) convertView.findViewById(R.id.tablename);
            holder.maxseat = (TextView) convertView.findViewById(R.id.maxseat);
            holder.table_time = (TextView) convertView.findViewById(R.id.table_time);
            holder.using = (TextView) convertView.findViewById(R.id.using);
            holder.yuyue_icon_1 = (ImageView) convertView.findViewById(R.id.yuyue_icon_1);
            holder.yuyue_icon_2 = (ImageView) convertView.findViewById(R.id.yuyue_icon_2);
            holder.table_item_box_zhuti = (LinearLayout) convertView.findViewById(R.id.table_item_box_zhuti);
            holder.table_item_box = (RelativeLayout) convertView.findViewById(R.id.table_item_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tablename.setText(this.list.get(position).get("tablename").toString());
        holder.maxseat.setText(this.list.get(position).get("maxseat").toString()+"座");

        if(position==this.getCheckedposition()){
            holder.table_item_box.setBackgroundColor(Color.parseColor("#efeeee"));
        }else{
            holder.table_item_box.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        if("0".equals(this.list.get(position).get("using").toString())){
            holder.using.setText("空闲中");
            holder.table_item_box_zhuti.setBackgroundResource(R.drawable.table_item_bg);
        }else if("1".equals(this.list.get(position).get("using").toString())){
            holder.using.setText("未满员");
            holder.using.setTextColor(Color.RED);
            holder.table_item_box_zhuti.setBackgroundResource(R.drawable.table_item_bg_full);
        }else{
            holder.using.setText("已满员");
            holder.table_item_box_zhuti.setBackgroundResource(R.drawable.table_item_bg_full);
        }
//        this.list.get(position).get("time_st").toString()
        if("0".equals(this.list.get(position).get("ydstatus").toString())){
             //1109
//            Log.d("TableAdapter", "桌子时间问题");
            holder.table_time.setText("");

        }else{
//            Log.d("TableAdapter", "桌子时间问题：：0000000000000000000000000000000000");
            holder.table_time.setText(this.list.get(position).get("time_st").toString());

        }

        if("0".equals(this.list.get(position).get("ydstatus").toString())){
            holder.yuyue_icon_1.setVisibility(View.GONE);
            holder.yuyue_icon_2.setVisibility(View.GONE);
        }else if("1".equals(this.list.get(position).get("ydstatus").toString())){
            holder.yuyue_icon_1.setVisibility(View.VISIBLE);
            holder.yuyue_icon_2.setVisibility(View.GONE);
        }else{
            holder.yuyue_icon_1.setVisibility(View.GONE);
            holder.yuyue_icon_2.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
