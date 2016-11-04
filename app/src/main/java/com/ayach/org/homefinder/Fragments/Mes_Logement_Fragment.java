package com.ayach.org.homefinder.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayach.org.homefinder.Adapter.RecycleviewAdapter;
import com.ayach.org.homefinder.DAO.MylogementParse;
import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mes_Logement_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mes_Logement_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mes_Logement_Fragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private RecycleviewAdapter recadapter;
    MylogementParse pl ;
    private ArrayList<Logement> loger = new ArrayList<Logement>();
    private OnFragmentInteractionListener mListener;
ParseUser usr ;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mes_Logement_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Mes_Logement_Fragment newInstance(String param1, String param2) {
        Mes_Logement_Fragment fragment = new Mes_Logement_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Mes_Logement_Fragment() {
        // Required empty public constructor
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

        View v = inflater.inflate(R.layout.fragment_mes__logement_, container, false);
         pl =new MylogementParse(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleMy);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        usr= ParseUser.getCurrentUser();
        try {
            loger = pl.execute(usr.getObjectId()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        recadapter = new RecycleviewAdapter(loger);
        recyclerView.setAdapter(recadapter);
        recadapter.SetOnItemClickListener(new RecycleviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Logement logement = (Logement) recadapter.getItem(position);
                    DetailFragment detail = new DetailFragment();
                    Bundle detBundle = new Bundle();
                    detBundle.putParcelable("logement", logement);
                    detail.setArguments(detBundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame, detail)
                            .commit();
                } catch (Exception e) {
                    System.out.println("" + e);
                }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button

                    FragmentManager mg = getFragmentManager();
                    mg.beginTransaction().replace(R.id.frame, new Welcom_Fragment(), "welcom").commit();
                    return true;

                }

                return false;
            }
        });
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
        public void onFragmentInteraction(Uri uri);
    }



}
