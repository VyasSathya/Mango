package com.example.vyas.mymate3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.ProfilePictureView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String DATA_TAG = "data";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        final ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        final ProfilePictureView profilePictureView2 = (ProfilePictureView) findViewById(R.id.profilePicture2);
        Button buttonFinish = (Button) findViewById(R.id.finishSetupButton);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                //TODO finishActivity() passing pictures
            }
        });

        Log.d("Permissions", AccessToken.getCurrentAccessToken().getPermissions().toString());
        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{photos{id,images}}");


        GraphRequest request2 = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.d("JSON OBJECT", object.toString());
                            boolean b = false;
                            JSONObject jsonObject = object.optJSONObject("albums");
                            JSONArray jsonArray = jsonObject.optJSONArray(DATA_TAG);
                            int numOfAlbums = jsonArray.length();
                            int c = 0;
                            for(int i = 0; i < numOfAlbums; i++){
                                JSONObject currentAlbum = jsonArray.optJSONObject(i);

                                if (currentAlbum.optJSONObject("photos")!= null) {
                                    JSONObject currentAlbumPhotos = currentAlbum.optJSONObject("photos");
                                    JSONArray currAlbumJsonArray = currentAlbumPhotos.optJSONArray(DATA_TAG);
                                    int numOfPhotosCurrAlbum = currAlbumJsonArray.length();
                                    for(int j = 0; j <numOfPhotosCurrAlbum; j++){
                                        JSONObject currPicture =currAlbumJsonArray.optJSONObject(j);
                                        c+=1;

                                        JSONArray currentImageLink = currPicture.optJSONArray("images");
                                            JSONObject jsonObject1 = currentImageLink.optJSONObject(0);
                                            String stringImageLink = jsonObject1.optString("source");
                                            Log.d("Image Link", stringImageLink);

                                        if(!b) {

                                            b = true;

                                        }
                                    }
                                }

                            }
                            Log.d("Number of Pictures", String.valueOf(c));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        request2.setParameters(parameters);
        request2.executeAsync();

    }



}
