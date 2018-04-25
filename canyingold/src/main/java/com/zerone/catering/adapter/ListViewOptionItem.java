package com.zerone.catering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zerone.catering.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ListViewOptionItem extends BaseAdapter {
    private Context context;
    private Map<String, String> mapitem;

    public ListViewOptionItem(Context context, Map<String, String> mapitem) {
        this.context = context;
        this.mapitem= mapitem;
    }

    @Override
    public int getCount() {
        if (mapitem != null) {
            return mapitem.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mapitem != null) {
            return mapitem.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mapitem != null) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_optionsitem, null);
            holder = new ViewHolder();
//            holder.titleName = convertView.findViewById(R.id.testname);
            holder.gridView=convertView.findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//         holder.titleName.setText(mapitem.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView titleName;
        private GridView gridView;
    }
}
