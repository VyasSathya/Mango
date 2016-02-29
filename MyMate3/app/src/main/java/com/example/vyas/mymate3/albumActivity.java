package com.example.vyas.mymate3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.facebook.AccessToken;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class albumActivity extends Activity {
    GridView gv;
    List<String> urlArrayList = null;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        urlArrayList = getIntent().getExtras().getStringArrayList("urls");

        gv = (GridView) findViewById(R.id.albumGridView);
        gv.setAdapter(new GridAdapter());
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

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return urlArrayList.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.single_grid,parent,false);
            ImageButton imageButton = (ImageButton) convertView.findViewById((R.id.imageButtonPhoto));
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent mIntent = new Intent(getApplicationContext(),albumActivity.class);
                    Intent mIntent = new Intent();
//                    Bundle extras = new Bundle();
//                    extras.putString("url", urlArrayList.get(position));
                    mIntent.putExtra("url",urlArrayList.get(position));


                    setResult(Activity.RESULT_OK, mIntent);
                    finish();
                    System.exit(0);
                }
            });

            try {
                imageButton.setImageBitmap(new MyAlbumTask().execute(urlArrayList.get(position)).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            gv.setNumColumns(3);

            return convertView;

        }
    }


    public class MyAlbumTask extends AsyncTask<String, Void, Bitmap> {

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

}