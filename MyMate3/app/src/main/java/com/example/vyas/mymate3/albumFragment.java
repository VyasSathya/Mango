package com.example.vyas.mymate3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class albumFragment extends Fragment {


    GridView gv;
    List<String> urlArrayList = null;
    int position;
    List<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        Bundle b = getArguments();

        urlArrayList =b.getStringArrayList("urls");



        gv = (GridView) view.findViewById(R.id.albumGridView);
        gv.setAdapter(new GridAdapter());

        return view;
    }

    class GridAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.single_grid,parent,false);
            for (int i = 0; i<urlArrayList.size(); i++) {
                try {
                    bitmapArrayList.add(i, new MyAlbumTask().execute(urlArrayList.get(i)).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            ImageView iv = (ImageView) convertView.findViewById((R.id.imageButtonPhoto));

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
