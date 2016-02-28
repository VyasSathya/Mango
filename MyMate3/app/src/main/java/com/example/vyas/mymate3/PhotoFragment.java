package com.example.vyas.mymate3;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vyas on 2/26/2016.
 */
public class PhotoFragment extends Fragment  {



    List<List<String>> listOfLists = new ArrayList<List<String>>();
    List<String> albumTitle = new ArrayList<String>();
    List<String> albumCoverURL = new ArrayList<String>();
    List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    int currentStringNumber;
    ImageView imageView;
    //URL[] textURL = new URL[200];
    int urlsMade = 0;
    ArrayList<Bitmap> bmp = new ArrayList<Bitmap>();
    //Bitmap[] bmp = new Bitmap[200];
    int totalNumberOfPictures=0;
    ListView listView;

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public PhotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        fillFacebookURLS();
        Log.d("URL FILLED", "frog");



        Button button = (Button) rootView.findViewById(R.id.facebook_photo_button);
        final ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.imageButton);

//        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                listView = (ListView) rootView.findViewById(R.id.albumListView);
                Adapter adapter = new Adapter (getContext(), albumTitle, bitmapList);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(),albumTitle.get(position),Toast.LENGTH_LONG).show();
                        albumFragment fragment2 = new albumFragment();
                        Bundle value= new Bundle();
                        value.putStringArrayList("urls",(ArrayList<String>)listOfLists.get(position) );
                        fragment2.setArguments(value);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(android.R.id.content, fragment2);
                        fragmentTransaction.commit();
                    }
                });



                try {

                    imageButton.setImageBitmap( new MyTask().execute(listOfLists.get(0).get(0)).get());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        return rootView;
    }





    private void fillFacebookURLS(){
        final String DATA_TAG = "data";

        Log.d("Permissions", AccessToken.getCurrentAccessToken().getPermissions().toString());
        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{name,photos{id,images}}");
        currentStringNumber =0;

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
                                            c += 1;
                                            JSONArray currentImageLink = currPicture.optJSONArray("images");
                                            JSONObject jsonObject1 = currentImageLink.optJSONObject(0);
                                            String stringImageLink = jsonObject1.optString("source");
                                            Log.d("Image Link", stringImageLink);

                                                if(j==0){
                                                    albumCoverURL.add(i,stringImageLink);
                                                    albumTitle.add(i,currentAlbum.optString("name"));
                                                }
                                            tempList.add(j,stringImageLink);
                                        }
                                    }
                                    else {
                                        i++;
                                    }
                                    listOfLists.add(i,tempList);
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
        request2.executeAsync();
    }

    public class MyTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bp = null;
            URL temp = null;
            try {
                temp = new URL(params[0]);
                //textURL.set(urlsMade, temp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                assert temp != null;
                //bmp.add(urlsMade, BitmapFactory.decodeStream(temp.openConnection().getInputStream()));
                bp = BitmapFactory.decodeStream(temp.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //urlsMade++;
            //return bmp.get(urlsMade);
            return bp;
        }

        @Override
        protected  void onPostExecute(Bitmap b)
        {
            super.onPostExecute(b);
        }

    }

//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            // Log exception
//            return null;
//        }
//    }



}


//class DownloadTask extends AsyncTask<String,Integer,String>
//{
//    ProgressDialog progressDialog;
//
//    @Override
//    protected void onPreExecute(){
//
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Download In Progress");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setMax(100);
//        progressDialog.setProgress(0);
//        progressDialog.show();
//    }
//
//
//    @Override
//    protected String doInBackground(String... params){
//
//        String path = params[0];
//        int file_length = 0;
//
//        try {
//            URL url = new URL(path);
//            URLConnection urlConnection = url.openConnection();
//            urlConnection.connect();
//            file_length = urlConnection.getContentLength();
//            File new_folder = new File("sdcard/photoalbum");
//            if(!new_folder.exists()){
//                new_folder.mkdir();
//            }
//            else {
//                Log.d("directory failed", "to make");
//            }
//
//
//            File input_file = new File(new_folder,"downloaded_image.jpg");
//            InputStream inputStream = new BufferedInputStream(url.openStream(),150000);
//            byte[] data = new byte[1024];
//            int total = 0;
//            int count = 0;
//            OutputStream outputStream = new FileOutputStream(input_file);
//            while((count = inputStream.read(data))!=-1){
//                total +=count;
//                outputStream.write(data,0,count);
//                int progress = (int)total*100/file_length;
//                publishProgress(progress);
//            }
//
//
//
//            inputStream.close();
//            outputStream.close();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        return "Download complete";
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values){
//        progressDialog.setProgress(values[0]);
//    }
//
//    @Override
//    protected void onPostExecute(String result){
//        progressDialog.hide();
//        Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
//        String path = "sdcard/photoalbum/downloaded_image.jpg";
//        imageView.setImageDrawable(Drawable.createFromPath(path));
//        //imageView.setImageDrawable(Drawable.createFromStream(inputStream,"My Picture"));
//
//    }
//
//
//
//}

