package com.zerone.catering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.Worker;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOrderGuanLiAdapter extends BaseAdapter {
    private Context context;
    private List<Worker> list;

    public ListViewOrderGuanLiAdapter(Context context,  List<Worker> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_neworder_guanli_items,null);
            holder = new ViewHolder();
            holder.guanLiName= (TextView) convertView.findViewById(R.id.listview_guanli);
            convertView.setTag(holder);
            } else {
                   holder = (ViewHolder) convertView.getTag();
            }
            holder.guanLiName.setText(list.get(position).getName());
           return convertView;
    }

    class  ViewHolder {

        private TextView guanLiName;

    }
}
