package com.example.wheelnavigator.Recommended;

public class PlaceDataModle {
    String Name , imageUrl , Crn;
    boolean Approved ;
    int ApplicationRatingScore;
    String PlaceType;
    double PlaceLat;
    double PlaceLng;
    double distance;


    public double getPlaceLat() {
        return PlaceLat;
    }

    public void setPlaceLat(double placeLat) {
        PlaceLat = placeLat;
    }

    public double getPlaceLng() {
        return PlaceLng;
    }

    public void setPlaceLng(double placeLng) {
        PlaceLng = placeLng;
    }



    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPlaceType() {
        return PlaceType;
    }

    public void setPlaceType(String placeType) {
        this.PlaceType = placeType;
    }

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

    public PlaceDataModle(String name, String imageUrl, boolean Approved , String Crn, int ApplicationRatingScore ,String PlaceType , double distance , double PlaceLat , double PlaceLng) {
        Name = name;
        this.imageUrl = imageUrl;
        this.Approved=Approved;
        this.Crn = Crn;
        this.ApplicationRatingScore=ApplicationRatingScore;
        this.PlaceType = PlaceType;
        this.PlaceLat = PlaceLat;
        this.PlaceLng = PlaceLng;
        this.distance = distance;

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
