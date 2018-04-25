package com.zerone.catering.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.domain.Table;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/6/21.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    private Context context;
    private List<Table> list;
    private List<Integer> listPos= new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    public TableAdapter(List<Table> list) {
            this.list = list;
            }

    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (context==null){
            context=parent.getContext();
            }
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_recyclerview_items,parent,false);

            return new ViewHolder(view);
            }

@Override
public void onBindViewHolder(final TableAdapter.ViewHolder holder, int position) {
        Table table = list.get(position);
        if ("0".equals(table.getUsing())){
        holder.tableUse.setText("空闲中");
        holder.cardView.setBackgroundResource(R.drawable.zhuozi_bg_jiucankongxian);
        holder.cImg.setImageResource(R.drawable.yuanjiao_green);
        }else if ("1".equals(table.getUsing())){
        holder.tableUse.setText("使用中");
        holder.cardView.setBackgroundResource(R.drawable.zhuozi_bg_jiucanzhong);
        holder.cImg.setImageResource(R.drawable.yuanjiao_hong);
        }
        holder.tableName.setText(table.getTablename());
        if(mOnItemClickListener != null){
        if ("1".equals(list.get(position).getUsing())){
        return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                int position = holder.getLayoutPosition(); // 1
                mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
        });
        }
        if(mOnItemLongClickListener != null){
        if ("1".equals(list.get(position).getUsing())){
        return;
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
@Override
public boolean onLongClick(View v) {
        int position = holder.getLayoutPosition();
    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
    return true;
        }
        });
        }
        }

@Override
public int getItemCount() {
        return list.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    private final CardView cardView;
    private final TextView tableName;
    private final TextView tableUse;
    private final CircleImageView cImg;

    public ViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView;
        tableName = (TextView) cardView.findViewById(R.id.tableName);
        tableUse = (TextView) cardView.findViewById(R.id.tableUse);
        cImg = (CircleImageView) cardView.findViewById(R.id.can_img);


    }
}


//点击事件的接口
public interface OnItemClickListener{
    void onItemClick(View view, int position);
}
//长按事件的接口
public interface OnItemLongClickListener{
    void onItemLongClick(View view, int position);
}

    //点击事件的接口对外开放
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //长按事件的接口对外开放
    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
