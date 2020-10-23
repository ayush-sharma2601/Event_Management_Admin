package com.ayush26.event_management_admin.Models;

public class EventRVItem {
    private String id;
    private String eventName;
    private String eventDate;
    private String eventGenre;
    private String eventState;

    public EventRVItem(String id, String eventName, String eventDate, String eventGenre, String eventState) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventGenre = eventGenre;
        this.eventState = eventState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventGenre() {
        return eventGenre;
    }

    public void setEventGenre(String eventGenre) {
        this.eventGenre = eventGenre;
    }
}
