package com.zerone.catering.adapter.table;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerone.catering.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */

public class RoomAdapter extends BaseAdapter {
    private Context con;
    private List<Map<String,Object>> list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;
    private int checkedposition;
    private int maxsize;

    public RoomAdapter(Context context,List<Map<String,Object>> list,int maxsize){
        this.con = context;
        this.list = list;
        inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checkedposition=0;
        this.maxsize = maxsize;
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getCheckedposition(){
        return this.checkedposition;
    }

    public void setCheckedposition(int checkedposition){
        this.checkedposition = checkedposition;
    }

    public class ViewHolder{
        public TextView roomname;
        public LinearLayout roombg;
        public ImageView selectedImg;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.room_item, null);
            holder = new ViewHolder();
            holder.roomname = (TextView) convertView.findViewById(R.id.roomname);
            holder.roombg = (LinearLayout) convertView.findViewById(R.id.roombg);

            holder.selectedImg = (ImageView) convertView.findViewById(R.id.selectedImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position==this.checkedposition){
            holder.roomname.setTextColor(Color.parseColor("#f8b02a"));
            holder.selectedImg.setVisibility(View.VISIBLE);
        }else {
            holder.roomname.setTextColor(Color.rgb(91,91,91));
            holder.selectedImg.setVisibility(View.GONE);
        }
        holder.roomname.setText(this.list.get(position).get("roomname").toString());
        return convertView;
    }

}
