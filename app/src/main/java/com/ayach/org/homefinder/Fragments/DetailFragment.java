package com.ayach.org.homefinder.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ayach.org.homefinder.Adapter.Adpterimage;
import com.ayach.org.homefinder.DAO.Favoris_Dao;
import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.Entity.Photo;
import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.Util.FeedBack_Dialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
Logement loge = new Logement();
    ParseUser user;
    ViewPager viewPager;
    MapView mMapView;
    private GoogleMap googleMap;
    CirclePageIndicator circlePageIndicator;
    Adpterimage adpterimage;
    ArrayList<String> url =new ArrayList<>();
    TextView titre ;
    TextView pays;
    TextView gouvert;
    TextView adresse;
    TextView roomnb;
    TextView bednb;
    TextView toilnb;
    TextView prnight;
    TextView prweek;
    TextView prmounth;
    TextView prmax;
    TextView prmin;
    TextView descritpion;
    ImageButton share;
ImageButton favoris;
    Favoris_Dao db;
    Bitmap imgshare ;
    android.support.v4.app.FragmentManager fm;
    FloatingActionButton FAB;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CallbackManager callbackManager;
    private LoginManager manager;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
       View v = inflater.inflate(R.layout.fragment_detail, container, false);
        db = new Favoris_Dao(getActivity().getApplicationContext());
        titre = (TextView)v.findViewById(R.id.titrelog);
        pays = (TextView)v.findViewById(R.id.paysloge);
        gouvert = (TextView)v.findViewById(R.id.gouvloge);
        adresse =(TextView)v.findViewById(R.id.cityloge);
        roomnb = (TextView)v.findViewById(R.id.roomloge);
        bednb = (TextView)v.findViewById(R.id.bedloge);
        toilnb = (TextView)v.findViewById(R.id.toilloge);
        descritpion =(TextView)v.findViewById(R.id.desc_detail);
        prnight = (TextView)v.findViewById(R.id.nightlytex);
        prweek = (TextView)v.findViewById(R.id.weeklytex);
        prmounth =(TextView)v.findViewById(R.id.mounthlytex);
        prmax = (TextView)v.findViewById(R.id.mxitex);
        prmin = (TextView)v.findViewById(R.id.minntex);
        favoris = (ImageButton) v.findViewById(R.id.favorid);
        share = (ImageButton) v.findViewById(R.id.share);


        mMapView = (MapView) v.findViewById(R.id.mapViewdetail);

        try {
            mMapView.onCreate(savedInstanceState);
        }
        catch (Exception e){}

        viewPager=(ViewPager)v.findViewById(R.id.detailpager);
        circlePageIndicator=(CirclePageIndicator)v.findViewById(R.id.indicator);
        List<Double> position ;
        LatLng markerloge =null ;
        try {

            if(getArguments().getParcelable("logement")!=null){
                loge = getArguments().getParcelable("logement") ;

            }
            else{
                loge = getArguments().getParcelable("maploge");


            }



            titre.setText(loge.getId());
            pays.setText(loge.getLocation().getCountry());
            gouvert.setText(loge.getLocation().getState());
            adresse.setText(loge.getLocation().getCity());
            roomnb.setText(""+loge.getAttr().getBedrooms());
            bednb.setText(""+loge.getAttr().getBeds());
            toilnb.setText(""+loge.getAttr().getBathrooms());
            descritpion.setText(""+loge.getAttr().getDescription());
            prnight.setText(""+loge.getPrice().getNightly()+"$");
            prweek.setText(""+loge.getPrice().getWeekly()+"$");
            prmounth.setText(""+loge.getPrice().getMonthly()+"$");
            prmax.setText(""+loge.getPrice().getMaxNight()+"$");
            prmin.setText(""+loge.getPrice().getMinNight()+"$");

            position = loge.getLatLng();
            markerloge = new LatLng(position.get(0), position.get(1));
        }
        catch (Exception e){System.out.println("erreur chargement Logement");}
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        for(Photo p : loge.getPhotos())
        {
          String s=  p.getLarge();
            url.add(s);

        }
        fm = getActivity().getSupportFragmentManager();
        adpterimage = new Adpterimage(getActivity().getApplicationContext(),url);
        viewPager.setAdapter(adpterimage);
        circlePageIndicator.setViewPager(viewPager);
       try {
           googleMap.getUiSettings().setScrollGesturesEnabled(false);
           googleMap.getUiSettings().setZoomControlsEnabled(false);
       }
       catch (Exception e){}
        String title = loge.getId();
        try {
            final Marker markloge = googleMap.addMarker(new MarkerOptions().position(markerloge).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.homeicon)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerloge, 15.0f));
        }
        catch (Exception e){}

        favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    try {
        db.addLogement(loge);
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Homefinder")
                .setContentText("Add to Favorite!")
                .show();
    }
    catch(SQLiteException e){
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Homefinder")
                .setContentText("already exist in favorite list ")
                .setConfirmText("Ok!")
                .show();

    }
            }
        });
       user= ParseUser.getCurrentUser();
        FAB = (FloatingActionButton) v.findViewById(R.id.fab);
        if(user.getObjectId().equals(loge.getIduser()))
        {FAB.setVisibility(View.GONE);}

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Would you like a coffee?", Toast.LENGTH_SHORT).show();
                FeedBack_Dialog Df = new FeedBack_Dialog();
                Bundle detBundle = new Bundle();
                detBundle.putParcelable("logement", loge);
                Df.setArguments(detBundle);
                Df.show(fm, "DialogFragment");
            }
        });



//





        final android.support.v4.app.Fragment f = this;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callbackManager = CallbackManager.Factory.create();

                List<String> permissionNeeds = Arrays.asList("publish_actions");


                manager = LoginManager.getInstance();

                manager.logInWithPublishPermissions(f,permissionNeeds);

                manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        sharePhotoToFacebook();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError  "+exception);
                    }
                  });



            }
        });
        return  v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        });
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
    public void share()
    {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(loge.getId())
                .setContentDescription(loge.getAttr().getDescription())
                .setImageUrl(Uri.parse(loge.getPhotos().get(0).getLarge()))
                .build();

        ShareApi.share(content, new FacebookCallback<Sharer.Result>(){
            @Override
            public void onSuccess(Sharer.Result result){

            }
            @Override
            public void onCancel(){

            }
            @Override
            public void onError(FacebookException error){

            }
        });
    }
    private void sharePhotoToFacebook() {
        imgshare = getBitmapFromURL(loge.getPhotos().get(0).getLarge());
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(imgshare)
                .setCaption("#HomeFinder  "+loge.getId())
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)

                .build();

        ShareApi.share(content, null);

    }
    public Bitmap getBitmapFromURL(String img) {
        try {

            URL url = new URL(img);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (IOException e) {
            // Log exception
            return null;
        }
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
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
