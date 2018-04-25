package com.zerone.catering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.FoodOrderInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOrderInfoAdapter extends BaseAdapter {
    private Context context;
    private List<FoodOrderInfoBean> list;

    public ListViewOrderInfoAdapter(Context context, List<FoodOrderInfoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list!=null){
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (list!=null){
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_listview_orderinfo,null);
            holder = new ViewHolder();
            holder.foodCount= (TextView) convertView.findViewById(R.id.foodCount);
            holder.foodName= (TextView) convertView.findViewById(R.id.foodName);
            holder.foodPrice= (TextView) convertView.findViewById(R.id.foodPrice);
            holder.optionlayout=(LinearLayout) convertView.findViewById(R.id.optionlayout);
            holder.optiontitle=(TextView) convertView.findViewById(R.id.optiontitle);
            convertView.setTag(holder);
            } else {
                   holder = (ViewHolder) convertView.getTag();
            }

        holder.foodCount.setText(list.get(position).getFoodCount()+"");
        holder.foodName.setText(list.get(position).getFoodName());
        holder.foodPrice.setText(list.get(position).getFoodPrice());

        if (list.get(position).getHasoption()==0){
            holder.optionlayout.setVisibility(View.GONE);
        }else {
            holder.optionlayout.setVisibility(View.VISIBLE);
            holder.optiontitle.setText(list.get(position).getTitle());
        }
        return convertView;

    }

    class  ViewHolder {
        private TextView foodName;
        private TextView  foodCount;
        private TextView  foodPrice;
        private LinearLayout optionlayout;
        private TextView  optiontitle;
    }
}
