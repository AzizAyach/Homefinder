package com.ayach.org.homefinder.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ayach.org.homefinder.Entity.Reservation;
import com.ayach.org.homefinder.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SendCallback;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by aziz on 10/01/2016.
 */
public class ReservationAdapter extends ArrayAdapter<Reservation> {

    List list =new ArrayList();
    private Context mContext;
    private int layoutResourceId;
    ImageButton  ok;
    ImageButton cancel;
    private ArrayList<Reservation> mlogeData = new ArrayList<Reservation>();
    public ReservationAdapter(Context  mContext, int layoutResourceId,ArrayList<Reservation> mlogeData) {
        super( mContext,layoutResourceId,mlogeData);
        this.setLayoutResourceId(layoutResourceId);
        this.mContext = mContext;
        this.mlogeData=mlogeData;
    }

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    public void setLayoutResourceId(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }

    public ArrayList<Reservation> getMlogeData() {
        return mlogeData;
    }

    public void setMlogeData(ArrayList<Reservation> mlogeData) {
        this.mlogeData = mlogeData;
        notifyDataSetChanged();
    }


    static class Handlerloge
    {

        TextView message;
        TextView Datedebut ;
        TextView Datefin;
        CircleImageView circleImageView;
        TextView nameuser;
    }
    @Override
    public int getCount() {
        return mlogeData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row ;
        row =convertView;
        final Handlerloge handler;
        if (convertView==null){

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(getLayoutResourceId(), parent, false);
            handler = new Handlerloge();
            handler.circleImageView = (CircleImageView)row.findViewById(R.id.user_image);
            handler.message = (TextView)row.findViewById(R.id.message_res);
            handler.Datedebut = (TextView)row.findViewById(R.id.datedebut);
            handler.Datefin= (TextView)row.findViewById(R.id.datefin);
            handler.nameuser=  (TextView)row.findViewById(R.id.name_user);
              ok = (ImageButton)row.findViewById(R.id.reservation_ok);
             cancel = (ImageButton)row.findViewById(R.id.reservation_cancel);
            row.setTag(handler);
        }
        else{

            handler= (Handlerloge)row.getTag();

        }
        final Reservation reservation = mlogeData.get(position);
        handler.message.setText(reservation.getMessage());
        handler.Datedebut.setText(reservation.getDebut_date());
        handler.Datefin.setText(reservation.getDebut_fin());
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", reservation.getUserid());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        ParseFile parseFile = object.getParseFile("Picture");
                        byte[] data = new byte[0];
                        try {
                            data = parseFile.getData();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            handler.nameuser.setText(object.getString("username"));
                            handler.circleImageView.setImageBitmap(bitmap);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }


                    }
                } else {
                    System.out.println("wrong    " + e);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("to be refuse request!")
                        .setConfirmText("Yes!")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Reservation");
                                query.getInBackground(reservation.getId(), new GetCallback<ParseObject>() {
                                    public void done(ParseObject object, ParseException e) {
                                        if (e == null) {
                                            object.put("type", 2);
                                            object.saveInBackground();

                                            ParsePush parsePush = new ParsePush();
                                            parsePush.setMessage("Your Request has been refused");

                                            ParseQuery parseQuery = ParseInstallation.getQuery();
                                            parseQuery.whereEqualTo("iduser", reservation.getUserid());
                                            parsePush.setQuery(parseQuery);
                                            parsePush.sendInBackground(new SendCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    System.out.println("push  " + e);
                                                }
                                            });
                                        }
                                    }
                                });


                                System.out.println("cancel button");
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                notifyDataSetChanged();

            }
        });

     ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("to be accepte request!")
                .setConfirmText("Yes!")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reservation");
                        System.out.println("id  " + reservation.getId());
                        query.getInBackground(reservation.getId(), new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    object.put("type", 1);
                                    object.saveInBackground();


                                    ParsePush parsePush = new ParsePush();
                                    parsePush.setMessage("Your Request has been accepted");

                                    ParseQuery parseQuery = ParseInstallation.getQuery();
                                    parseQuery.whereEqualTo("iduser", reservation.getUserid());
                                    parsePush.setQuery(parseQuery);
                                    parsePush.sendInBackground(new SendCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            System.out.println("push  " + e);
                                        }
                                    });
                                }
                            }
                        });


                        sDialog.dismissWithAnimation();
                    }

                })
                .show();
        notifyDataSetChanged();
    }

});
        return row;
    }

}
