package com.example.vyas.mymate3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Vyas on 2/25/2016.
 */
public class HeightFragment extends Fragment{


        public static HeightFragment newInstance() {
            HeightFragment fragment = new HeightFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public HeightFragment() {
        }


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_height, container, false);

            ListView listView;
            ArrayAdapter<String> arrayAdapter;
            final String[] heightList = {
                    "> 4'0",
                    "4'0",
                    "4'1",
                    "4'2",
                    "4'3",
                    "4'4",
                    "4'5",
                    "4'6",
                    "4'7",
                    "4'8",
                    "4'9",
                    "4'10",
                    "4'11",
                    "5'0",
                    "5'1",
                    "5'2",
                    "5'4",
                    "5'4",
                    "5'5",
                    "5'6",
                    "5'7",
                    "5'8",
                    "5'9",
                    "5'10",
                    "5'11",
                    "6'0",
                    "6'1",
                    "6'2",
                    "6'4",
                    "6'4",
                    "6'5",
                    "6'6",
                    "6'7",
                    "6'8",
                    "6'9",
                    "6'10",
                    "6'11",
                    "< 6'11"
            };

            listView = (ListView) rootView.findViewById(R.id.height_list_view);
            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, heightList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    String myHeight = heightList[position];
                                                    SharedPreferences.Editor editor = getContext().getSharedPreferences("userdetails", Context.MODE_PRIVATE).edit();
                                                    editor.remove("height");
                                                    editor.apply();
                                                    editor.putString("height", myHeight);
                                                    editor.apply();
                                                    Toast.makeText(getContext(), myHeight + " is selected", Toast.LENGTH_LONG).show();
                                                }
                                            }
            );

            return rootView;
        }
    }
