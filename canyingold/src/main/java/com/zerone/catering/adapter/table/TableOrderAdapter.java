package com.zerone.catering.adapter.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zerone.catering.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */

public class TableOrderAdapter extends BaseAdapter {
    private Context con;
    private List<Map<String,Object>> list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;


    public TableOrderAdapter(Context context, List<Map<String,Object>> list){
        this.con = context;
        this.list = list;
        inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


    public class ViewHolder{
        public TextView tablename;
        public TextView ordersn;
        public TextView datenum;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.table_order_item, null);
            holder = new ViewHolder();
            holder.tablename = (TextView) convertView.findViewById(R.id.tablename);
            holder.ordersn = (TextView) convertView.findViewById(R.id.ordersn);
            holder.datenum = (TextView) convertView.findViewById(R.id.datenum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tablename.setText(this.list.get(position).get("tablename").toString());
        holder.ordersn.setText(this.list.get(position).get("ordersn").toString());
        holder.datenum.setText(this.list.get(position).get("datenum").toString());
        return convertView;
    }

}
