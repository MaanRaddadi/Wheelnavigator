package com.example.wheelnavigator.Recommended;

public class PlaceDataModle {
    String Name , imageUrl , Crn;
    boolean Approved ;
    int ApplicationRatingScore;

    public int getApplicationRatingScore() {
        return ApplicationRatingScore;
    }

    public void setApplicationRatingScore(int applicationRatingScore) {
        ApplicationRatingScore = applicationRatingScore;
    }

    PlaceDataModle(){

    }
    public String getCrn() {
        return Crn;
    }

    public void setCrn(String crn) {
        Crn = crn;
    }



    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean approved) {
        Approved = approved;
    }

    public PlaceDataModle(String name, String imageUrl, boolean Approved , String Crn, int ApplicationRatingScore) {
        Name = name;
        this.imageUrl = imageUrl;
        this.Approved=Approved;
        this.Crn = Crn;
        this.ApplicationRatingScore=ApplicationRatingScore;


    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
