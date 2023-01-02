package com.example.wheelnavigator;

public class Upload {

       private String mImgUrl;


       public Upload(){

       }

    public Upload(String ImgUrl){


        mImgUrl = ImgUrl;

    }



    public String getImgUrl(){
         return mImgUrl;
    }
    public void setImgUrl(String ImgUrl){
           mImgUrl=ImgUrl;
    }

}
