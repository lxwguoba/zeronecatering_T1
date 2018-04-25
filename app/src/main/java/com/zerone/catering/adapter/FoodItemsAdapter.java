package com.zerone.catering.adapter;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zerone.catering.R;
import com.zerone.catering.activitys.main.OrderMainActivity;
import com.zerone.catering.domain.FoodItemsBean;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.Utils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/1.
 */

public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.MyViewHolder> {
    //点击事件的处里
    private TableAdapter.OnItemClickListener mOnItemClickListener;
    private TableAdapter.OnItemLongClickListener mOnItemLongClickListener;
    List<FoodItemsBean.GoodslistBean> list;
    Map<String, Integer> mapcount;
    Context context;
    Handler handler;
    private LayoutInflater inflater;
    private final ACache mCache;

    public FoodItemsAdapter(Context context, List<FoodItemsBean.GoodslistBean> list, Map<String, Integer> mapcount, Handler handler){
        this.context=context;
        mCache = Utils.getACache(context);
        this.list=list;
        this.mapcount=mapcount;
        this.handler=handler;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_newfoodchild_items,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }


   //进行操作
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String goodsname = list.get(position).getGoodsname();
         if (goodsname.length()>9){
             String substring = goodsname.substring(0, 7);
             holder.foodName.setText(substring+"...");
         }else {
             holder.foodName.setText(goodsname);
         }


          holder.foodPrice.setText(list.get(position).getPrice());
//           Log.i("URL", ""+mapcount.get(list.get(position).getGoodsid()));
           Integer integer = mapcount.get(list.get(position).getGoodsid());
          if (integer==null||integer==0){
              holder.foodCount.setText("销售完");
              holder.foodCount.setTextColor(Color.rgb(242,91,73));
          }else if (integer==-1){
              holder.foodCount.setText("无限量");
              holder.foodCount.setTextColor(Color.rgb(242,91,73));
          }else {
              holder.foodCount.setText("x"+mapcount.get(list.get(position).getGoodsid()));
              holder.foodCount.setTextColor(Color.rgb(50,50,50));
          }

          Glide.with(context).load(list.get(position).getThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().centerCrop().into(holder.foodImg);

            //点击事件的实施对外开发
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int position = holder.getLayoutPosition();
                  if (mOnItemClickListener!=null){
                      mOnItemClickListener.onItemClick(holder.itemView,position);
                  }
              }
          });

         //长按事件的实施对外开发
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getLayoutPosition();
                if (mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                }
                return true;
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String goodsid = list.get(position).getGoodsid();
                String money=list.get(position).getPrice();
                mCache.put("vipmoney",money);
                Message message = new Message();
                    message.what=18;
                    message.obj= goodsid;
                    handler.sendMessage(message);
//                Log.d("FoodItemsAdapter", "点击我干啥呢 I'M VIP");
            }
        });
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
         RelativeLayout relayout;
         TextView  foodName;
         ImageView foodImg;
         TextView foodPrice;
         TextView foodCount;
         ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            relayout= (RelativeLayout) view.findViewById(R.id.relayout);
            foodName=(TextView) view.findViewById(R.id.foodName);
            foodImg = (ImageView) view.findViewById(R.id.foodImg);
            foodPrice = (TextView) view.findViewById(R.id.foodPrice);
            foodCount= (TextView) view.findViewById(R.id.foodCount);
            imageButton=(ImageButton) view.findViewById(R.id.vipbtn);
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
    public void setOnItemClickListener(TableAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //长按事件的接口对外开放
    public void setOnItemLongClickListener(TableAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}