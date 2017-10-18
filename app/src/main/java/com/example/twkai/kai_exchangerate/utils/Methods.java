package com.example.twkai.kai_exchangerate.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by twKai on 2017/9/14.
 */

public class Methods {


    public static void Ascending(ArrayList<HashMap<String, Object>> DataArray, final String key){
        if(DataArray.size() > 0){
            Collections.sort(DataArray, new Comparator<HashMap<String, Object>>() {
                @Override
                public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                    double num1 = 0;
                    double num2 = 0;

                    try {
                        num1 = Double.parseDouble(o1.get(key).toString());
                        num2 = Double.parseDouble(o2.get(key).toString());
                        if(num2 > num1){
                            return -1;
                        }else if(num2 < num1){
                            return 1;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    return 0;
                }
            });

        }

    }

    public static void Descending(ArrayList<HashMap<String, Object>> DataArray, final String key){
        if(DataArray.size() > 0){
            Collections.sort(DataArray, new Comparator<HashMap<String, Object>>() {
                @Override
                public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                    double num1 = 0;
                    double num2 = 0;
                    try {
                        num1 = Double.parseDouble(o1.get(key).toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {

                        num2 = Double.parseDouble(o2.get(key).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(num2 < num1){
                        return -1;
                    }else if(num2 > num1){
                        return 1;
                    }
                    return 0;
                }
            });

        }

    }

}
