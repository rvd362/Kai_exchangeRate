package com.example.twkai.kai_exchangerate.bean;

/**
 * Created by twKai on 2017/9/18.
 */

public class BuySellBean {
    /**
     * bkbuy : 35.68800
     * bksell : 36.08800
     * cashbuy : 35.33800
     * cashsell : 36.33800
     */

    private String bkbuy;
    private String bksell;
    private String cashbuy;
    private String cashsell;

    public String getBkbuy() {
        return bkbuy;
    }

    public void setBkbuy(String bkbuy) {
        this.bkbuy = bkbuy;
    }

    public String getBksell() {
        return bksell;
    }

    public void setBksell(String bksell) {
        this.bksell = bksell;
    }

    public String getCashbuy() {
        return cashbuy;
    }

    public void setCashbuy(String cashbuy) {
        this.cashbuy = cashbuy;
    }

    public String getCashsell() {
        return cashsell;
    }

    public void setCashsell(String cashsell) {
        this.cashsell = cashsell;
    }
}
