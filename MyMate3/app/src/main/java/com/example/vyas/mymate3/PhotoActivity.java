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

//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        // Insert your code here
//                        JSONObject jsonObject = object.optJSONObject("albums");
//
//                            JSONArray jsonArray = jsonObject.optJSONArray("data");
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "albums");
//        request.setParameters(parameters);
//        request.executeAsync();
//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        //try {
//                            //This contains all the photos with array data>>link
//                            //JSONObject photosobject = object.getJSONObject("photos");
//                            //String id = object.optString("id");
//                            //Log.d("Id", id);
//                            JSONObject jsonObject = object.optJSONObject("albums");
//
//                            JSONArray jsonArray = jsonObject.optJSONArray("data");
////                            int length = jsonArray.length();
//                        //JSONObject jsonObject1 = jsonObject.optJSONObject("data");
//                        //int length = jsonObject1.length();
//                          //  Log.d("Number of albums", String.valueOf(length));
////                            for (int i = 0; i < jsonArray.length(); i++) {
////                                JSONObject obj1 = jsonArray.optJSONObject(i);
////                                JSONArray albumid = obj1.optString("id");
////                                Log.d("Album Id", albumid);
////                            }
//                        //}
//                    }});
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,album-id");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }


    }

    void hardCodeInfo(){
        //changeableAttributes = new ChangeableAttributes();
        //changeableAttributes.setHeight(5, 8);

    }
}
