package com.example.wheelnavigator.Admin;

public class ImgModel {
    private String imageUrl;
    public ImgModel (){

    }

    public ImgModel(String imageUrl){
         this.imageUrl =imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
