package com.zerone.catering.adapter.order;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.adapter.ListViewOptionTitle;
import com.zerone.catering.domain.order.YuYueBean;
import com.zerone.catering.utils.UtilsTime;

import java.util.List;

/**
 * Created by on 2017/11/20 0020 14 50.
 * Author  LiuXingWen
 */

public class YuYueAdapter extends BaseAdapter {
   private List<YuYueBean> list;
   private Context context;
    private Handler handler;

    public YuYueAdapter(List<YuYueBean> list, Context context,Handler handler) {
        this.list = list;
        this.context = context;
        this.handler=handler;
    }

    @Override
    public int getCount() {
         if (list!=null){
            return list.size();
         }
        return 0;
    }


    @Override
    public Object getItem(int i) {
        if (list!=null){
            return list.get(i);
        }
        return null;
    }


    @Override
    public long getItemId(int i) {
        if (list!=null){
             return  i;
        }
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView == null) {
            holder = new  ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_yuyue_items, null);
            holder.createOrderTime=convertView.findViewById(R.id.createOrderTime);
            holder.deleteOrder=convertView.findViewById(R.id.deleteOrder);
            holder.userName=convertView.findViewById(R.id.userName);
            holder.userPhone=convertView.findViewById(R.id.userPhone);
            holder.peopleCount = convertView.findViewById(R.id.peopleCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String datetime_st = list.get(position).getDatetime_st();
        String time = UtilsTime.getTime(Long.parseLong(datetime_st));
        holder.createOrderTime.setText(time);

        holder.userName.setText(list.get(position).getRealname());
        holder.userPhone.setText(list.get(position).getMobile());
        holder.peopleCount.setText(list.get(position).getDatenum());
        holder.deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message=new Message();
                 message.what=100;
                 message.obj=position;
                 handler.sendMessage(message);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView  userName;
        private TextView  userPhone;
        private TextView  peopleCount;
        private TextView  createOrderTime;
        private Button    deleteOrder;
    }
}
