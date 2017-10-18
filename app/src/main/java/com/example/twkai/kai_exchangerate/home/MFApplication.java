package com.example.twkai.kai_exchangerate.home;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


/**
 * Created by twKai on 2017/9/11.
 */

public class MFApplication extends Application {
    public static final String TAG = MFApplication.class.getSimpleName();
    private Socket socket;
    public static String URL_SERVER =  "";
    public static String Dollar =  "";
    public static String CashSortKey =  "";
    public static String BkSortKey =  "";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Socket getSocket() {
        if (socket == null) {
            try {
                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                socket = IO.socket(URL_SERVER);

            }catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return socket;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        socket.off("message");
    }
}