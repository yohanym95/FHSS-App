package com.example.myapp.Models;

public class Events {
    private int id;
    private String EventName, EventDate,EventDetails;

    public Events(int id, String eventName, String eventDate, String eventDetails) {
        this.id = id;
        EventName = eventName;
        EventDate = eventDate;
        EventDetails = eventDetails;
    }

    public String getEventDetails() {
        return EventDetails;
    }

    public void setEventDetails(String eventDetails) {
        EventDetails = eventDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }
}
