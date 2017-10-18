package com.example.twkai.kai_exchangerate.utils;


/**
 * Created by twKai on 2017/9/14.
 */

public class BankProvider {

    public static String getBankName(String data){
        switch (data) {
            case "cathaybk":
                return "國泰世華銀行";
            case "fubonbk":
                return "富邦銀行";
            case "megabk":
                return "兆豐銀行";
            case "twbk":
                return "台灣銀行";

            case "chbbk":
                return "彰化銀行";

            case "esunbk":
                return "玉山銀行";

            case "taishinbk":
                return "台新銀行";

            case "hncbbk":
                return "華南銀行";

            case "tcbbk":
                return "合作金庫銀行";

            case "ctbcbk":
                return "中國信託銀行";

            case "feibbk":
                return "遠東商業銀行";

            case "sinopacbk":
                return "永豐銀行";

            case "kgibk":
                return "凱基銀行";

            case "tcbk":
                return "大眾銀行";

            case "entiebk":
                return "安泰銀行";

            case "scbk":
                return "上海商業銀行";

            case "dbsbk":
                return "星展銀行";

            case "netbk":
                return "日盛銀行";

            case "hsbcbk":
                return "匯豐銀行";
            case "firstbk":
                return "第一銀行";
            default:
                return "";
        }

    }

    public static int getBankID(String data){
        switch (data) {
            case "cathaybk":
                return 1;
            case "fubonbk":
                return 2;
            case "megabk":
                return 3;
            case "twbk":
                return 4;

            case "chbbk":
                return 5;

            case "esunbk":
                return 6;

            case "taishinbk":
                return 7;

            case "hncbbk":
                return 8;

            case "tcbbk":
                return 9;

            case "ctbcbk":
                return 10;

            case "feibbk":
                return 11;

            case "sinopacbk":
                return 12;

            case "kgibk":
                return 13;

            case "tcbk":
                return 14;

            case "entiebk":
                return 15;

            case "scbk":
                return 16;

            case "dbsbk":
                return 17;

            case "netbk":
                return 18;

            case "hsbcbk":
                return 19;

            case "firstbk":
                return 20;
            default:
                return 0;
        }

    }


}
