package com.ayach.org.homefinder.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.Util.DownloadImage;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Login_Activity extends AppCompatActivity {
    LoginButton mBtnFb;
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    String email;
    String name;
    ParseUser parseUser;
    String profileid;
    DownloadImage dw = new DownloadImage();
    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        mBtnFb = (LoginButton) findViewById(R.id.login_button);
        loginbutton = (Button) findViewById(R.id.LoginParse);
        signup = (Button) findViewById(R.id.signup);
        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, com.parse.ParseException e) {
                                if (user != null) {
                                    saveNotify();
                                    Intent intent = new Intent(
                                            Login_Activity.this,
                                            Home_Activity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Login success",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No user exist please signup",
                                            Toast.LENGTH_LONG).show();
                                }

                            }

                        });
            }
        });
        mBtnFb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(Login_Activity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {

                        if (user == null) {
                            Log.d("Homefinder", "pas de session facebook");
                        } else if (user.isNew()) {
                            Log.d("Homefinder", "utilisateur ajouter!");
                            getUserDetailsFromFB();

                        } else {

                            Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                            startActivity(intent);
                        }
                    }


                });
            }
        });// Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();


                if (usernametxt.equals("") && passwordtxt.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                } else {

                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {

                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserDetailsFromFB() {
        GraphRequest graph = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null) {

                    try {
                        email = response.getJSONObject().getString("email");

                        name = response.getJSONObject().getString("name");
                        profileid = response.getJSONObject().getString("id");
                        Log.d("nom profile", profileid);
                        Log.d("mail", email);
                        Log.d("mail", name);
                        SavenewUser();
                    } catch (JSONException e) {
                        Log.d("EXCEPTION", e.getMessage() + " ");
                    }

                }

            }
        });



        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,picture,name");
        graph.setParameters(parameters);
        graph.executeAsync();

        Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
        startActivity(intent); ;
    }



        public void SavenewUser(){

    parseUser = ParseUser.getCurrentUser();
    parseUser.setUsername(name);
    parseUser.setEmail(email);
    byte [] image = DownloadImage(profileid);
    final ParseFile parseFile = new ParseFile("_image.jpg", image);
    parseFile.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            parseUser.put("Picture", parseFile);
            System.out.println("file   " + e);
            //Finally save all the user details
            parseUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    System.out.println("saved " + e);
                    Toast.makeText(Login_Activity.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });
            saveNotify();


}
public void saveNotify(){

    ParseUser usern= ParseUser.getCurrentUser();
    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
    installation.put("iduser", usern.getObjectId());
    installation.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            System.out.println("ok push       " + e);
        }
    });


}

public byte[] DownloadImage(String id){
     Bitmap bit =null;
    String imgUrl = "https://graph.facebook.com/"+profileid+"/picture?type=large";
    try {
        bit = dw.execute(imgUrl).get();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] byteArray = stream.toByteArray();

    return  byteArray;
}

        }