package com.example.tracker;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class listpoint extends Application {

    // create a list to  save each location point


    public List<Location> getLoc() {
        return loc;
    }

    public void setLoc(List<Location> loc) {
        this.loc = loc;
    }

    private List<Location> loc;


    public void onCreate(){
        super.onCreate();
        loc = new ArrayList<>();
    }
}
