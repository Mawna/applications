package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class find extends AppCompatActivity implements SensorEventListener {



    int LOCATION_REQUEST_CODE = 10001;
    private static final String TAG = "find";


    private TextView nbp;
    private SensorManager senman;
    private Sensor nbs;

    //////pedometter
    int stepc = 0;
    ProgressBar prog;
    Button map;
    ImageButton info, close;
    private AlertDialog.Builder dia;
    private AlertDialog dialo;

    List<Location> saveloc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        //  wayp = findViewById(R.id.add);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        nbp = findViewById(R.id.nbp);
        info = findViewById(R.id.info);
        map = findViewById(R.id.map2);
        map.setClickable(false);

        asklocationpermission();

        listpoint lp = (listpoint)getApplicationContext();
        saveloc = lp.getLoc();
        saveloc.clear(); // to be sure that the list is empty before the tracking begin



        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent n = new Intent(getApplicationContext(), map.class);
                startActivity(n);
            }
        });

        /////////////////////////////////////////// pedometer ////////////////////////////////////

        prog = findViewById(R.id.progressBar); // initialized progress bar for step counter

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infopopu();
            }
        });

        senman = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // allow access to different sensor of the phone
        onResume(); // begin the step counting


        if (senman.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            nbs = senman.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); // take the spet counter sensor
        } else {
            Toast.makeText(this, "no step counter", Toast.LENGTH_SHORT).show();
        }

    }




    //////////////////////////////////////////////////////////////////////////////pedometer/////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == nbs) { // i use sensor to count each step compare if sensor use is a step counter sensor
            stepc = (int) event.values[0];
            nbp.setText(String.valueOf(stepc));
            prog.setProgress((stepc/100)); // add it to the progress bar in purcentages the number of step
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (senman.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) // check if there is a step counter sensor
            senman.registerListener(this, nbs, SensorManager.SENSOR_DELAY_NORMAL); // count


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (senman.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            senman.unregisterListener(this, nbs); // stop counting

    }

    public void infopopu() {// popup for how app works
        //create a credit popup with xml file
        dia = new AlertDialog.Builder(this);
        final View popuset = getLayoutInflater().inflate(R.layout.infopopop, null);
        close = popuset.findViewById(R.id.close2);

        dia.setView(popuset);
        dialo = dia.create();
        dialo.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialo.dismiss();
            }
        });

    }


    private void asklocationpermission() { // ask permission for the location to use the map
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE); // show alert dialog
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) { //
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) // ask permission
                //permission granted
                map.setClickable(true);
            else { // if user click on deny can't continue
                //permission not granted
                Toast.makeText(this, "this app requires permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }



}