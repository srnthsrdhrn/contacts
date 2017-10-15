package com.example.android.displaycontacts;

/**
 * Created by Srinath on 15-10-2017.
 */

public class CallLog {
    String phoneNumber;
    String time;
    String type;
    String duration;

    public String toString(){
        return "Phone Number: "+ this.phoneNumber+
                " \n Time: "+ this.time+" \n Duration: "+ this.duration+" \n Type: "+ this.type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
