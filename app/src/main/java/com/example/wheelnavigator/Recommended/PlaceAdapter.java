package com.example.wheelnavigator.Recommended;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wheelnavigator.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {
    Context context;
    ArrayList <PlaceDataModle> list;
    private PlaceSelectListener placeSelectListener;
    public PlaceAdapter(Context context, ArrayList<PlaceDataModle> list, PlaceSelectListener placeSelectListener) {
        this.context = context;
        this.list = list;
        this.placeSelectListener = placeSelectListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.placecardview, parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {
             PlaceDataModle place = list.get(position);
             holder.placename.setText(place.getName());
             holder.DistanceFromCurrent.setText(place.getDistance() + " Km");

        Glide.with(context).load(list.get(position).getImageUrl()).error(R.drawable.ic_baseline_explore_24).fitCenter().into(holder.placelogo);

        holder.PlaceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeSelectListener.onItemClicked(place);
            }
        });

        holder.ApplicationRating.setImageResource(R.drawable.ic_baseline_accessible_24_default);
       if(place.getApplicationRatingScore() >= 30){
           holder.ApplicationRating.setImageResource(R.drawable.ic_baseline_accessible_24_green);
       }
        if(place.getApplicationRatingScore() >= 20&& place.ApplicationRatingScore < 30){
            holder.ApplicationRating.setImageResource(R.drawable.ic_baseline_accessible_24_yellew);
        }
        if(place.getApplicationRatingScore() >= 10 && place.ApplicationRatingScore < 20){
            holder.ApplicationRating.setImageResource(R.drawable.ic_baseline_accessible_24_orange);
        }
        if(place.getApplicationRatingScore() < 10 ){
            holder.ApplicationRating.setImageResource(R.drawable.ic_baseline_accessible_24_red);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
          TextView placename , DistanceFromCurrent;
          ImageView placelogo, ApplicationRating;
          CardView PlaceCard;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placelogo = (ImageView) itemView.findViewById(R.id.PlaceLogo);
            placename = (TextView) itemView.findViewById(R.id.CardPlacename);
            PlaceCard = (CardView) itemView.findViewById(R.id.PlaceCard);
            ApplicationRating = (ImageView) itemView.findViewById(R.id.ApplicationRating);
            DistanceFromCurrent = (TextView) itemView.findViewById(R.id.DistanceFromCurrent);

        }
    }
}
