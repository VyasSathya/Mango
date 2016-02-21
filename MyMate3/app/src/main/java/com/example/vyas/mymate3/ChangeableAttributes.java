package com.example.vyas.mymate3;

/**
 * Created by Vyas on 2/17/2016.
 */
public class ChangeableAttributes {
    private int heightFeet;
    private int heightInches;
    private String education;
    private String occupation;
    private String religion;
    private String community;

    public void ChangeableAttributes(){
        heightFeet = 0;
        heightInches = 0;
        education = "none";
        occupation = "none";
        religion = "none";
        community = "none";
    };

    public void setHeight(int f, int i) {
        if( f > 1 && f < 10 && i < 12 && i >= 0){
            heightFeet=  f;
            heightInches = i;
        }
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public String getEducation(){
        return education;
    }

    public String getReligion() {
        return religion;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getCommunity(){
        return community;
    }
}
