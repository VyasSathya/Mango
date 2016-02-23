package com.example.vyas.mymate3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class QuestionnaireActivity extends AppCompatActivity {

    ChangeableAttributes changeableAttributes;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        listView = (ListView)findViewById(R.id.options_list_view);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,educationList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id)
                {
                    Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position)+" is selected", Toast.LENGTH_LONG).show();

                }

        });




        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest graphRequest = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        String first_name = object.optString("first_name");
                        String birthday = object.optString("birthday");
                        String gender = object.optString("gender");
                        Log.d("First Name", first_name);
                        Log.d("Birthday", birthday);
                        Log.d("Gender", gender);

                        String[] tokens = birthday.split("/");

                        Log.d("Token 1", tokens[0]);
                        Log.d("Token 2", tokens[1]);
                        Log.d("Token 3", tokens[2]);

                        int token1 = Integer.parseInt(tokens[0]);
                        int token2 = Integer.parseInt(tokens[1]);
                        int token3 = Integer.parseInt(tokens[2]);

                        int age = getAge(token3, token2, token1);
                        String stringAge = String.valueOf(age);



                        Log.d("Calculated Age", stringAge);

                        SharedPreferences.Editor editor = getSharedPreferences("userdetails", MODE_PRIVATE).edit();
                        editor.putString("first_name", first_name);
                        editor.putString("gender", gender);
                        editor.putString("age", stringAge);
                        editor.apply();

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,birthday,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
        hardCodeInfo();


        Button photoButton = (Button) findViewById(R.id.photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MyApp", "I am exiting Question Activity");
                Intent intent = new Intent();
                Bundle bp = new Bundle();
                bp.putString("education", changeableAttributes.getEducation());
                bp.putString("occupation", changeableAttributes.getOccupation());
                bp.putString("religion", changeableAttributes.getReligion());
                bp.putString("community", changeableAttributes.getCommunity());
                bp.putInt("heightfeet", changeableAttributes.getHeightFeet());
                bp.putInt("heightinches", changeableAttributes.getHeightInches());
                intent.putExtras(bp);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }



        public int getAge (int _year, int _month, int _day) {

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
            if(a < 0)
                throw new IllegalArgumentException("Age < 0");
            return a;
        }






    void hardCodeInfo(){
        changeableAttributes = new ChangeableAttributes();
        changeableAttributes.setHeight(5, 8);
        changeableAttributes.setEducation("bachelors");
        changeableAttributes.setOccupation("Animator");
        changeableAttributes.setReligion("Hindu");
        changeableAttributes.setCommunity("Tamil");

    }

}
