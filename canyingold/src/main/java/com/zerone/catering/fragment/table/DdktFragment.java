package com.zerone.catering.fragment.table;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zerone.catering.R;
import com.zerone.catering.activitys.main.OrderMainActivity;
import com.zerone.catering.activitys.table.TableCtrlActivity;
import com.zerone.catering.constant.Constant;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.utils.ACache;

/**
 * Created by Administrator on 2017/8/5.
 */

public class DdktFragment extends Fragment{
    private Button kt_btn;
    private TableCtrlActivity activity;
    private String tableid;
    private String tablename;
    private Branch branch;
    private ACache mCache;
    //最低消费的记录
    private String tableMinconsume;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (TableCtrlActivity)getActivity();
        Bundle bundle = getArguments();//从activity传过来的Bundle
        tableid = bundle.getString("tableid");
        tablename = bundle.getString("tablename");
        tableMinconsume = bundle.getString("minconsume");
        mCache = ACache.get(activity);
        branch = (Branch) mCache.getAsObject("branch");
        View view = inflater.inflate(R.layout.tablectrl_ddktfragment, container, false);
        kt_btn = (Button)view.findViewById(R.id.kt_btn);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        kt_btn.setOnClickListener(kt);
    }
    Button.OnClickListener kt = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            final EditText jcnum = new EditText(activity);
            jcnum.setInputType(InputType.TYPE_CLASS_NUMBER);
            new AlertDialog.Builder(activity).setTitle("请输入就餐人数").setIcon(android.R.drawable
                    .ic_dialog_info).setView(jcnum).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String num = jcnum.getText().toString();
                    gotodc(num);
                }
            }).setNegativeButton("取消", null).show();

        }
    };

    /**
     *
     *
     * 修改这里修改这里 跳转到订单页点餐 传tableid 和就餐人数datenum;
     * @param num
     */
    public void gotodc(String num){
        Intent intent = new Intent(activity,OrderMainActivity.class);
        intent.putExtra("tableid",tableid);
        intent.putExtra("tablename",tablename);
        intent.putExtra("datenum",num);
        intent.putExtra("minconsume",tableMinconsume);
        intent.putExtra("actionId", Constant.ACTIONID_TABLEGOORDER);
        startActivity(intent);
        getActivity().finish();
    }
}
