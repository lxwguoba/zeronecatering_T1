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

public class TableGoodsAdapter extends BaseAdapter {
    private Context con;
    private List<Map<String,Object>> list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;


    public TableGoodsAdapter(Context context,List<Map<String,Object>> list){
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
        public TextView goods_title;
        public TextView goods_num;
        public TextView goods_price;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.table_goodsinfo_item, null);
            holder = new ViewHolder();
            holder.goods_title = (TextView) convertView.findViewById(R.id.goods_title);
            holder.goods_num = (TextView) convertView.findViewById(R.id.goods_num);
            holder.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = this.list.get(position).get("title").toString();
         if (title.length()>9){
             holder.goods_title.setText(title.substring(0,7)+"...");
         }else {
             holder.goods_title.setText(title);
         }


        holder.goods_price.setText(this.list.get(position).get("price").toString());
        holder.goods_num.setText(this.list.get(position).get("total").toString());
        return convertView;
    }

}
