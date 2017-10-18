package com.example.twkai.kai_exchangerate.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twkai.kai_exchangerate.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by twKai on 2017/9/13.
 */

public class Country_itemAdapter extends RecyclerView.Adapter<Country_itemAdapter.ViewHolder>{
    private ArrayList<HashMap<String, Object>> RateData = new ArrayList<HashMap<String, Object>>();

    int[] image = {
            R.drawable.usd, R.drawable.jpy, R.drawable.krw, R.drawable.myr, R.drawable.nzd, R.drawable.php, R.drawable.sek, R.drawable.sgd,
            R.drawable.thb, R.drawable.aud, R.drawable.cad, R.drawable.chf, R.drawable.cny, R.drawable.eur, R.drawable.gbp, R.drawable.hkd,
            R.drawable.idr, R.drawable.vnd, R.drawable.zar};




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCountry;
        public ImageView Img;

        public ViewHolder(View v) {
            super(v);
            mCountry = (TextView) v.findViewById(R.id.txtCountry);
            Img = (ImageView)v.findViewById(R.id.img);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country_rate, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mCountry.setText(RateData.get(position).get("Country").toString());
        holder.Img.setImageResource(Integer.valueOf(RateData.get(position).get("Img").toString()));

    }


    public interface ClickListener {
        void ItemClicked(View v, int position);
    }

    public void setItems(){
        ArrayList<HashMap<String, Object>> tempData = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i <country.length; i++){
            HashMap<String, Object> oneInfo = new HashMap<String, Object>();
            oneInfo.put("Country", country[i]);
            oneInfo.put("Img", image[i]);
            oneInfo.put("Dollar", dollar[i]);
            tempData.add(oneInfo);
        }
        RateData = tempData;
    }

    public ArrayList<HashMap<String, Object>> getItems(){

        return RateData;
    }



    @Override
    public int getItemCount() {
        return RateData.size();
    }
}
