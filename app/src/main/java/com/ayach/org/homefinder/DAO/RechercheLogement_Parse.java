package com.ayach.org.homefinder.DAO;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.Entity.LogerData;
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
 * Created by aziz on 27/12/2015.
 */
public class RechercheLogement_Parse extends AsyncTask<LogerData,Void,ArrayList<Logement>> {
    public static String appid="Faz4ThhQoEGxPNcb6gfiEIKlmfxM67ImsJzOaCLf";
    public static String restid="VcOVo2d6Ybzfl0qy2ew454pwK5JpPm3Q5viCxSS4";
    Context mContext;
    SweetAlertDialog pDialog;
    ArrayList<Logement> logements = new ArrayList<Logement>();
    public RechercheLogement_Parse(Context mContext){this.mContext=mContext;}
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
    protected ArrayList<Logement> doInBackground(LogerData... params) {


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

                    String pay = params[0].getPays();
                    String vil = params[0].getCity();
                    int toilet = params[0].getNbtoil();
                    int bedro = params[0].getNbchambre();
                    int bed = params[0].getNblit();
                    int min = params[0].getPricemin();
                    int max = params[0].getPricemax();
                    result= EntityUtils.toString(response.getEntity());
                    JSONObject x=new JSONObject(result);

                    JSONArray jsonarray = x.getJSONArray("results");
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobj = jsonarray.getJSONObject(i);

/*                     JSONObject loca = jsonobj.getJSONObject("location");
                        String payi = loca.getString("country");
                        String vili = loca.getString("state");
                        JSONObject att = jsonobj.getJSONObject("attr");
                        String toileti = att.getString("bathrooms");
                        String bedroi = att.getString("bedrooms");
                        String vilim = loca.getString("state");
                        JSONObject price = jsonobj.getJSONObject("price");
*/


                        Logement lo = new Logement();
                            String logmentstring = jsonobj.toString();
                            System.out.println(logmentstring);
                            Gson gpr = new Gson();
                            lo = gpr.fromJson(String.valueOf(jsonobj), Logement.class);
try {


    if (lo.getLocation().getCountry().equals(pay) && !lo.getLocation().getState().equals(vil) &&
            lo.getAttr().getBeds() >= bed && lo.getAttr().getBedrooms() >= bedro
            && lo.getAttr().getBathrooms() >= toilet && (min <= lo.getPrice().getNightly())
            && (max >= lo.getPrice().getNightly()))

    {
        logements.add(lo);


    }
}
catch (Exception e){}


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

