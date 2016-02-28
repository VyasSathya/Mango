package com.example.vyas.mymate3;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vyas on 2/27/2016.
 */
public class Adapter extends ArrayAdapter<String> {

    Context c;
    List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    List<String> albumTitle = new ArrayList<String>();
    LayoutInflater inflater = null;

    public Adapter(Context context, List<String> albTitle, List<Bitmap> bitmap) {
        super(context, R.layout.model_album, albTitle);

        this.c = context;
        this.albumTitle = albTitle;
        this.bitmaps = bitmap;
    }

    public class ViewHolder {
        TextView nameAlbum;
        ImageView imageView;

    }

    @Override
    public int getCount() {
        return albumTitle.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.model_album, null);
        }

        final ViewHolder holder = new ViewHolder();

        if (position <= getCount()) {
            holder.nameAlbum = (TextView) convertView.findViewById(R.id.albumName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.albumImage);

            Log.d("Get Count", String.valueOf(getCount()));
            Log.d("Position", String.valueOf(position));

            holder.nameAlbum.setText(albumTitle.get(position));
            holder.imageView.setImageBitmap(bitmaps.get(position));

            convertView.setTag(holder);
        } else {
        }
        return convertView;
        //return super.getView(position,convertView,parent);

    }

}
