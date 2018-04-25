package com.zerone.catering.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zerone.catering.R;
import com.zerone.catering.domain.FoodXi;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 *
 */

public class GridViewAdapter extends BaseAdapter {
    private List<FoodXi> list;
    private Context context;

    private int clickItemPosition=-1;
    //标识选中的item
    public void setSelectPosition(int position){
        clickItemPosition=position;
    }

    public GridViewAdapter(List<FoodXi> list, Context context) {
        this.list = list;
        this.context = context;
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
            return    list.get(position);
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
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView=   LayoutInflater.from(context).inflate(R.layout.activity_gridview_items,null);
            // 初始化组件
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.foodName);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.selectedImg);
            viewHolder.layout_bg= (LinearLayout) convertView.findViewById(R.id.layout_bg);
            // 给converHolder附加一个对象
            convertView.setTag(viewHolder);
        }else {
            // 取得converHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.foodName.setText(list.get(position).getName());

        if (clickItemPosition== position) {
            viewHolder.foodName.setTextColor(Color.rgb(248,176,42));
            viewHolder.img.setVisibility(View.VISIBLE);
        } else {
            viewHolder.foodName.setTextColor(Color.rgb(91,91,91));
            viewHolder.img.setVisibility(View.GONE);
        }

        //这个是设置border的
//        if (position==0){
//            viewHolder.layout_bg.setBackgroundResource(R.drawable.gridview_border);
//        }else  if (position==4){
////             viewHolder.layout_bg.setBackgroundResource(R.drawable.gridview_border_right);
//         }else {
//             viewHolder.layout_bg.setBackgroundResource(R.drawable.gridview_border_bottom);
//         }
        return convertView;
    }

    class ViewHolder{
        private LinearLayout layout_bg;
        private TextView foodName;
        private ImageView img;

    }
}
