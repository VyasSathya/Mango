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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends Activity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (isLoggedIn()){
            Toast.makeText(getApplicationContext(), "Already Logged in!", Toast.LENGTH_SHORT).show();
            goToElements();
        }
        else{
            Toast.makeText(getApplicationContext(), "Not Yet Logged in!", Toast.LENGTH_SHORT).show();
        }
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);


        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("user_photos"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginManager.getInstance().logInWithReadPermissions(
                        MainActivity.this,
                        Arrays.asList("user_photos"));
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                JSONObject jsonObject = object.optJSONObject("albums");
                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                int b = jsonArray.length();
                                Log.d("Album Length",String.valueOf(b));
                                for(int i=0; i<jsonArray.length();i++){
                                    JSONObject eachAlbum = jsonArray.optJSONObject(i);
                                    String albumName = eachAlbum.optString("name");
                                    Log.d("Album Id", albumName);
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "albums");
                request.setParameters(parameters);
                request.executeAsync();
                Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                goToElements();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        //boolean handled = callbackManager.onActivityResult(requestCode, resultCode, data);
        //if (handled) { Toast.makeText(getApplicationContext(), "Callback Handled", Toast.LENGTH_SHORT).show(); }
        //else { Toast.makeText(getApplicationContext(), "Failed to Handle Callback", Toast.LENGTH_SHORT).show(); }
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


}
