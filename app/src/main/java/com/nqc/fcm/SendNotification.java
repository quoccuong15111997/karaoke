package com.nqc.fcm;

import android.content.Context;

import com.google.common.net.HttpHeaders;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nqc.firebase.SongFirebase;

import org.json.JSONObject;

import java.net.URLEncoder;

import cz.msebera.android.httpclient.entity.StringEntity;

public class SendNotification {
    SongFirebase songFirebase;
    Context context;
    public SendNotification(SongFirebase songFirebase, Context context){
        this.songFirebase=songFirebase;
        this.context=context;
    }
    public void send(){
        try {
            String url = "https://fcm.googleapis.com/fcm/send";
            AsyncHttpClient client = new AsyncHttpClient();

            client.addHeader(HttpHeaders.AUTHORIZATION, "key=AIzaSyA1GK7F5TgOO4nUQVbSnzGZrq5AP3icRtE");
            client.addHeader(HttpHeaders.CONTENT_TYPE, RequestParams.APPLICATION_JSON);
            JSONObject params = new JSONObject();

            params.put("to","/topics/ThongBao");

            JSONObject notificationObject = new JSONObject();
            String body="Bài hát: "+songFirebase.getTenBH()+", Mã: "+songFirebase.getMaBH();
            String title="Có bài hát mới vừa được cập nhật";
            notificationObject.put("body", URLEncoder.encode(body));
            notificationObject.put("title", URLEncoder.encode(title));

            params.put("notification", notificationObject);

            StringEntity entity = new StringEntity(params.toString());

            client.post(context, url, entity, RequestParams.APPLICATION_JSON, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                    //Log.i(TAG, responseString);
                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                    //Log.i(TAG, responseString);
                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
