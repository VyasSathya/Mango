package com.example.vyas.mymate3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Vyas on 2/25/2016.
 */
public class EducationFragment extends Fragment {


    public static EducationFragment newInstance() {
        EducationFragment fragment = new EducationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EducationFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getContext());
        final View rootView = inflater.inflate(R.layout.fragment_education, container, false);

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//
//        GraphRequest graphRequest = GraphRequest.newMeRequest(
//                accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        String first_name = object.optString("first_name");
//                        String birthday = object.optString("birthday");
//                        String gender = object.optString("gender");
//                        Log.d("First Name", first_name);
//                        Log.d("Birthday", birthday);
//                        Log.d("Gender", gender);
//
//                        String[] tokens = birthday.split("/");
//
//                        Log.d("Token 1", tokens[0]);
//                        Log.d("Token 2", tokens[1]);
//                        Log.d("Token 3", tokens[2]);
//
//                        int token1 = Integer.parseInt(tokens[0]);
//                        int token2 = Integer.parseInt(tokens[1]);
//                        int token3 = Integer.parseInt(tokens[2]);
//
//                        int age = getAge(token3, token2, token1);
//                        String stringAge = String.valueOf(age);
//
//
//                        Log.d("Calculated Age", stringAge);
//
//                        SharedPreferences.Editor editor = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE).edit();
//                        editor.putString("first_name", first_name);
//                        editor.putString("gender", gender);
//                        editor.putString("age", stringAge);
//                        editor.apply();
//
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "first_name,birthday,gender");
//        graphRequest.setParameters(parameters);
//        graphRequest.executeAsync();

        ListView listView;
        ArrayAdapter<String> arrayAdapter;
        final String[] educationList = {
                "High School",
                "Bachelors",
                "Masters",
                "Phd",
                "Other",
                "None"
        };

        listView = (ListView) rootView.findViewById(R.id.education_list_view);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, educationList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String myEducation = educationList[position];
                                                SharedPreferences.Editor editor = getContext().getSharedPreferences("userdetails", Context.MODE_PRIVATE).edit();
                                                editor.putString("education", myEducation);
                                                editor.apply();
                                                Toast.makeText(getContext(), myEducation + " is selected", Toast.LENGTH_LONG).show();
                                            }
                                        }
        );

        return rootView;
    }

    public int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }
}
