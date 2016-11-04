package com.ayach.org.homefinder.DAO;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.ayach.org.homefinder.Entity.Logement;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by aziz on 02/12/2015.
 */
public class Parse_Logement extends AsyncTask<String,Void,ArrayList<Logement>> {
    public static String appid="Faz4ThhQoEGxPNcb6gfiEIKlmfxM67ImsJzOaCLf";
    public static String restid="VcOVo2d6Ybzfl0qy2ew454pwK5JpPm3Q5viCxSS4";
    Context mContext;
    SweetAlertDialog pDialog ;
public Parse_Logement(Context mContext)
{
 this.mContext=mContext;

}
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected ArrayList<Logement> doInBackground(String... params) {
        ArrayList<Logement> logements = new ArrayList<Logement>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("https://api.parse.com/1/classes/Logements");
        httpGet.addHeader("X-Parse-Application-Id",appid);
        httpGet.addHeader("X-Parse-REST-API-Key", restid);
        httpGet.addHeader("content-type", "application/json");
        String result;

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);

            try {
                if (params != null) {


                    result= EntityUtils.toString(response.getEntity());
                    JSONObject x=new JSONObject(result);

                    JSONArray jsonarray = x.getJSONArray("results");
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobj = jsonarray.getJSONObject(i);


                           Logement lo = new Logement();
                        String logmentstring = jsonobj.toString();
                        System.out.println(logmentstring);
                        Gson gpr = new Gson();
                          lo  = gpr.fromJson(String.valueOf(jsonobj), Logement.class);
                       logements.add(lo);
                    }

                }
                else {
                    Log.e("Parsing", "No json object from data");
                }
            }
            catch (JSONException e) {
                e.printStackTrace();}

        } catch (IOException e) {
            e.printStackTrace();
        }



   return logements;
    }

    @Override
    protected void onPostExecute(ArrayList Logement) {
        pDialog.dismiss();
    }
    }

