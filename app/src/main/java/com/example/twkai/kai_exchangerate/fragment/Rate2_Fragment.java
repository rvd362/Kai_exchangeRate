// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.twkai.kai_exchangerate.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.twkai.kai_exchangerate.Adapter.Rate_ListAdapter2;
import com.example.twkai.kai_exchangerate.R;
import com.example.twkai.kai_exchangerate.base.BaseFragment;
import com.example.twkai.kai_exchangerate.home.MFApplication;
import com.example.twkai.kai_exchangerate.databinding.FragmentRate2Binding;
import com.example.twkai.kai_exchangerate.view.RecyclerViewNoBugLinearLayoutManager;


public class Rate2_Fragment extends BaseFragment<FragmentRate2Binding> {

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rate2;

    }

    @Override
    protected void init() {
        // 设置Layout管理器
        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBind.rvListBk.setLayoutManager(layoutManager);
        dataBind.btnBkbuy.setTextColor(0Xffffbb00);
        dataBind.btnBksell.setTextColor(0Xffffffff);

        dataBind.btnBkbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBind.btnBkbuy.setTextColor(0Xffffbb00);
                dataBind.btnBksell.setTextColor(0Xffffffff);
                dataBind.rvListBk.scrollToPosition(1);
                MFApplication.BkSortKey = "";
                dataBind.rvListBk.getLayoutManager().scrollToPosition(0);
            }
        });

        dataBind.btnBksell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBind.btnBkbuy.setTextColor(0Xffffffff);
                dataBind.btnBksell.setTextColor(0Xffffbb00);
                dataBind.rvListBk.scrollToPosition(1);
                MFApplication.BkSortKey = "";
                dataBind.rvListBk.getLayoutManager().scrollToPosition(0);
            }
        });



    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void showView(Rate_ListAdapter2 rate_listAdapter){
        dataBind.rvListBk.setAdapter(rate_listAdapter);
        dataBind.txtDollarBk.setText("");
    }

    public void showDollar(String dollar){
        dataBind.txtDollarBk.setText(dollar);
    }

}
