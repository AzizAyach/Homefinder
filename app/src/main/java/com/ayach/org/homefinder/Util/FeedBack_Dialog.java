package com.ayach.org.homefinder.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.R;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by aziz on 04/01/2016.
 */
public class FeedBack_Dialog extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatePicker debut;
    DatePicker fin;
    Button submit;
    Button cancel;
    String mes;
    Logement loge = new Logement();
    ParseUser user;
    android.support.v4.app.FragmentManager fm;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FeedBack_Dialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogFilterFragment newInstance(String param1, String param2) {
        DialogFilterFragment fragment = new DialogFilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.feedbackdialog_fragment, container, false);
        debut = (DatePicker) v.findViewById(R.id.starter);
        fin = (DatePicker) v.findViewById(R.id.enden);
        cancel = (Button) v.findViewById(R.id.cancel_feed);
        submit = (Button) v.findViewById(R.id.apply_filter_feed);
        getDialog().setTitle("Request to Rent");
        user = ParseUser.getCurrentUser();



                submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = debut.getDayOfMonth();
                int month = debut.getMonth();
                int year = debut.getYear() - 1900;
                int day1 = fin.getDayOfMonth();
                int month1 = fin.getMonth();
                int year1 = fin.getYear() - 1900;

                if (((day >= day1 && month == month1)) || ( month >= month1  && year == year1)) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Wrong Date")
                            .setContentText("verfie your date selected!")
                            .show();


                } else {
                    loge = getArguments().getParcelable("logement");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    final String formatedDate = sdf.format(new Date(year, month, day));
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    final String formatedDate1 = sdf.format(new Date(year1, month1, day1));
                    mes = "I Want Rent your House on this date  " + formatedDate + " to " + formatedDate1;
                    ParseObject reserv = new ParseObject("Reservation");

                    ParseACL acl = new ParseACL();
                    acl.setPublicReadAccess(true);
                    acl.setPublicWriteAccess(true);
                    reserv.setACL(acl);
                    reserv.put("idlogement", loge.getId());
                    reserv.put("type", 0);
                    reserv.put("message", mes);
                    reserv.put("start", formatedDate);
                    reserv.put("end", formatedDate1);
                    reserv.put("propid", loge.getIduser());
                    reserv.put("userid", user.getObjectId());

                    reserv.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            System.out.println("" + e);
                        }
                    });

                    ParsePush parsePush = new ParsePush();
                    parsePush.setMessage("You Have Request For Rent  from"+user.getUsername());

                    ParseQuery parseQuery = ParseInstallation.getQuery();
                    parseQuery.whereEqualTo("iduser", loge.getIduser());
                    parsePush.setQuery(parseQuery);
                    parsePush.sendInBackground(new SendCallback() {
                        @Override
                        public void done(ParseException e) {
                            System.out.println("push  " + e);
                        }
                    });
                    dismiss();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dismiss();
            }
        });
        return v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
