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

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class HuiYuanInfoAdapter extends BaseAdapter {
    private Context context;
    private List<HuiYuanInfoBean.MemberlistBean> list;

    public HuiYuanInfoAdapter(Context context, List<HuiYuanInfoBean.MemberlistBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_neworder_huiyuan_items,null);
            holder = new ViewHolder();
            holder.layout= (LinearLayout) convertView.findViewById(R.id.layout);
            holder.userId= (TextView) convertView.findViewById(R.id.huiyuan_ID);
            holder.userName=(TextView) convertView.findViewById(R.id.huiyuan_name);
            holder.userPhone=(TextView) convertView.findViewById(R.id.huiyuan_phone);
            holder.userJiFen=(TextView) convertView.findViewById(R.id.huiyuan_jifen);
            holder.userYuEr=(TextView) convertView.findViewById(R.id.huiyuan_yue);
            holder.userInfo=(TextView) convertView.findViewById(R.id.huiyuan_beizhu);
            holder.userNum= (TextView) convertView.findViewById(R.id.huiyuan_num);
            convertView.setTag(holder);
            } else {
                   holder = (ViewHolder) convertView.getTag();
            }
              if (position%2==0){
                  holder.layout.setBackgroundColor(Color.rgb(255,255,255));
              }else {
                  holder.layout.setBackgroundColor(Color.rgb(245,245,245));
              }

             holder.userNum.setText((position+1)+"");
             holder.userId.setText(list.get(position).getId());
             holder.userName.setText(list.get(position).getRealname());
             holder.userPhone.setText(list.get(position).getMobile());
             holder.userJiFen.setText(list.get(position).getCredit1());
             holder.userYuEr.setText("￥"+list.get(position).getCredit2());
             holder.userInfo.setText(list.get(position).getTitle());
           return convertView;
    }

    class  ViewHolder {
        private  TextView  userId;
        private  TextView userName;
        private  TextView userPhone;
        private  TextView userJiFen;
        private  TextView userYuEr;
        private  TextView userInfo;
        private  TextView userNum;
        private LinearLayout layout;
    }
}
