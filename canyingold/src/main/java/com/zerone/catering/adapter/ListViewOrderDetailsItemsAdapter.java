package com.zerone.catering.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zerone.catering.R;
import com.zerone.catering.domain.OrderInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOrderDetailsItemsAdapter extends BaseAdapter {
    private Context context;
    private List<OrderInfo.DataBean.ItemBean.GoodsBean> list;

    public ListViewOrderDetailsItemsAdapter(Context context, List<OrderInfo.DataBean.ItemBean.GoodsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_nweorderdetailssix_items,null);
            holder = new ViewHolder();
            holder.foodId= (TextView) convertView.findViewById(R.id.foodId);
            holder.foodName  = (TextView) convertView.findViewById(R.id.foodName);
            holder.foodCount=(TextView) convertView.findViewById(R.id.foodCount);
            holder.foodPrice = (TextView) convertView.findViewById(R.id.foodPrice);
            holder.foodSuccessPrice=(TextView) convertView.findViewById(R.id.foodOkPrice);
            holder.yongjin =(TextView) convertView.findViewById(R.id.foodYongJin);
            holder.foodUpData = (Button) convertView.findViewById(R.id.foodUpData);
            holder.optionLayout=(LinearLayout) convertView.findViewById(R.id.optionLayout);
            holder.optionsname =(TextView) convertView.findViewById(R.id.optionsname);
            convertView.setTag(holder);
            } else {
                   holder = (ViewHolder) convertView.getTag();
            }
        holder.foodId.setText(list.get(position).getId());
        String title = list.get(position).getTitle();

         if (title.length()>7){
             holder.foodName.setText(title.substring(0,7)+"...");
         }else {
             holder.foodName.setText(title);
         }


        holder.foodCount.setText("x "+list.get(position).getTotal());
        //这个是单价
        holder.foodPrice.setText("￥"+list.get(position).getMarketprice());
        //这个是成交价格
        holder.foodSuccessPrice.setText("￥"+list.get(position).getPrice());
        holder.yongjin.setText("￥"+list.get(position).getCommission());
        Log.i("URL", "getView: "+list.get(position).getOptionname());
         if ("null".equals(list.get(position).getOptionname())){
          //等于空 隐藏规格
          holder.optionLayout.setVisibility(View.GONE);
         }else {
         //展示规格
          holder.optionLayout.setVisibility(View.VISIBLE);
          holder.optionsname.setText(list.get(position).getOptionname()+"");
         }
        return convertView;
    }

    class  ViewHolder {
        private TextView  foodId;
        private TextView   foodName;
        private TextView   foodCount;
        private TextView   foodPrice;
        private TextView   foodSuccessPrice;
        private TextView  yongjin;
        private Button foodUpData;
        private TextView  optionsname;
        private LinearLayout   optionLayout;

    }
}
