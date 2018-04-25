package com.zerone.catering.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.HuiYuanInfoBean;
import com.zerone.catering.domain.VIPBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class VIPAdapter extends BaseAdapter {
    private Context context;
    private List<VIPBean.DataBean> list;

    public VIPAdapter(Context context, List<VIPBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_viplistitems,null);
            holder = new ViewHolder();
            holder.levelId=(TextView) convertView.findViewById(R.id.levelid);
            holder.leveName=(TextView) convertView.findViewById(R.id.levelname);
            holder.levePrice=(TextView) convertView.findViewById(R.id.levelprice);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
            holder.leveName.setText(list.get(position).getLevelname());
            holder.levePrice.setText("￥"+list.get(position).getLevelprice());
            holder.levelId.setText(list.get(position).getLevelid());

           return convertView;
    }

    class  ViewHolder {
        private  TextView  levelId;
        private  TextView  leveName;
        private  TextView  levePrice;
    }
}
