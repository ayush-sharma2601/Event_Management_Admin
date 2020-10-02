package com.ayush26.event_management_admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

    public class EventVH extends RecyclerView.ViewHolder {
        TextView eventName,eventDate,eventGenre;
        CardView background;
        public EventVH(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventGenre = itemView.findViewById(R.id.event_genre);
            background = itemView.findViewById(R.id.event_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open event page
                }
            });
        }

        public void PopulateEvents(EventRVItem eventRVItem) {
            if (code==1)
                background.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.eventRed));
            eventName.setText(eventRVItem.getEventName());
            eventDate.setText(eventRVItem.getEventDate());
            eventGenre.setText(eventRVItem.getEventGenre());
        }
    }
}
