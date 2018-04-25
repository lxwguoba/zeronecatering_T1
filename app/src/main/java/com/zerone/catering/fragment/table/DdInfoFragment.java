package com.zerone.catering.fragment.table;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerone.catering.R;


/**
 * Created by Administrator on 2017/8/4.
 */

public class DdInfoFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablectrl_ddinfofragment, container, false);
        return view;
    }
}
