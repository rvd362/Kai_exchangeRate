package com.example.twkai.kai_exchangerate.Adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateDiffCallback extends DiffUtil.Callback{

    private final ArrayList<HashMap<String, Object>>  oldList;
    private final ArrayList<HashMap<String, Object>>  newList;

    public RateDiffCallback(ArrayList<HashMap<String, Object>>  oldList, ArrayList<HashMap<String, Object>> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).get("ID") == newList.get(newItemPosition).get("ID");
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final HashMap<String, Object> oldItem = oldList.get(oldItemPosition);
        final HashMap<String, Object> newItem = newList.get(newItemPosition);

        return oldItem.get("bank").equals(newItem.get("bank"));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
