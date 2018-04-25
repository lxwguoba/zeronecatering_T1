package com.zerone.catering.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/8/1.
 *
 *
 *
 */

public class SetRecyclerRow extends RecyclerView.ItemDecoration{

    private int space;
    private int left;

    public  SetRecyclerRow (int space,int left) {
        this.space = space;
        this.left=left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) >= 0) {
            outRect.bottom = space;
        }
        if (parent.getChildPosition(view)==0||parent.getChildPosition(view)==1||parent.getChildPosition(view)==2){
              outRect.top=space ;
        }
            outRect.left = left;
            outRect.right= left;
    }
}
