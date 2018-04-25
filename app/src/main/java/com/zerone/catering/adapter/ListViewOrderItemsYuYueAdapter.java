package com.zerone.catering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zerone.catering.R;
import com.zerone.catering.domain.YuYueOrderListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOrderItemsYuYueAdapter extends BaseAdapter {
    private Context context;
    private   List<YuYueOrderListBean.DataBean> list;

    public ListViewOrderItemsYuYueAdapter(Context context,  List<YuYueOrderListBean.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_ordernumberlistitems_yuyue,null);
            holder = new ViewHolder();
            holder.orderNum = (TextView) convertView.findViewById(R.id.orderNumber);
            convertView.setTag(holder);
            } else {
                   holder = (ViewHolder) convertView.getTag();
            }
        if (list.get(position).getIn_other_table()==1){
            holder.orderNum.setText(list.get(position).getOrdersn()+"[已预约]");
        }else if (list.get(position).getIn_other_table()==0){
            holder.orderNum.setText(list.get(position).getOrdersn());
        }

        return convertView;
    }

    class  ViewHolder {
        private TextView  orderNum;
    }
}
