package com.zerone.catering.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.FoodOrderInfoBean;
import com.zerone.catering.domain.OptionsBean;
import com.zerone.catering.domain.OptionsBeanItem;
import com.zerone.catering.utils.ListViewUtils;
import com.zerone.catering.utils.SetGridViewHeight;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOptionTitle extends BaseAdapter {
    private Context context;
    private List<OptionsBean> list;
    private Handler handler;

    public ListViewOptionTitle(Context context, List<OptionsBean> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (list != null) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_optionsitem, null);
            holder = new ViewHolder();
            holder.titleName = convertView.findViewById(R.id.testname);
            holder.gridView = convertView.findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleName.setText(list.get(position).getTitle() + ":");
        final GridViewOptionAdapter gridViewOptionAdapter = new GridViewOptionAdapter(list.get(position).getItems(), context, "");
        holder.gridView.setAdapter(gridViewOptionAdapter);
        SetGridViewHeight.setListViewHeightBasedOnChildrenAUTO(holder.gridView);
        //默认选中第一个
        gridViewOptionAdapter.setSelectPosition(0);

        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                gridViewOptionAdapter.setSelectPosition(pos);
                gridViewOptionAdapter.notifyDataSetChanged();
                String title_item = position + "==" + pos;
                Message message = new Message();
                message.obj = title_item;
                message.what = 2;
                handler.sendMessage(message);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView titleName;
        private GridView gridView;
    }
}
