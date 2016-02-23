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

import org.codehaus.jackson.map.util.JSONPObject;
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

//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        try {
//                            String profilePicId = object.optString("id");
//                            JSONObject jsonObject = object.optJSONObject("albums");
//                            JSONArray jsonArray = jsonObject.optJSONArray("data");
//                            int b = jsonArray.length();
//                            Log.d("Album Length", String.valueOf(b));
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject eachAlbum = jsonArray.optJSONObject(i);
//                                //String eachCoverPhotoId = eachAlbum.optJSONObject("cover_photo").optString("name");
//                                String eachCoverPhotoId = eachAlbum.optString("id");
//                                Log.d("Album Id", eachCoverPhotoId);
//                                String albumName = eachAlbum.optString("name");
//                                Log.d("Album name", albumName);
//                                String url = "https://graph.facebook.com/" + eachCoverPhotoId + "/picture?type=large";
//
//                                //String coverId = eachCoverPhoto.optString("name");
//                                //Log.d("Cover id", coverId);
//                                if (i == 2) {
//                                    profilePictureView.setProfileId(profilePicId);
//                                    profilePictureView2.setProfileId(eachCoverPhotoId);
//                                }
//                            }
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "albums");
//        request.setParameters(parameters);
//        request.executeAsync();
//        Bundle parameters = new Bundle();
//        //parameters.putString("fields", "albums");
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/10150642863150633",
//                parameters,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//                        try {
//                            Log.d("This is the response",response.toString());
//
//                            JSONArray dataArr = response.getJSONObject().getJSONArray("data");
////                            JSONObject singleAlbum = dataArr.getJSONObject(0);
////                            String albumName = singleAlbum.optString("name");
////                            Log.d("Album Name", albumName);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).executeAsync();

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
                                JSONObject currentAlbumPhotos = currentAlbum.optJSONObject("photos");
                                JSONArray currAlbumJsonArray = currentAlbumPhotos.optJSONArray(DATA_TAG);
                                int numOfPhotosCurrAlbum = currAlbumJsonArray.length();
                                for(int j = 0; j <numOfPhotosCurrAlbum; j++){
                                    JSONObject currPicture =currAlbumJsonArray.optJSONObject(j);

                                    c+=1;

                                    //String currentPictureId = currPicture.optString("id");
                                    JSONArray currentImageLink = currPicture.optJSONArray("images");
                                    //Log.d("Current Picture ID", currentPictureId);
                                    //for(int k = 0; k < currentImageLink.length(); k++){
                                        JSONObject jsonObject1 = currentImageLink.optJSONObject(0);
                                        String stringImageLink = jsonObject1.optString("source");
                                        Log.d("Image Link", stringImageLink);
                                    //}


                                    if(!b){
                                        ImageView user_picture;
                                        user_picture=(ImageView)findViewById(R.id.userpicture);
//                                        URL img_value = null;
//                                        img_value = new URL("http://graph.facebook.com/"+currentPictureId+"/picture?type=large");
//                                        Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
//                                        user_picture.setImageBitmap(mIcon1);


                                        //profilePictureView2.setProfileId(currentPictureId);
                                        b = true;


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
