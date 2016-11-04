package com.ayach.org.homefinder.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ayach.org.homefinder.Fragments.DetailFragment;
import com.ayach.org.homefinder.Fragments.List_rechercheFragment;
import com.ayach.org.homefinder.Fragments.Map_All_Logement;
import com.ayach.org.homefinder.Fragments.Logement_Map;
import com.ayach.org.homefinder.Fragments.Favoris_fragment;
import com.ayach.org.homefinder.Fragments.Mes_Logement_Fragment;
import com.ayach.org.homefinder.Fragments.Recherche_Fragment;
import com.ayach.org.homefinder.Fragments.Reservation_Fragment;
import com.ayach.org.homefinder.Fragments.Welcom_Fragment;
import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.Util.DialogFilterFragment;
import com.ayach.org.homefinder.Util.FeedBack_Dialog;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , Welcom_Fragment.OnFragmentInteractionListener,
        DialogFilterFragment.OnFragmentInteractionListener,
        Logement_Map.OnFragmentInteractionListener
        ,Recherche_Fragment.OnFragmentInteractionListener,Mes_Logement_Fragment.OnFragmentInteractionListener
        ,DetailFragment.OnFragmentInteractionListener,List_rechercheFragment.OnFragmentInteractionListener,
        Map_All_Logement.OnFragmentInteractionListener,
    Favoris_fragment.OnFragmentInteractionListener,FeedBack_Dialog.OnFragmentInteractionListener
    ,Reservation_Fragment.OnFragmentInteractionListener

{   ParseUser currentUser;
    TextView nameduser;
    TextView emailuser;
    String struser;
    String mailuser;
    Intent intent;
    CircleImageView circleImageView ;;
 Bitmap bit ;
    public static Bundle photoBundle=new Bundle();
    public static Bundle searchBunle =new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentUser = ParseUser.getCurrentUser();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        mailuser = currentUser.getEmail();
        struser = currentUser.getUsername();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame, new Welcom_Fragment())
                .commit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_head);
        nameduser = (TextView) header.findViewById(R.id.nameuser);
       navigationView.setItemIconTintList(null);
        emailuser = (TextView)header.findViewById(R.id.mailuser);
        circleImageView = (CircleImageView) header.findViewById(R.id.profile_picture);
        try {
            nameduser.setText(struser);
            emailuser.setText(mailuser);
            ParseFile parseFile =currentUser.getParseFile("Picture");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            circleImageView.setImageBitmap(bitmap);


        }catch (Exception e){}
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DisplayFragment(id);
        return true;

    }

    public void DisplayFragment(int viewId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        switch (viewId) {
            case R.id.nav_Home:
    ft.replace(R.id.frame, new Welcom_Fragment())
            .commit();
                break;
            case R.id.nav_Add:
                if(this.NetworkAvailable()) {
                intent = new Intent(Home_Activity.this, Add_Activity.class);
                startActivity(intent);
                finish();
                }
                else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No internet!")
                            .show();
                }
                break;
            case R.id.nav_recherche:
                if(this.NetworkAvailable()) {

                    ft.replace(R.id.frame, new Recherche_Fragment())
                            .commit();
                }
                else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No internet!")
                            .show();
                }
                break;
            case R.id.nav_reserva:
                if(this.NetworkAvailable()) {

                    ft.replace(R.id.frame, new Reservation_Fragment())
                            .commit();
                }
                else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No internet!")
                            .show();
                }
                break;
            case R.id.nav_My:
                if(this.NetworkAvailable()) {

                    ft.replace(R.id.frame, new Mes_Logement_Fragment())
                            .commit();
                }
                else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No internet!")
                            .show();
                }
                break;
            case R.id.nav_con:
            // ParseFacebookUtils.getSession().closeAndClearTokenInformation();
                ParseUser.logOut();
                disconnectFromFacebook();
                intent = new Intent(Home_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
                break;
           // case R.id.nav_user:
                //  fm.beginTransaction().replace(R.id.frame, new Recherche_Fragment()).commit();
            //    break;
            case R.id.favori:
                ft.replace(R.id.frame, new Favoris_fragment())
                        .commit();
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);

    }
    public boolean NetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    }







