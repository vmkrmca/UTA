package com.deepak.utacommunityservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsApprovalAdapter extends RecyclerView.Adapter<EventsApprovalAdapter.EventRequestViewHolder> {

    Context mContext;
    ArrayList<EventRequest> mEventRequestArrayList;
    EventRequestListener mEventRequestListener;

    public EventsApprovalAdapter(Context context, ArrayList<EventRequest> eventRequestArrayList,EventRequestListener eventRequestListener) {
        this.mContext = context;
        this.mEventRequestArrayList = eventRequestArrayList;
        this.mEventRequestListener = eventRequestListener;
    }

    @NonNull
    @Override
    public EventRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView  = LayoutInflater.from(mContext).inflate(R.layout.event_request_row,parent,false);
        return new EventRequestViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRequestViewHolder holder, int position) {

        holder.tvMobileNumber.setText(""+mEventRequestArrayList.get(position).mobileNumber);
        holder.tvEmailAddress.setText(""+mEventRequestArrayList.get(position).emailAddress);
        holder.tvEventTitle.setText(""+mEventRequestArrayList.get(position).eventTitle);
        holder.tvEventDate.setText(""+mEventRequestArrayList.get(position).eventDate);
        holder.tvEventTime.setText(""+mEventRequestArrayList.get(position).eventTime);

        holder.tvApproval.setOnClickListener(v-> mEventRequestListener.onEventRequest(mEventRequestArrayList.get(position),1001));
        holder.tvDApproval.setOnClickListener(v-> mEventRequestListener.onEventRequest(mEventRequestArrayList.get(position),1002));



    }

    @Override
    public int getItemCount() {
        return mEventRequestArrayList.size();
    }


    public class EventRequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvMobileNumber,tvEmailAddress,tvEventTitle,tvEventDate,tvEventTime,tvApproval,tvDApproval;


        public EventRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMobileNumber = itemView.findViewById(R.id.tvMobileNumber);
            tvEmailAddress = itemView.findViewById(R.id.tvEmailAddress);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvEventTime = itemView.findViewById(R.id.tvEventTime);
            tvApproval = itemView.findViewById(R.id.tvApproval);
            tvDApproval = itemView.findViewById(R.id.tvDApproval);

        }
    }

}
