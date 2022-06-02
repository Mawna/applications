package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tracker.databinding.ActivityMapBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class map extends FragmentActivity implements OnMapReadyCallback {

    private int ACCESS_LOCATION_REQUEST_CODE = 1001 ;
    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private long hours, min, sec;
    public long gethours(){return hours;}
    public long getmin(){return min;}
    public long getsec(){return sec;}
    LocationRequest locationRequest;
    long pauseoffset;

    Button pause;
    boolean time = true;

    Chronometer chronometer;
    Marker userlocationmarker;
    

    List<Location> saveloc;

    FusedLocationProviderClient fusedLocationProviderClient;

    LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // take location each 5 sec
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        chronometer = findViewById(R.id.timer); // create a klink with xml chronometer

        chronometer.setText(String.format(Locale.getDefault(), "%02d : %02d : %02d", 0, 0, 0)); // set the text and the frmat of the chronometer
        chronometer.setFormat(String.format(Locale.getDefault(), "%02d : %02d : %02d", 0, 0, 0));

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 1000 || (SystemClock.elapsedRealtime() - chronometer.getBase()) == 0) {
                    long times= SystemClock.elapsedRealtime() - chronometer.getBase();

                    //define units
                     hours = TimeUnit.MILLISECONDS.toHours(times);
                     min = TimeUnit.MILLISECONDS.toMinutes(times) -
                            TimeUnit.HOURS.toMinutes(hours);
                     sec = TimeUnit.MILLISECONDS.toSeconds(times) -
                            TimeUnit.MINUTES.toSeconds(min) -
                            TimeUnit.HOURS.toSeconds(hours);
                    chronometer.setText(String.format(Locale.getDefault(), "%02d : %02d : %02d", hours, min, sec));
                }
            }
        });

        chronometer.start();

        listpoint lp = (listpoint)getApplicationContext(); // take the application context to create the list of each location point
        saveloc = lp.getLoc();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){ // chekc permission

            enablelocation();
            zoomtouserlocation();
        }
        else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                //dialog if permission is requiered
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_LOCATION_REQUEST_CODE);
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_LOCATION_REQUEST_CODE);
            }
        }


    }

    LocationCallback locationCallBack = new LocationCallback(){ // get last location
        @Override
        public void onLocationResult(LocationResult locationResult) { //Called when device location information is available.
            if(locationResult == null){
                return;
            }
            //just in case

            listpoint t = (listpoint) getApplicationContext();
            saveloc = t.getLoc();
            if(saveloc.size()==0)
                saveloc.add(locationResult.getLastLocation());
            else if(locationResult.getLastLocation() != saveloc.get(saveloc.size()-1)) // check if user move or not to add or not in the list
            {
                saveloc.add(locationResult.getLastLocation());

                // polyline (drawing a line behind the user on the map take the 2 last register points in the list and draw a line between them)
                LatLng l1 =new LatLng(saveloc.get(saveloc.size()-1).getLatitude(),saveloc.get(saveloc.size()-1).getLongitude());
                LatLng l2 = new LatLng(saveloc.get(saveloc.size()-2).getLatitude(),saveloc.get(saveloc.size()-2).getLongitude());
                PolylineOptions rectoption = new PolylineOptions().add(l1).add(l2);
                Polyline polyline = mMap.addPolyline(rectoption);
                polyline.setColor(Color.rgb(167,134,254));
            }

                if(mMap != null){// if map exist i set the location with the new location
                    setusermark(locationResult.getLastLocation());
                }
            //}
        }
    };

    public void onClick (View view){ // create this to have access to the button whearas there is a map fragment ( a findviewbyid in the on create doesn't work so i made a new methods

        //pause and resume timer and location
        if(view.getId() == R.id.pause )
        {
            pause = findViewById(view.getId());
            if(time){
                chronometer.stop();
                pauseoffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                onStop();
                time = false;
                pause.setText("Resume");
            }
            else {

                onStart();
                chronometer.start();
                pause.setText("Pause");
                time=true;
            }

        }
        //stop location
        if(view.getId() == R.id.stop)
        {
            onStop();
            if(saveloc.size() > 3){
                Intent g = new Intent(getApplicationContext(),graph.class);
                g.putExtra("hours",hours);
                g.putExtra("min",min);
                g.putExtra("sec",sec);
                startActivity(g);
                finish();
            }
            else// if there is ess than 2 points in the list i concider that the user don't run /move then it is not a real "run" i don't concider it
            {
                Toast.makeText(getApplicationContext(), "You don't have enought data to create a graph", Toast.LENGTH_SHORT).show();
                Intent g = new Intent(getApplicationContext(),find.class);
                startActivity(g);
                finish();
            }

        }

    }



    private void setusermark(Location location){ // setup the location and the mark

        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (userlocationmarker == null) // create a marker if it is not created
        {
            //Create a new mark
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bonicon)); // supposed to create a custom marker but it is not dysplay i don't know why


            userlocationmarker = mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17)); // follow the marker
        }
        else {
            //use previous marker
            userlocationmarker.setPosition(latLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    }

    private void startlocationupdates(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            // use fusedlocation to start location update thanks to the locationcallBack

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper());
    }
    private void stoplocationupdates(){
        //stop location update
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    //call location update
    @Override
    protected void onStart() {
        super.onStart();
        startlocationupdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stoplocationupdates();
    }

    private void enablelocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);

    }

    private void zoomtouserlocation(){ // mange the zoom camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation(); // get last location us it for the zoom
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(saveloc.size() >0)
                    {
                        //create a latlong which follow the user and thaks to this we can create the zoom
                        LatLng lat = new LatLng(saveloc.get(0).getLatitude(),saveloc.get(0).getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat, 17)); // move the camera and zoom on it (int control zoom)
                        MarkerOptions mark = new MarkerOptions();
                        mark.position(lat);
                        mark.title(("lat : " + saveloc.get(0).getLatitude() + " long : " + saveloc.get(0).getLongitude()));
                        mMap.addMarker(mark);

                    }


                }
            });
        }



    }
}