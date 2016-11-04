package com.ayach.org.homefinder.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ayach.org.homefinder.Adapter.RecycleviewAdapter;
import com.ayach.org.homefinder.DAO.RechercheLogement_Parse;
import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.Entity.LogerData;
import com.ayach.org.homefinder.Entity.Price;
import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.View.Home_Activity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link List_rechercheFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link List_rechercheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class List_rechercheFragment extends android.support.v4.app.Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Logement> loger = new ArrayList<Logement>();
    private ArrayList<Logement> logerfiltrer = new ArrayList<Logement>();
    RechercheLogement_Parse pl ;
    private OnFragmentInteractionListener mListener;
    Price price = new Price();
    LogerData search ;
    RecyclerView recyclerView;
    private RecycleviewAdapter recadapter;

    public List_rechercheFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment List_rechercheFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static List_rechercheFragment newInstance(String param1, String param2) {
        List_rechercheFragment fragment = new List_rechercheFragment();
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
        View v = inflater.inflate(R.layout.fragment_list_recherche, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclesearch);
        RechercheLogement_Parse pl =new RechercheLogement_Parse(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        try{
            String pays = getArguments().getString("pays");
            String ville = getArguments().getString("ville");
            price = Home_Activity.searchBunle.getParcelable("Search");
            int bed =1;
            int room =1;
            int toil =1;
            int pricemin = 10;
            int pricemax = 1000;
            if(price!=null) {
                bed = price.getMonthly();
                room = price.getNightly();
                toil = price.getWeekend();
                pricemin = price.getMinNight();
                pricemax = price.getMaxNight();
            }
            search = new LogerData(pays,ville,room,toil,bed,pricemin,pricemax);

        }
        catch (Exception e){
 System.out.println("probleme de search");
        }
        try {
            loger = pl.execute(search).get();
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
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.del_menu_favoris, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menurech:
                Map_All_Logement maploge= new Map_All_Logement();
                Bundle Map_Bundle = new Bundle();
                Map_Bundle.putParcelableArrayList("logm", loger);
                maploge.setArguments(Map_Bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frame, maploge)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
