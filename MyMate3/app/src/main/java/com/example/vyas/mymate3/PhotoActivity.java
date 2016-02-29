package com.example.vyas.mymate3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.ProfilePictureView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhotoActivity extends Activity {

    List<List<String>> listOfLists = new ArrayList<List<String>>();
    List<String> albumTitle = new ArrayList<String>();
    List<String> albumCoverURL = new ArrayList<String>();
    List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    int totalNumberOfPictures=0;
    ListView listView;
    boolean isFilled = false;
    Bundle bundle;
    int reqCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FacebookSdk.sdkInitialize(getApplicationContext());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        fillFacebookURLS();

        //SystemClock.sleep(7000);


        for(int i = 0; i< listOfLists.size(); i++) {
            Log.d("Current Album Title:", albumTitle.get(i));

            for (int j = 0; j < listOfLists.get(i).size(); j++) {
                Log.d("Current URL from LoL:", listOfLists.get(i).get(j));

            }
        }

        for(int i = 0; i<albumCoverURL.size(); i++)
        {
            try {
                bitmapList.add(i, new MyTask().execute(albumCoverURL.get(i)).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }




        Adapter adapter = new Adapter (PhotoActivity.this, albumTitle, bitmapList);
        listView = (ListView) findViewById(R.id.albumListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PhotoActivity.this, albumTitle.get(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PhotoActivity.this, albumActivity.class);
                bitmapList.clear();

                Bundle value = new Bundle();
                value.putStringArrayList("urls", (ArrayList<String>) listOfLists.get(position));
                intent.putExtras(value);
                startActivityForResult(intent, reqCode);

            }
        });






    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode,event);
    }



    private void fillFacebookURLS(){
        final String DATA_TAG = "data";

        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{name,photos{id,images}}");

        GraphRequest request2 = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.d("JSON OBJECT", object.toString());
                            JSONObject jsonObject = object.optJSONObject("albums");
                            JSONArray jsonArray = jsonObject.optJSONArray(DATA_TAG);
                            int numOfAlbums = jsonArray.length();
                            int c = 0;
                            for (int i = 0; i < numOfAlbums; i++) {
                                List<String> tempList = new ArrayList<String>();
                                JSONObject currentAlbum = jsonArray.optJSONObject(i);
                                if (currentAlbum.optJSONObject("photos") != null) {
                                    JSONObject currentAlbumPhotos = currentAlbum.optJSONObject("photos");
                                    JSONArray currAlbumJsonArray = currentAlbumPhotos.optJSONArray(DATA_TAG);
                                    int numOfPhotosCurrAlbum = currAlbumJsonArray.length();
                                    if (numOfPhotosCurrAlbum > 0) {
                                        for (int j = 0; j < numOfPhotosCurrAlbum; j++) {
                                            JSONObject currPicture = currAlbumJsonArray.optJSONObject(j);
                                            JSONArray currentImageLink = currPicture.optJSONArray("images");
                                            JSONObject jsonObject1 = currentImageLink.optJSONObject(0);
                                            String stringImageLink = jsonObject1.optString("source");
                                            if(j==0){
                                                albumCoverURL.add(i,stringImageLink);
                                                albumTitle.add(i,currentAlbum.optString("name"));
                                            }
                                            tempList.add(j,stringImageLink);
                                            //SystemClock.sleep(10);
                                            c++;
                                        }
                                    }
                                    else if(i < numOfAlbums-1) {
                                        i++;
                                    }
                                    listOfLists.add(i,tempList);
                                    //SystemClock.sleep(500);
                                }

                            }
                            Log.d("Number of Pictures", String.valueOf(c));
                            totalNumberOfPictures = c;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        request2.setParameters(parameters);
        request2.executeAndWait();
        isFilled = true;
    }

    private class MyTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bp = null;
            URL temp = null;
            try {
                temp = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                assert temp != null;
                bp = BitmapFactory.decodeStream(temp.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bp;
        }

        @Override
        protected  void onPostExecute(Bitmap b)
        {
            super.onPostExecute(b);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            setResult(Activity.RESULT_OK,data);
            finish();
            System.exit(0);

    }

}

