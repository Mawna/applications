package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintsChangedListener;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class graph extends AppCompatActivity  {

    ImageView back;
    List<Location> saveloc;
    ArrayList<Float> distlist = new ArrayList<>();
    ArrayList<Float> speed = new ArrayList<>();// for the speed value
    TextView averagesp, totdist,tottime, altitude;

    long hours, min, sec;

    private LineChart mChart;

    ArrayList<Entry> yValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        if(getIntent().hasExtra("hours") && getIntent().hasExtra("min") && getIntent().hasExtra("sec"))
        {
            hours = getIntent().getLongExtra("hours",0);
            min = getIntent().getLongExtra("min",0);
            sec = getIntent().getLongExtra("sec",0);
        }

        // i have implement something to create the graph

        listpoint lp = (listpoint)getApplicationContext();
        saveloc = lp.getLoc(); // pick up the list of location

        back = findViewById(R.id.back);
        listpoint t = (listpoint) getApplicationContext();
        saveloc = t.getLoc();
        back.setOnClickListener(new View.OnClickListener() { // just finish the app and clear the list of point
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(),find.class);
                startActivity(back);
                finish();


            }
        });

        // calcule average

        float average=0;

        float dist1 = 0;
        double alti = 0;
        for (int i = 0; i<saveloc.size()-1; i++)
        {

                float dist = saveloc.get(i).distanceTo(saveloc.get(i+1)); //  take dist between each point saved
                distlist.add(dist); //  take diste in each point
                double tmp = saveloc.get(i).getAltitude(); // altitude max
                if (alti<tmp)
                {
                    alti = tmp;
                }
                float sp = (dist/5)*3.6f; // search the speed in km/h between each point /5 because position is taking each 5 sec
                speed.add(sp) ;
                dist1+=dist; // calcul total dist
                average+=sp;



        }
        average = average/(saveloc.size()-2); // find average speed

        averagesp = findViewById(R.id.spnb);
        String av = String.format(Locale.getDefault(),"%.2f km/h",average);
        averagesp.setText(av); // display speed

        totdist = findViewById(R.id.totdist);
        String di = String.format(Locale.getDefault(),"%.3f km",dist1/1000);
        totdist.setText(di);

        altitude = findViewById(R.id.altinb);
        String alt = String.format(Locale.getDefault(),"%.2f m",alti); //2 numbers after the coma
        altitude.setText(alt);


        mChart = findViewById(R.id.graph); // use graph

        // allow to set the graph
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        yValue = new ArrayList<>(); // value of graph
        float time = 0;
        for (int i=0;i<speed.size();i++) {

            float a = speed.get(i);
            yValue.add(new Entry(time,a)); // for each speed value i put the corresponding time on abs
            time+=5;
        }
        tottime = findViewById(R.id.tottime);
       /* long min =0;
        while (times > 60)
        {
            times = times/60;
            min++;
        }*/

        if(min < 10 && sec>10)
            tottime.setText("0"+hours +" : 0" + min + " : "+ sec );

        else if(min < 10 && sec<10)
            tottime.setText("0"+hours +" : 0" + min + " : 0"+ sec );

        else if(min > 10 && sec < 10)
            tottime.setText("0"+hours +" : " + min + " : 0"+ sec );

        else
            tottime.setText("0"+hours +" : " + min + " : "+ sec );

        LineDataSet set1 = new LineDataSet(yValue,"Running"); // draw the line
        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data); // link each point with the line




    }
}