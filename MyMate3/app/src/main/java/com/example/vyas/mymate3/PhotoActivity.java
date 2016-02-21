package com.example.vyas.mymate3;

import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_photo);
        //hardCodeInfo();


        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            //This contains all the photos with array data>>link
                            //JSONObject photosobject = object.getJSONObject("photos");
                            String id = object.optString("id");
                            Log.d("Id", id);


                            ProfilePictureView pictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
                            pictureView.setProfileId(id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture,photos{link}");
        request.setParameters(parameters);
        request.executeAsync();




        Button finishSetup = (Button) findViewById(R.id.finishSetupButton);
        finishSetup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MyApp", "I am exiting photo activity");
                Intent intent =  new Intent();
                Bundle bp = new Bundle();
                //bp.putString("education", changeableAttributes.getEducation());
                intent.putExtras(bp);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }




    void hardCodeInfo(){
        //changeableAttributes = new ChangeableAttributes();
        //changeableAttributes.setHeight(5, 8);

    }
}
