package com.zerone.catering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.Product_Zi;

import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */

public class PopList02Adapter extends BaseAdapter {

    private Context context ;
    private List<Product_Zi> list;

    public PopList02Adapter(Context context, List<Product_Zi> list) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_xianxia_edt_items,null);
            holder = new ViewHolder();
            holder.pName_Zi= (TextView) convertView.findViewById(R.id.pName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (list.get(position).getpName_Zi()!=null){
            holder.pName_Zi.setText(list.get(position).getpName_Zi());
        }

        return convertView;
    }

    class ViewHolder {

        public TextView pName_Zi;

    }
}

