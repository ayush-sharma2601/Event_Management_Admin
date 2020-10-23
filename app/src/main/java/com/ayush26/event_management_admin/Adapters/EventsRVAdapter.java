package com.ayush26.event_management_admin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush26.event_management_admin.Activities.EventOnClick;
import com.ayush26.event_management_admin.Models.EventRVItem;
import com.ayush26.event_management_admin.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.EventVH> {

    ArrayList<EventRVItem> events;
    Context context;
    int code;

    public EventsRVAdapter(ArrayList<EventRVItem> events, Context context,int code) {
        this.events = events;
        this.context = context;
        this.code = code;
    }

    @NonNull
    @Override
    public EventVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_rv_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventVH holder, int position) {
        holder.PopulateEvents(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void AddEvent(EventRVItem eventRVItem){
        events.add(eventRVItem);
    }

    public String getDate(String DTF){
        if(DTF!=null){
            String month = getMonth(DTF.substring(5,7));
            String date = DTF.substring(8,10)+ " " + month+ ", "+ DTF.substring(0,4);
            return date;
        }else
            return "false";
    }

    private String getMonth(String substring) {
        return (substring.equals("01")?"January":(substring.equals("02")?"February":(substring.equals("03")?"March":(substring.equals("04")?"April":
                (substring.equals("05")?"May":(substring.equals("06")?"June":(substring.equals("07")?"July":(substring.equals("08")?"August":
                        (substring.equals("09")?"September":(substring.equals("10")?"October":(substring.equals("11")?"November":(substring.equals("12")?"December":"Default"))))))))))));
    }

    public class EventVH extends RecyclerView.ViewHolder {
        TextView eventName,eventDate,eventGenre;
        CardView background;
        public EventVH(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventGenre = itemView.findViewById(R.id.event_genre);
            background = itemView.findViewById(R.id.event_card);


        }

        public void PopulateEvents(final EventRVItem eventRVItem) {
            if (code==1)
                background.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.eventRed));

            String date = getDate(eventRVItem.getEventDate());
            eventName.setText(eventRVItem.getEventName());
            eventDate.setText(date);
            eventGenre.setText(eventRVItem.getEventGenre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open event page
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), EventOnClick.class).putExtra("state",eventRVItem.getEventState())
                            .putExtra("competition_id",eventRVItem.getId()));
                    Log.i("TAG", "onClick: >>>>>>>>>>>>>>>>>>>>>  "+eventRVItem.getId());
                }
            });

        }
    }
}
