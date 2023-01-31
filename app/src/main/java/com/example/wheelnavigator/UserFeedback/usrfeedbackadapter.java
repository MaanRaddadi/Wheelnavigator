package com.example.wheelnavigator.UserFeedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelnavigator.R;

import java.util.ArrayList;

public class usrfeedbackadapter extends RecyclerView.Adapter<usrfeedbackadapter.MyViewHolder> {
    Context context;
    ArrayList <usrFeedbackDataModel> list;
    public usrfeedbackadapter(Context context , ArrayList <usrFeedbackDataModel> list ){
        this.context =context;
        this.list =list;


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
             TextView Username , Feedbacktxt , RatingValue;
             RatingBar ratingBar;
             ImageView disabilityicon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Username =  (TextView) itemView.findViewById(R.id.FeedbackCardUsername);
            Feedbacktxt = (TextView)  itemView.findViewById(R.id.feedbacktext);
            RatingValue = (TextView) itemView.findViewById(R.id.RatingValue2);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar2);
            disabilityicon = (ImageView) itemView.findViewById(R.id.wheelchairicon);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usrfeedbackmodel, parent , false);
        return new MyViewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           usrFeedbackDataModel feedback = list.get(position);

           holder.Username.setText(feedback.getUsername());
           holder.Feedbacktxt.setText(feedback.getFeedbacktxt());
           holder.RatingValue.setText(String.valueOf(feedback.getRatingValue()));
           holder.ratingBar.setRating(feedback.getRatingValue());

           if(feedback.isDisability() == true ){
               holder.disabilityicon.setImageResource(R.drawable.wheelchairperson);
           }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
