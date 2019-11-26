package com.example.alarmproject;

public class AlarmData {
    private int hour;
    private int minute;
    private String days;
    private String name;
    private String location;
    private boolean message;
    private boolean hasPassword;
    private String password;
    private boolean onoff;

    public AlarmData(int hour, int minute, String days, String name, String location, boolean message, boolean hasPassword, String password, boolean onoff) {
        this.hour = hour;
        this.minute = minute;
        this.days = days;
        this.name = name;
        this.location = location;
        this.message = message;
        this.hasPassword = hasPassword;
        this.password = password;
        this.onoff = onoff;
    }
    public AlarmData(){

    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnoff() {
        return onoff;
    }

    public void setOnoff(boolean onoff) {
        this.onoff = onoff;
    }
}

