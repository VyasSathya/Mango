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
public class CommunityFragment extends Fragment {


    public static CommunityFragment newInstance() {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CommunityFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        ListView listView;
        ArrayAdapter<String> arrayAdapter;
        final String[] communityList = {
                "Hindi",
                "Tamil",
                "Telugu",
                "Marathi",
                "Kannada",
                "Punjabi"
        };

        listView = (ListView) rootView.findViewById(R.id.community_list_view);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, communityList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String myCommunity = communityList[position];
                                                SharedPreferences.Editor editor = getContext().getSharedPreferences("userdetails", Context.MODE_PRIVATE).edit();
                                                editor.remove("community");
                                                editor.apply();
                                                editor.putString("community", myCommunity);
                                                editor.apply();
                                                Toast.makeText(getContext(), myCommunity + " is selected", Toast.LENGTH_LONG).show();
                                            }
                                        }
        );

        return rootView;
    }
}
