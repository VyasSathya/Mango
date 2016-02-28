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
public class ReligionFragment extends Fragment {


    public static ReligionFragment newInstance() {
        ReligionFragment fragment = new ReligionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ReligionFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_religion, container, false);

        ListView listView;
        ArrayAdapter<String> arrayAdapter;
        final String[] religionList = {
                "Hindu",
                "Muslim",
                "Christian",
                "Jewish",
                "Other",
                "None"
        };

        listView = (ListView) rootView.findViewById(R.id.religion_list_view);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, religionList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String myReligion = religionList[position];
                                                SharedPreferences.Editor editor = getContext().getSharedPreferences("userdetails", Context.MODE_PRIVATE).edit();
                                                editor.putString("religion", myReligion);
                                                editor.apply();
                                                Toast.makeText(getContext(), myReligion + " is selected", Toast.LENGTH_LONG).show();
                                            }
                                        }
        );

        return rootView;
    }

}