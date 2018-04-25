package com.zerone.catering.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.DaiFuKuanOrderBean;
import com.zerone.catering.utils.UtilsTime;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOrderItemsAdapter extends BaseAdapter {
    private Context context;
    private List<DaiFuKuanOrderBean.DataBean.ListBean> list;

    private Handler handler;

    public ListViewOrderItemsAdapter(Context context, List<DaiFuKuanOrderBean.DataBean.ListBean> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_neworder_daifukuang_items,null);
            holder = new ViewHolder();
            holder.orderNum = (TextView) convertView.findViewById(R.id.orderNum);
            holder.orderUser = (TextView) convertView.findViewById(R.id.orderUser);
            holder.userPhone= (TextView) convertView.findViewById(R.id.userPhone);
            holder.seat_fee = (TextView) convertView.findViewById(R.id.seat_fee);
            holder.orderType =  (TextView) convertView.findViewById(R.id.orderType);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.orderStatus  = (TextView) convertView.findViewById(R.id.orderStatus);
            holder.orderTime  =(TextView) convertView.findViewById(R.id.orderTime);

            convertView.setTag(holder);
            } else {
                   holder = (ViewHolder) convertView.getTag();
            }
            holder.orderNum.setText(list.get(position).getOrdersn());
            holder.orderUser.setText(list.get(position).getTableName());
            holder.userPhone.setText(list.get(position).getMobile());
             double sale = Double.parseDouble(list.get(position).getSale());
             double  money=Double.parseDouble(list.get(position).getPrice());
             double newmoney=sale*money;
            holder.money.setText("￥"+newmoney);
            holder.seat_fee.setText("￥"+list.get(position).getSeat_fee());
            holder.orderTime.setText(UtilsTime.getTime(Long.parseLong(list.get(position).getCreatetime())));
            //"dispatchchoice": "3",
            //1.外卖订单
            // 2.预约就餐订单
            // 3.现场订单订单
//        orderType 修改为打印状态

            //前台打印状态
            Integer rp_times=Integer.parseInt(list.get(position).getRp_times());
            //后厨打印
            Integer kp_times=Integer.parseInt(list.get(position).getKp_times());
            if (rp_times>0&&kp_times>0){
                holder.orderType.setText("前台已打印，后厨已打印");
            }else   if (rp_times>0&& kp_times==0){
                holder.orderType.setText("前台已打印，后厨未打印");
            }else   if (rp_times==0&& kp_times>0){
                holder.orderType.setText("前台未打印，后厨已打印");
            }else   if (rp_times==0&& kp_times==0){
                holder.orderType.setText("前台未打印，后厨未打印");
            }
        //订单类型
        if ("0".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("待付款");
        }else   if ("1".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("已付款");
        }else   if ("2".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("待收货");
        }else   if ("3".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("已完成");
        }else   if ("-1".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("已关闭");
        }else   if ("-2".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("退款中");
        }else   if ("-3".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("换货中");
        }else   if ("-4".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("退货中");
        }else   if ("-5".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("已退货");
        }else   if ("-6".equals(list.get(position).getStatus())){
            holder.orderStatus.setText("已退款");
        }

        return convertView;
    }

    class  ViewHolder {
        private TextView  orderNum;
        private TextView  orderUser;
        private TextView  userPhone;
        private TextView  payType;
        private TextView  orderType;
        private TextView  money;
        private TextView  orderStatus;
        private TextView  orderTime;
        private  TextView seat_fee;
    }
}
