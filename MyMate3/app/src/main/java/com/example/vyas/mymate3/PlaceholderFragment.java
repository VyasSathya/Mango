package com.example.vyas.mymate3;

/**
 * Created by Vyas on 2/25/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static int currentSection;


    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        currentSection = sectionNumber;
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_tabbed_question, container, true);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


        String[] list = {};
        ListView listView;
        ArrayAdapter<String> arrayAdapter;
        String[] educationList = {
                "High School",
                "Bachelors",
                "Masters",
                "Phd",
                "Other",
                "None"
        };
        String[] occupationList = {
                "Engineer",
                "Doctor",
                "Lawyer",
                "Dentist",
                "Therapist",
                "Other",
                "None"
        };
        String[] religionList = {
                "Hindu",
                "Muslim",
                "Christian",
                "Jewish",
                "Other",
                "None"
        };
        String[] communityList = {
                "Hindi",
                "Tamil",
                "Telugu",
                "Marathi",
                "Kannada",
                "Punjabi"
        };
        String[] heightList = {
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


        switch (currentSection) {
            case 0:
                list = educationList;
                break;
            case 1:
                list = occupationList;
                break;
            case 2:
                list = religionList;
                break;
            case 3:
                list = communityList;
                break;
            case 4:
                list = heightList;
                break;
        }


        listView = (ListView) rootView.findViewById(R.id.options_list_view);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Toast.makeText(getContext(), parent.getItemIdAtPosition(position) + " is selected", Toast.LENGTH_LONG).show();
                                            }
                                        }
        );
        //setRetainInstance(true);
        return rootView;
    }

}