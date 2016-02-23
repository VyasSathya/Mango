package com.example.vyas.mymate3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class ElementActivity extends AppCompatActivity {

    TextView info;
    ChangeableAttributes changeableAttributes;
    private GoogleMap mMap = null;

    //MapView mMapView = null;
    //mMapView.enableWrapAround(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AccessToken accessToken = AccessToken.getCurrentAccessToken();

        firstTimeSetup();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission Granted","automatic");
        } else {
            //TODO Show rationale and request permission.
            Log.d("Permission Granted", "manual");
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);

        Log.d("Location ",location.toString());



        setContentView(R.layout.activity_element);

    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    //This Function Checks for a First Time Setup and if so starts the profile setup process
    private void firstTimeSetup(){
        SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);
        changeableAttributes = new ChangeableAttributes();
        if(pref.getBoolean("firststart", true)){
            SharedPreferences.Editor editor = pref.edit();
            //TODO editor.putBoolean("firststart", false);
            editor.putBoolean("firststart", true);
            editor.apply();

            goToPhotos();
            goToQuestionnaire();
        }


        Log.d("Reading Saved Data", "Saved data:");
        SharedPreferences userDetails = ElementActivity.this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String firstName = userDetails.getString("first_name", "");
        String gender = userDetails.getString("gender", "");
        String age = userDetails.getString("age", "");


        Log.d("Reading Age", age);
        Log.d("Reading Gender", gender);
        Log.d("Reading First Name", firstName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0)
        {
            Bundle b = data.getExtras();
            changeableAttributes.setHeight(b.getInt("heightfeet"),b.getInt("heightinches"));
            changeableAttributes.setEducation("education");
            changeableAttributes.setOccupation("occupation");
            changeableAttributes.setReligion("religion");
            changeableAttributes.setCommunity("community");
        }
        else if(resultCode == RESULT_OK && requestCode == 1)
        {
            Bundle b = data.getExtras();

        }
    }

    // This function goes to the questionnaire
    private void goToQuestionnaire(){
        Intent intent = new Intent(this, QuestionnaireActivity.class);
        startActivityForResult(intent, 0);
    }

    private void goToPhotos(){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivityForResult(intent,1);
    }


}