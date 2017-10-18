// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.twkai.kai_exchangerate.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.twkai.kai_exchangerate.Adapter.Rate_ListAdapter1;
import com.example.twkai.kai_exchangerate.R;
import com.example.twkai.kai_exchangerate.base.BaseFragment;
import com.example.twkai.kai_exchangerate.home.MFApplication;
import com.example.twkai.kai_exchangerate.databinding.FragmentRate1Binding;
import com.example.twkai.kai_exchangerate.view.RecyclerViewNoBugLinearLayoutManager;


public class Rate1_Fragment extends BaseFragment<FragmentRate1Binding> {

    public Rate1_Fragment(){

    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rate1;

    }

    @Override
    protected void init() {

        // 设置Layout管理器
        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(getActivity());//RecyclerViewNoBugLinearLayoutManager閃退問題
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBind.rvListCash.setLayoutManager(layoutManager);
        dataBind.btnCashbuy.setTextColor(0Xffffbb00);
        dataBind.btnCashsell.setTextColor(0Xffffffff);
        dataBind.btnCashbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBind.btnCashbuy.setTextColor(0Xffffbb00);
                dataBind.btnCashsell.setTextColor(0Xffffffff);
                MFApplication.CashSortKey = "";
                dataBind.rvListCash.getLayoutManager().scrollToPosition(0);
            }
        });

        dataBind.btnCashsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBind.btnCashbuy.setTextColor(0Xffffffff);
                dataBind.btnCashsell.setTextColor(0Xffffbb00);
                dataBind.rvListCash.scrollToPosition(1);
                MFApplication.CashSortKey = "";
                dataBind.rvListCash.getLayoutManager().scrollToPosition(0);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void showView(Rate_ListAdapter1 rate_listAdapter){
        dataBind.rvListCash.setAdapter(rate_listAdapter);
    }

    public void showDollar(String dollar){
        dataBind.txtDollarCash.setText(dollar);
    }

}
