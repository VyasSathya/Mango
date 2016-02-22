package com.example.vyas.mymate3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.MapView;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class ElementActivity extends AppCompatActivity {

    TextView info;
    ChangeableAttributes changeableAttributes;

    MapView mMapView = (MapView) findViewById(R.id.map);
    //mMapView.enableWrapAround(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        firstTimeSetup();
        setContentView(R.layout.activity_element);
        info = (TextView)findViewById(R.id.info);
        if(accessToken!=null){
            info.setText(accessToken.getUserId());
        }
        else{
            //TODO
        }
    }

    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    protected void onResume() {
        super.onResume();
        mMapView.unpause();
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