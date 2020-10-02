package com.ayush26.event_management_admin.Models;

public class EventRVItem {
    private String eventName;
    private String eventDate;
    private String eventGenre;

    public EventRVItem(String eventName, String eventDate, String eventGenre) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventGenre = eventGenre;
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
