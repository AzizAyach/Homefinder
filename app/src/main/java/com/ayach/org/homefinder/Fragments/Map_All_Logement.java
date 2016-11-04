package com.ayach.org.homefinder.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Map_All_Logement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Map_All_Logement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Map_All_Logement extends android.support.v4.app.Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    private GoogleMap googleMap;
    private ArrayList<Logement> logements = new ArrayList<Logement>();
    Logement logemap = new Logement();
    Map<Marker,Logement> map = new HashMap<>();
    LatLng markerloge = null;
    List<Double> position;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Map_All_Logement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Map_All_Logement.
     */
    // TODO: Rename and change types and number of parameters
    public static Map_All_Logement newInstance(String param1, String param2) {
        Map_All_Logement fragment = new Map_All_Logement();
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
        View v = inflater.inflate(R.layout.fragment_map__all__logement, container, false);
        logements = getArguments().getParcelableArrayList("logm");
        mMapView = (MapView) v.findViewById(R.id.mapViewarray);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapsInitializer.initialize(getActivity().getApplicationContext());
                getMylocation();
                BitmapDescriptor bit = BitmapDescriptorFactory.fromResource(R.drawable.homeicon);
                for (final Logement loge : logements) {
                    try {
                        position = loge.getLatLng();
                        markerloge = new LatLng(position.get(0), position.get(1));
                        String title = loge.getId();
                        Marker logeMark = googleMap.addMarker(new MarkerOptions().position(markerloge).title(title).icon(bit));
                        map.put(logeMark, loge);
                    }
                    catch (Exception e){}

                }
                googleMap.setInfoWindowAdapter(new infoWindowGoogle());
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Logement logedet = map.get(marker);
                        DetailFragment dl = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("maploge", logemap);
                        dl.setArguments(bundle);
                        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.frame, dl)
                                .commit();
                    }
                });
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

    public void getMylocation() {

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else {
            try {
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location location) {
                        location.getLongitude();
                        location.getLatitude();
                        System.out.println("location");
                        LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l, 10.0f));
                    }


                });
            }
            catch (Exception e){}
        }
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

                    android.support.v4.app.FragmentManager mg = getFragmentManager();
                    mg.beginTransaction().replace(R.id.frame, new Welcom_Fragment(), "welcom").commit();
                    return true;

                }

                return false;
            }
        });}
    public class infoWindowGoogle implements GoogleMap.InfoWindowAdapter {
        View contenu ;

        public infoWindowGoogle(){



        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            logemap =map.get(marker);
            LayoutInflater inflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contenu = inflater.inflate(R.layout.info_window_google,null);
            ImageView img = (ImageView)contenu.findViewById(R.id.infoimage);
            String photo = logemap.getPhotos().get(0).getLarge();
            Picasso.with(getActivity().getApplicationContext()).load(photo).into(img);
            TextView adresse = ((TextView)contenu.findViewById(R.id.adresseinfo));
            adresse.setText(logemap.getLocation().getStreetName());
            TextView prix = ((TextView)contenu.findViewById(R.id.prixinfo));
            prix.setText(logemap.getPrice().getNightly()+"$");
            Button bnt = (Button) contenu.findViewById(R.id.btn_more);
return  contenu;
        }

    }

}
