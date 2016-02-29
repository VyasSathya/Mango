package com.example.vyas.mymate3;

/**
 * Created by Vyas on 2/28/2016.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vyas on 2/25/2016.
 */
public class FillPhotosFragment extends Fragment {

    String[] PHOTO_RESOURCES = {"Facebook","Choose from Device","Take Photo"};
    ImageButton imageButton1 = null;
    ImageButton imageButton2 = null;
    ImageButton imageButton3 = null;
    ImageButton imageButton4 = null;
    ImageButton imageButton5 = null;
    ImageButton imageButton6 = null;
    ImageButton imageButton7 = null;
    ImageButton checkButton = null;


    public static FillPhotosFragment newInstance() {
        FillPhotosFragment fragment = new FillPhotosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FillPhotosFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        final View rootView = inflater.inflate(R.layout.fragment_fill_photos, container, false);

        imageButton1 = (ImageButton) rootView.findViewById(R.id.choosePhoto1);
        imageButton2 = (ImageButton) rootView.findViewById(R.id.choosePhoto2);
        imageButton3 = (ImageButton) rootView.findViewById(R.id.choosePhoto3);
        imageButton4 = (ImageButton) rootView.findViewById(R.id.choosePhoto4);
        imageButton5 = (ImageButton) rootView.findViewById(R.id.choosePhoto5);
        imageButton6 = (ImageButton) rootView.findViewById(R.id.choosePhoto6);
        imageButton7 = (ImageButton) rootView.findViewById(R.id.choosePhoto7);
        checkButton = (ImageButton) rootView.findViewById(R.id.checkbox);

        SharedPreferences userDetailsCreate = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
        final SharedPreferences.Editor editorCreate = userDetailsCreate.edit();


        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton1.getDrawable() == null) {
                    showAlert(v, 1);
                }
                else{
                    imageButton1.setImageDrawable(null);
                    editorCreate.remove("Code1");
                    editorCreate.apply();
                }

            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton2.getDrawable() == null) {
                    showAlert(v, 2);
                }
                else{
                    imageButton2.setImageDrawable(null);
                    editorCreate.remove("Code2");
                    editorCreate.apply();
                }

            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton3.getDrawable() == null) {
                    showAlert(v, 3);
                }
                else{
                    imageButton3.setImageDrawable(null);
                    editorCreate.remove("Code3");
                    editorCreate.apply();
                }

            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton4.getDrawable() == null) {
                    showAlert(v, 4);
                }
                else{
                    imageButton4.setImageDrawable(null);
                    editorCreate.remove("Code4");
                    editorCreate.apply();
                }

            }
        });

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton5.getDrawable() == null) {
                    showAlert(v, 5);
                }
                else{
                    imageButton5.setImageDrawable(null);
                    editorCreate.remove("Code5");
                    editorCreate.apply();
                }

            }
        });

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton6.getDrawable() == null) {
                    showAlert(v, 6);
                }
                else{
                    imageButton6.setImageDrawable(null);
                    editorCreate.remove("Code6");
                    editorCreate.apply();
                }

            }
        });

        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton7.getDrawable() == null) {
                    showAlert(v, 7);
                }
                else{
                    imageButton7.setImageDrawable(null);
                    editorCreate.remove("Code7");
                    editorCreate.apply();
                }

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elementsPresent())
                {
                    goToElements();
                }
                else{
                    //TODO dialog what is missing
                }
            }
        });


        for(int i=1; i<8; i++){
            String getCurrentString = "Code" + String.valueOf(i);

            String getURL = userDetailsCreate.getString(getCurrentString,"");
            if(!getURL.isEmpty())
                switch (i) {
                    case 1:
                        try {
                            imageButton1.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            imageButton2.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            imageButton3.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            imageButton4.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            imageButton5.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            imageButton6.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            imageButton7.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                }
        }




        return rootView;
    }


    private void goToFacebookPhotos(int i){
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        startActivityForResult(intent, i);
    }

    public void showAlert(View view, final int whichPhoto){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
        myAlert.setTitle("Select Photos From");
        myAlert.setItems(PHOTO_RESOURCES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),PHOTO_RESOURCES[which],Toast.LENGTH_LONG).show();
                if(which==0){
                    goToFacebookPhotos(whichPhoto);
                }
            }
        });
        myAlert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String temp = data.getExtras().getString("url");
        SharedPreferences userDetails = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();



        String createNewString = "Code" + String.valueOf(requestCode);


        if(userDetails.contains(createNewString))
        {
            editor.remove(createNewString);
            editor.apply();
        }
        editor.putString(createNewString, temp);
        editor.apply();



        // urlHolder[requestCode] = temp;
        for(int i=1; i<8; i++){
            String getCurrentString = "Code" + String.valueOf(i);

            String getURL = userDetails.getString(getCurrentString,"");
             if(!getURL.isEmpty())
                switch (i) {
                    case 1:
                        try {
                            imageButton1.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            imageButton2.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            imageButton3.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            imageButton4.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            imageButton5.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            imageButton6.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            imageButton7.setImageBitmap(new MyAlbumTask().execute(getURL).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }



        Log.d("Activity motion","Success");
    }

    public class MyAlbumTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bp = null;
            URL temp = null;
            URLConnection urlConnection;
            try {
                temp = new URL(params[0]);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                assert temp != null;

                urlConnection = temp.openConnection();
                Log.d("Connection timeout",String.valueOf(urlConnection.getConnectTimeout()));
                urlConnection.setConnectTimeout(1000);
                bp = BitmapFactory.decodeStream(urlConnection.getInputStream());
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

    private void goToElements(){
        Intent intent = new Intent(getActivity(), ElementActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    boolean elementsPresent(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
        String errors = "";

        if(!(sharedPreferences.contains("education"))){
            errors = errors + " Education ";
        }

        if(!(sharedPreferences.contains("occupation"))){
            errors = errors + " Occupation ";
        }

        if(!(sharedPreferences.contains("religion"))){
            errors = errors + " Religion ";
        }

        if(!(sharedPreferences.contains("community"))){
            errors = errors + " Community ";
        }

        if(!(sharedPreferences.contains("height"))){
            errors = errors + " Height ";
        }


        if(imageButton1.getDrawable() == null){
            errors = errors + " Profile Picture ";
        }

        if (!errors.isEmpty()) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Missing Information")
                    .setMessage("Please Fill the" + errors + "Field(s)")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
            return false;
        }

        return true;
    }

}