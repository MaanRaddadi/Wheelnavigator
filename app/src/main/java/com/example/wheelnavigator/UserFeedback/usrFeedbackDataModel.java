package com.example.wheelnavigator.UserFeedback;

public class usrFeedbackDataModel {
    String Username , Feedbacktxt;
 boolean  Disability;
 float RatingValue;

    public String getFeedbacktxt() {
        return Feedbacktxt;
    }

    public void setFeedbacktxt(String feedbacktxt) {
        Feedbacktxt = feedbacktxt;
    }

    public float getRatingValue() {
        return RatingValue;
    }

    public void setRatingValue(float ratingValue) {
        RatingValue = ratingValue;
    }

    usrFeedbackDataModel(){


 }

    public usrFeedbackDataModel(String username, String feedbacktxt, boolean disability, float ratingValue) {
        this.Username = username;
        this.  Feedbacktxt = feedbacktxt;
        this. Disability = disability;
        this. RatingValue = ratingValue;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public boolean isDisability() {
        return Disability;
    }

    public void setDisability(boolean disability) {
        Disability = disability;
    }
}
