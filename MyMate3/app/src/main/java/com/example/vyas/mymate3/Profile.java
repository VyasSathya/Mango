package com.example.vyas.mymate3;

import android.media.Image;

/**
 * Created by Vyas on 2/15/2016.
 */
public class Profile {

    private String userId;
    private String token;

    public String getUserId(){
        return userId;
    }

    public String getToken(){
        return token;
    }

    public void setUserId(String s){
        userId = s;
    }

    public void setToken(String s){
        token = s;
    }

    public Profile() {
        userId = null;
        token = null;
    }
}
