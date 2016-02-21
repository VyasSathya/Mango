package com.example.vyas.mymate3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionnaireActivity extends AppCompatActivity {

    ChangeableAttributes changeableAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequestBatch batch = new GraphRequestBatch(
                GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                String name = object.optString("name");


                                String link = object.optString("link");
                                //String birthday = object.optString("birthday");

                                //String location = object.optJSONObject("location").optString("name");
                                Log.d("Name", name);

                                Log.d("Link", link);




                                //Log.d("Birthday",birthday);
                                //Log.d("Location",location);

                            }
                        }),
                GraphRequest.newMyFriendsRequest(
                        accessToken,
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(
                                    JSONArray jsonArray,
                                    GraphResponse response) {
                                JSONObject object = response.getJSONObject();
                                //JSONObject summary = object.optJSONObject("summary");
                                String link = object.optString("link");
                                JSONArray listFriends = object.optJSONArray("data");

                                //String birthday = object.optString("birthday");

                                //String location = object.optJSONObject("location").optString("name");
                                //Log.d("Summary", summary.optString("total_count"));
                                Log.d("list friends", listFriends.length()+"");

                                //Log.d("Birthday",birthday);
                                //Log.d("Location",location);

                            }
                        })

        );
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                // Application code for when the batch finishes
            }
        });
        batch.executeAsync();
        hardCodeInfo();

        Button photoButton = (Button) findViewById(R.id.photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finishActivity();
                Log.d("MyApp", "I am exiting Question Activity");
                Intent intent =  new Intent();
                Bundle bp = new Bundle();
                bp.putString("education", changeableAttributes.getEducation());
                bp.putString("occupation",changeableAttributes.getOccupation());
                bp.putString("religion",changeableAttributes.getReligion());
                bp.putString("community",changeableAttributes.getCommunity());
                bp.putInt("heightfeet", changeableAttributes.getHeightFeet());
                bp.putInt("heightinches",changeableAttributes.getHeightInches());
                intent.putExtras(bp);
                setResult(RESULT_OK, intent);
                finish();
            }
        });





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
