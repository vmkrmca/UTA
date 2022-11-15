package com.deepak.utacommunityservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    ArrayList<Event> mEventArrayList = null;
    Context mContext;
    RequestEventClickListener mRequestEventClickListener;


    public EventAdapter(Context context,ArrayList<Event> eventArrayList,RequestEventClickListener requestEventClickListener) {
        this.mContext = context;
        this.mEventArrayList = eventArrayList;
        this.mRequestEventClickListener = requestEventClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.event_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvEventDate.setText(""+mEventArrayList.get(position).eventDate);
        holder.tvEventTime.setText(""+mEventArrayList.get(position).eventTime);
        holder.tvEventTitle.setText(""+mEventArrayList.get(position).eventTitle);
        holder.tvEventDescription.setText(""+mEventArrayList.get(position).eventDescription);
        Bitmap mBitmap = getImage(mEventArrayList.get(position).eventImage);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), mBitmap);
        holder.ivImage.setBackground(bitmapDrawable);

        holder.tvRequestEvent.setOnClickListener(v -> mRequestEventClickListener.onRequestClick(mEventArrayList.get(position)));
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int getItemCount() {
        return mEventArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvRequestEvent,tvEventTitle,tvEventDescription,tvEventDate,tvEventTime;
        ImageView ivImage;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
        tvEventDescription = itemView.findViewById(R.id.tvEventDescription);
        tvEventDate = itemView.findViewById(R.id.tvEventDate);
        tvEventTime = itemView.findViewById(R.id.tvEventTime);
        ivImage = itemView.findViewById(R.id.ivImage);
        tvRequestEvent = itemView.findViewById(R.id.tvRequestEvent);
    }
}

}


