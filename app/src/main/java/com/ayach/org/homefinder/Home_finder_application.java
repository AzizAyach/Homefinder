package com.ayach.org.homefinder;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by aziz on 18/11/2015.
 */
public class Home_finder_application extends MultiDexApplication{

    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "Faz4ThhQoEGxPNcb6gfiEIKlmfxM67ImsJzOaCLf","IEPKjdZHocCWjqWmV5adoTguIXi43LclGKrenedU");
        ParseUser.enableRevocableSessionInBackground();
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseFacebookUtils.initialize(this);
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.esprit.org.homefinder",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
