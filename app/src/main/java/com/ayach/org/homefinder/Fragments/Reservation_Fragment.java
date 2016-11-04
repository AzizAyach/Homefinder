package com.ayach.org.homefinder.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ayach.org.homefinder.Adapter.ReservationAdapter;
import com.ayach.org.homefinder.Entity.Reservation;
import com.ayach.org.homefinder.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reservation_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reservation_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservation_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listareserv;
    List<ParseObject> ob;
    private ReservationAdapter reservAdapter;
    private Reservation Rdata;
    private ArrayList<Reservation> listdata;
    ParseUser user;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Reservation_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservation_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservation_Fragment newInstance(String param1, String param2) {
        Reservation_Fragment fragment = new Reservation_Fragment();
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
        View v = inflater.inflate(R.layout.fragment_reservation_, container, false);
        listareserv =(ListView) v.findViewById(R.id.listereserv);
        listdata= new ArrayList();
        reservAdapter = new ReservationAdapter(getActivity(), R.layout.row_reservation, listdata);

user= ParseUser.getCurrentUser();
        listareserv.setAdapter(reservAdapter);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reservation");
        query.whereEqualTo("type", 0);
        query.whereEqualTo("propid",user.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object  :objects) {
                        Reservation reservation = new Reservation();
                        reservation.setId(object.getObjectId());
                        System.out.println(object.getObjectId());
                        reservation.setMessage(object.getString("message"));
                        reservation.setDebut_date(object.getString("start"));
                        reservation.setPropid(object.getString("propid"));
                        reservation.setType(object.getInt("type"));
                        reservation.setUserid(object.getString("userid"));
                        reservation.setIdlogement(object.getString("idlogement"));
                        reservation.setDebut_fin(object.getString("end"));
                        listdata.add(reservation);
                    }
                    reservAdapter.setMlogeData(listdata);

                } else {


                }
            }

        });



        return  v;
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
