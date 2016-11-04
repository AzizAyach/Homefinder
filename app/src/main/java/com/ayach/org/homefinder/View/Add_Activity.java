package com.ayach.org.homefinder.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ayach.org.homefinder.DAO.AddLogement;
import com.ayach.org.homefinder.Entity.Attr;
import com.ayach.org.homefinder.Entity.Location;
import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.Entity.Price;
import com.ayach.org.homefinder.Fragments.DetailFragment;
import com.ayach.org.homefinder.Fragments.Logement_Map;
import com.ayach.org.homefinder.Fragments.Favoris_fragment;
import com.ayach.org.homefinder.Fragments.Map_All_Logement;
import com.ayach.org.homefinder.Fragments.Mes_Logement_Fragment;
import com.ayach.org.homefinder.Fragments.Recherche_Fragment;
import com.ayach.org.homefinder.Fragments.Reservation_Fragment;
import com.ayach.org.homefinder.Fragments.Welcom_Fragment;
import com.ayach.org.homefinder.Page.ListPageAdd;
import com.ayach.org.homefinder.Page.PageName;
import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.Util.DownloadImage;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseUser;
import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;
import com.tech.freak.wizardpager.ui.ReviewFragment;
import com.tech.freak.wizardpager.ui.StepPagerStrip;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Add_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks, Logement_Map.OnFragmentInteractionListener
        , Recherche_Fragment.OnFragmentInteractionListener, Welcom_Fragment.OnFragmentInteractionListener,
        Mes_Logement_Fragment.OnFragmentInteractionListener
        , DetailFragment.OnFragmentInteractionListener,
        Map_All_Logement.OnFragmentInteractionListener,
        Favoris_fragment.OnFragmentInteractionListener
        , Reservation_Fragment.OnFragmentInteractionListener {
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    String rename;
    Intent intent;
    private boolean mEditingAfterReview;
    public  static DialogFragment dg;
    public static Bundle photoBundle = new Bundle();
    public static Bundle MapBundle = new Bundle();
    public static Bundle BundleAttr = new Bundle();
    public static Bundle Locationbundle = new Bundle();
    public static Bundle Price_Bundle = new Bundle();
    private AbstractWizardModel mWizardModel = new ListPageAdd(this);

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    Price prix = new Price();
    Attr att = new Attr();
    Location loc = new Location();
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    List<byte[]> lsimage = new ArrayList<byte[]>();
    ArrayList<Double> po = new ArrayList<Double>();
    AddLogement add;
    Logement l;
    ParseUser user;
    TextView nameduser;
    TextView emailuser;
    String struser;
    String mailuser;
    CircleImageView circleImageView;
    Profile profile;
    Bitmap bit;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ParseUser currentUser = ParseUser.getCurrentUser();

        profile = Profile.getCurrentProfile();
        mailuser = currentUser.getEmail();
        struser = currentUser.getUsername();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_Add);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip
                .setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
                    @Override
                    public void onPageStripSelected(int position) {
                        position = Math.min(mPagerAdapter.getCount() - 1,
                                position);
                        if (mPager.getCurrentItem() != position) {
                            mPager.setCurrentItem(position);
                        }
                    }
                });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    Log.d("Bundle", String.valueOf(photoBundle));
                    prix = Add_Activity.Price_Bundle.getParcelable("Price");
                    LatLng position = Add_Activity.MapBundle.getParcelable("position");
                    att = Add_Activity.BundleAttr.getParcelable("Attr");
                    rename = mWizardModel.findByKey("Name of Logement").getData().getString(PageName.Name_Logement);
                    bitmapArray = Add_Activity.photoBundle.getParcelableArrayList("Photo");
                    try {
                        po.add(position.latitude);
                        po.add(position.longitude);
                        lsimage = ConvertBitmap(bitmapArray);

                    } catch (Exception e) {
                    }
                    Log.d("", "" + lsimage);
                    System.out.println("ajouter avec succes" + lsimage);
                    Log.d("Bundle get", Home_Activity.photoBundle.toString());
                    loc = Add_Activity.Locationbundle.getParcelable("Location");
                    user = ParseUser.getCurrentUser();

                 /*   dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage("Add Your Logement")
                                    .setPositiveButton(
                                            "Confirmed",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    List<Photo> phu = new ArrayList<Photo>();
                                                    List<ParseFile> photoflile = new ArrayList<ParseFile>();
                                                    ArrayList<String> phlist = new ArrayList<String>();
                                                    try {
                                                        for (byte[] b : lsimage) {
                                                            ParseFile file = new ParseFile("Photo.jpg", b);
                                                            file.save();
                                                            String c = file.getUrl();
                                                            Log.d("url image", c);
                                                            Photo p = new Photo();
                                                            p.setMedium(c);
                                                            p.setCaption(c);
                                                            p.setSmall(c);
                                                            p.setLarge(c);
                                                            p.setXlarge(c);
                                                            phu.add(p);
                                                            Gson g = new Gson();
                                                            String gf = g.toJson(p);
                                                            phlist.add(gf);
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                    Gson gp = new Gson();
                                                    Logement l = new Logement(rename, po, att, prix, phu, loc, null, user.getObjectId());
                                                    String src = gp.toJson(l);
                                                    add = new AddLogement(Add_Activity.this);
                                                    System.out.println(src);
                                                    add.execute(src);
                                                    Intent intent = new Intent(Add_Activity.this, Home_Activity.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            })
                                    .setNegativeButton(android.R.string.cancel,
                                            null).create();

                        }
                    };
                    dg.show(getSupportFragmentManager(), "place_order_dialog");*/
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);

                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_head);
        nameduser = (TextView) header.findViewById(R.id.nameuser);
        navigationView.setItemIconTintList(null);
        emailuser = (TextView) header.findViewById(R.id.mailuser);
        circleImageView = (CircleImageView) header.findViewById(R.id.profile_picture);
        String result = null;
        if (profile != null) {
            result = profile.getId();

        } else {
            if (currentUser.get("idfacebook") != null) {
                result = currentUser.get("idfacebook").toString();

            } else {
            }


        }
        try {

            emailuser.setText(mailuser);
            nameduser.setText(struser);
            DownloadImage dw = new DownloadImage();
            String imgUrl = "https://graph.facebook.com/" + result + "/picture?type=large";
            bit = dw.execute(imgUrl).get();
            circleImageView.setImageBitmap(bit);
        } catch (Exception e) {
        }
        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 =
        // review
        // step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText("finish");

        } else {
            mNextButton.setText("previw");

            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
                    true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton
                .setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    public List<byte[]> ConvertBitmap(ArrayList<Bitmap> bt) {
        List<byte[]> ls = new ArrayList<byte[]>();
        for (Bitmap b : bt) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imgData = stream.toByteArray();

            ls.add(imgData);
        }

        return ls;


    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    public void DisplayFragment(int viewId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        switch (viewId) {
            case R.id.nav_Home:
                intent = new Intent(Add_Activity.this, Home_Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_Add:

                break;
            case R.id.nav_recherche:
                ft.replace(R.id.frame, new Recherche_Fragment())
                        .commit();
                break;
            case R.id.nav_My:
                ft.replace(R.id.frame, new Mes_Logement_Fragment())
                        .commit();
                break;
            case R.id.nav_con:
                ParseUser.logOut();
                disconnectFromFacebook();
                intent = new Intent(Add_Activity.this, Login_Activity.class);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_Add);
        drawer.closeDrawer(GravityCompat.START);
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

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment ph = getSupportFragmentManager().getFragments().get(mPager.getCurrentItem() - 3);
        if (ph != null) {
            ph.onActivityResult(requestCode, resultCode, data);
        } else {
            System.out.println("problem retour");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Add_ Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, mCurrentPageSequence == null ? 1
                    : mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
