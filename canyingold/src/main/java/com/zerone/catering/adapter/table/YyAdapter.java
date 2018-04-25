package com.zerone.catering.adapter.table;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


import com.zerone.catering.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class YyAdapter extends BaseAdapter {
    private Context con;
    private List<Map<String, Object>> list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;
    private Handler handler=null;

    public YyAdapter(Context context, List<Map<String, Object>> list,Handler handler) {
        this.con = context;
        this.list = list;
        this.handler=handler;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= getCount()) {
            return null;
        }
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public CheckBox item_checked_btn;
        public TextView start_date;
        public TextView start_time;
        public TextView end_date;
        public TextView end_time;
        public TextView tablename;
        public TextView showNumber;
        public Button update;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.table_yy_item, null);
            holder = new ViewHolder();
            holder.item_checked_btn = (CheckBox) convertView.findViewById(R.id.item_checked_btn);
            holder.start_date = (TextView) convertView.findViewById(R.id.start_date);
            holder.start_time = (TextView) convertView.findViewById(R.id.start_time);
            holder.end_date = (TextView) convertView.findViewById(R.id.end_date);
            holder.end_time = (TextView) convertView.findViewById(R.id.end_time);
            holder.tablename = (TextView) convertView.findViewById(R.id.tablename);
            holder.showNumber=(TextView) convertView.findViewById(R.id.showNumber);
            holder.update=(Button) convertView.findViewById(R.id.update_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String sd[] = this.list.get(position).get("datetime_st_format").toString().split(" ");
        String ed[] = this.list.get(position).get("datetime_nd_format").toString().split(" ");
        holder.item_checked_btn.setEnabled(false);
        holder.start_date.setText(sd[0]);
        holder.start_time.setText(sd[1]);
        holder.end_date.setText(ed[0]);
        holder.end_time.setText(ed[1]);
        holder.tablename.setText(this.list.get(position).get("tablename").toString());
        Log.i("URL", "getView: "+list.get(position).get("ordernumber").toString());
        holder.showNumber.setText(list.get(position).get("ordernumber").toString());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message mes=new Message();
                mes.obj=list.get(position).get("id");
                mes.what=2;
                handler.sendMessage(mes);
            }
        });
        return convertView;
    }
}
