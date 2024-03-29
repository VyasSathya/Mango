package com.example.vyas.mymate3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {


    private CallbackManager callbackManager;
    List<String> permissionNeeds= Arrays.asList("user_photos", "email", "user_birthday", "user_friends");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LoginButton loginButton;

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        final SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);
        if (isLoggedIn()){
            Toast.makeText(getApplicationContext(), "Already Logged in!", Toast.LENGTH_SHORT).show();

            if(pref.getBoolean("firststart", true)){
                firstTimeSetup();
            }
            else{
                goToElements();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Not Yet Logged in!", Toast.LENGTH_SHORT).show();
        }
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);


        TextView info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(permissionNeeds);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginManager.getInstance().logInWithReadPermissions(
                        MainActivity.this,
                        permissionNeeds);

                Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                if(pref.getBoolean("firststart", true)){
                    firstTimeSetup();
                }
                else{
                    goToElements();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Login Error!", Toast.LENGTH_SHORT).show();

            }
        });


    }



    private void goToElements(){
        Intent intent = new Intent(this, ElementActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;

    }

    private void firstTimeSetup(){
        SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        //TODO set bool to false
        editor.putBoolean("firststart", true);
        editor.apply();

        goToTabbed();


    }

    private void goToTabbed(){
        Intent intent = new Intent(this, TabbedQuestionActivity.class);
        startActivity(intent);
        finish();
    }


}
