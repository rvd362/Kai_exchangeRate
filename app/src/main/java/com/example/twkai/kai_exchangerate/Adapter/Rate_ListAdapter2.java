package com.example.twkai.kai_exchangerate.Adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.twkai.kai_exchangerate.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by twKai on 2017/9/13.
 */

public class Rate_ListAdapter2 extends RecyclerView.Adapter<Rate_ListAdapter2.ViewHolder>{
    private ArrayList<HashMap<String, Object>> RateData = new ArrayList<HashMap<String, Object>>();
//    int Page = 0;


    public Rate_ListAdapter2(ArrayList<HashMap<String, Object>> dataList) {
        this.RateData.addAll(dataList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mBank, mBkBuy, mBkSell;

        public ViewHolder(View v) {
            super(v);
            mBank = (TextView) v.findViewById(R.id.txtBank);
            mBkBuy = (TextView) v.findViewById(R.id.txtBkBuy);
            mBkSell = (TextView) v.findViewById(R.id.txtBkSell);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_network_rate2, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HashMap<String, Object> rateData = RateData.get(position);

//        MFApplication.SortKey = "bkbuy";
        if(RateData.size()>0){
            holder.mBank.setText(rateData.get("bank").toString());
            holder.mBkBuy.setText(rateData.get("bkbuy").toString());
            holder.mBkSell.setText(rateData.get("bksell").toString());


        }
//        CashOrBuy(holder, position);

    }

    public void setItems(ArrayList<HashMap<String, Object>> Data){
        RateData = Data;
    }

    public void swapItems( ArrayList<HashMap<String, Object>> Rates) {
        final RateDiffCallback diffCallback = new RateDiffCallback(this.RateData, Rates);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.RateData.clear();
        this.RateData.addAll(Rates);
//        Log.e("123456789", "------" + RateData);
        diffResult.dispatchUpdatesTo(this);
    }



    @Override
    public int getItemCount() {
        return RateData.size();
    }

//    public void setPage(int page){
//        Page = page;
//
//    }
//    private void CashOrBuy(ViewHolder holder, int position){
//        holder.mBkBuy.setVisibility(View.GONE);
//        holder.mBkSell.setVisibility(View.GONE);
//        holder.mCashBuy.setVisibility(View.GONE);
//        holder.mCashSell.setVisibility(View.GONE);
//        switch (Page) {
//            case 0:
//                holder.mCashBuy.setVisibility(View.VISIBLE);
//                holder.mCashSell.setVisibility(View.VISIBLE);
////                if(position == 0){
////                    holder.mCashBuy.setTextColor(0Xffff0000);
////                    holder.mCashSell.setTextColor(0Xffff0000);
////                }else if(position == 1){
////                    holder.mCashBuy.setTextColor(0Xff0000ff);
////                    holder.mCashSell.setTextColor(0Xff0000ff);
////                }else if(position == 2){
////                    holder.mCashBuy.setTextColor(0Xff00ff00);
////                    holder.mCashSell.setTextColor(0Xff00ff00);
////                }
//                break;
//            case 1:
//                holder.mBkBuy.setVisibility(View.VISIBLE);
//                holder.mBkSell.setVisibility(View.VISIBLE);
////                if (position == 0) {
////                    holder.mBkBuy.setTextColor(0Xffff0000);
////                    holder.mBkSell.setTextColor(0Xffff0000);
////
////                }else if (position == 1) {
////                    holder.mBkBuy.setTextColor(0Xff0000ff);
////                    holder.mBkSell.setTextColor(0Xff0000ff);
////
////                }else if (position == 2) {
////                    holder.mBkBuy.setTextColor(0Xff00ff00);
////                    holder.mBkSell.setTextColor(0Xff00ff00);
////
////                }
//                break;
//
//            default:
//                break;
//        }
//
//    }

}
