package com.example.jumbleword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Chapter extends AppCompatActivity {

    Button chap1,chap2,chap3,chap4,chap5,chap6;
    //array of each level
    private String[] l1 ={"DOG","PET","CAT", "SUN", "HOT","BUT","SHE","DRY","WET","RUN"};
    private String[] l2 ={"FIND","WORD","EASY","THIS","BIRD","SICK","SHOE","SCAN","SAFE","SALE"};
    private String[] l3 ={"SEVEN","ABOUT","PIZZA","WATER","BOARD","PARTY","DREAM","RIVER","AFTER"};
    private String[] l4 ={"ABROOAD","DEIDE","CHOICE","LEGACY","FOSTER","ENTIRE","DANGER","FORGET","TISSUE"};
    private String[] l5 ={"ABILITY","GENERAL","FORTUNE","VERSION","OUTDOOR","OVERALL","ELEMENT","CLIMATE","CLOSING","WORKING"};
    private String[] l6 ={"CHILDREN","ABSTRACT","LIGHTING","FOREMOST","OFFERING","NUMEROUS","SPORTING","LIMITING","FOOTBALL","COMPRISE"};

    // to pick up each tab
    public String[] getL1(){return l1;}
    public String[] getL2(){return l2;}
    public String[] getL3(){return l3;}
    public String[] getL4(){return l4;}
    public String[] getL5(){return l5;}
    public String[] getL6(){return l6;}

    ImageView back;

    int chs ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_chapter);
        final MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.se2);

        //pick up chs from MainActivity
        if(getIntent().hasExtra("chs") )
        {
            chs = getIntent().getIntExtra("chs",0);
        }


        // link each button to xml code
        chap1 = findViewById(R.id.lv1);
        chap2 = findViewById(R.id.lv2);
        chap3 = findViewById(R.id.lv3);
        chap4 = findViewById(R.id.lv4);
        chap5 = findViewById(R.id.lv5);
        chap6 = findViewById(R.id.lv6);


        // in each oncliklistener i do a putextra to add the right tab to each level and i send it to find activity
        chap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
                Intent chap = new Intent(getApplicationContext(),Find.class);
                chap.putExtra("l1",10); // value is the number of try for each word
                chap.putExtra("chs",chs);  // pick up the chs value
                startActivity(chap);

            }
        });
        chap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chap = new Intent(getApplicationContext(),Find.class);
                chap.putExtra("l2",8);
                chap.putExtra("chs",chs);
                startActivity(chap);
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
            }
        });
        chap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chap = new Intent(getApplicationContext(),Find.class);
                chap.putExtra("l3",6);
                chap.putExtra("chs",chs);
                startActivity(chap);
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
            }
        });
        chap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chap = new Intent(getApplicationContext(),Find.class);
                chap.putExtra("l4",4);
                chap.putExtra("chs",chs);
                startActivity(chap);
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
            }
        });
        chap5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chap = new Intent(getApplicationContext(),Find.class);
                chap.putExtra("l5",2);
                chap.putExtra("chs",chs);
                startActivity(chap);
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
            }
        });
        chap6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chap = new Intent(getApplicationContext(),Find.class);
                chap.putExtra("l6",1);
                chap.putExtra("chs",chs);
                startActivity(chap);
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
            }
        });

        back = findViewById(R.id.back1);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
            }
        });
    }


}