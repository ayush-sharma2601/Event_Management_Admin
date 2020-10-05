package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ayush26.event_management_admin.Adapters.EventsRVAdapter;
import com.ayush26.event_management_admin.Models.EventRVItem;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityAdminHomeBinding;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {

    ActivityAdminHomeBinding adminHomeBinding;
    ArrayList<EventRVItem> upcomingEvents,pastEvents;
    EventsRVAdapter pastEventAdapter,upcomingEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminHomeBinding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(adminHomeBinding.getRoot());

        setLayoutManager();

        initArrayList();

        addData();

        initAdapter();

        attachAdapter();

        updateData();

        adminHomeBinding.newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this,CreateNewEvent.class));
            }
        });

    }

    private void updateData() {
        upcomingEventsAdapter.notifyDataSetChanged();
        pastEventAdapter.notifyDataSetChanged();
    }

    private void attachAdapter() {
        adminHomeBinding.upcomingEventsRv.setAdapter(upcomingEventsAdapter);
        adminHomeBinding.pastEventsRv.setAdapter(pastEventAdapter);
    }

    private void addData() {
        upcomingEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing"));
        pastEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing"));
        pastEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing"));
        pastEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing"));
        pastEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing"));
        pastEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing"));

    }

    private void initAdapter() {
        upcomingEventsAdapter = new EventsRVAdapter(upcomingEvents,this,0);
        pastEventAdapter = new EventsRVAdapter(pastEvents,this,1);
    }

    private void initArrayList() {
        upcomingEvents = new ArrayList<>();
        pastEvents = new ArrayList<>();
    }

    private void setLayoutManager() {
        adminHomeBinding.upcomingEventsRv
                .setLayoutManager(new LinearLayoutManager(AdminHome.this,LinearLayoutManager.VERTICAL,false));
        adminHomeBinding.pastEventsRv
                .setLayoutManager(new LinearLayoutManager(AdminHome.this,LinearLayoutManager.VERTICAL,false));
    }
}