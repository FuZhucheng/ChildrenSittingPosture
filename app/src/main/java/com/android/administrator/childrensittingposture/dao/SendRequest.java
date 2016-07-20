package com.android.administrator.childrensittingposture.dao;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus on 2016/7/20.
 */
public class SendRequest {


    public void SendUid(final int uid, final Handler handler, final Context context) {
        final OkHttpClient mOkHttpClient=new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Request request=new Request.Builder()
                                                                        .url("http://www.baidu.com")
                                                                        .build();
                Response response=null;
                try{
                    response=mOkHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        Log.e("success","success");
                        Analysis analysis=new Analysis();
                        analysis.analysis(response.body().string());
                    }
                    else {
                        throw new IOException("Unexpected code"+response);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }
}


