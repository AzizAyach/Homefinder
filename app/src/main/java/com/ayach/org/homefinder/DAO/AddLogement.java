package com.ayach.org.homefinder.DAO;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.PendingIntent.getActivities;
import static android.app.PendingIntent.getActivity;

/**
 * Created by aziz on 02/12/2015.
 */
public class AddLogement extends AsyncTask<String,Void,Void>{
Context mContext;
   public static String appid="Faz4ThhQoEGxPNcb6gfiEIKlmfxM67ImsJzOaCLf";
    public static String restid="VcOVo2d6Ybzfl0qy2ew454pwK5JpPm3Q5viCxSS4";
    public AddLogement (Context ctx){
        this.mContext=ctx;
    }
    @Override
    protected void onPreExecute() {

        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


    }
    @Override
    protected Void doInBackground(String... params) {

        long delayInMillis =120000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, delayInMillis);
        String src =params[0];
        System.out.println(src);
        HttpClient httpClient = new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;



        HttpPost httpPost = new HttpPost("https://api.parse.com/1/classes/Logements");
        httpPost.addHeader("X-Parse-Application-Id",appid);
        httpPost.addHeader("X-Parse-REST-API-Key",restid);
        httpPost.addHeader("content-type","application/json");
        StringEntity a = null;
        try {
            a = new StringEntity(src);
            httpPost.setEntity(a);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);



    }
    }

