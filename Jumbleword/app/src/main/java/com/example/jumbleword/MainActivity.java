package com.example.jumbleword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {
    Button play,setting,rules;
    private AlertDialog.Builder dialogbuilder,dia;
    private AlertDialog dialog,dialo;
    private Switch Music;
    private Switch Soud;
    private Button credits;
    private TextView test, rule;
    private ImageButton close, close2;
    MediaPlayer mus ;
    private int  chs ;
    private int chm  ;


    public int setchm(int x)
    {return chm=x;} // to know if music has to be switch on or not

    // to know if button sound has to be switch on or not
    public int getChs(){
        return chs;
    }
    public int setchs(int x)
    {return chs=x;}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // blocked the phone orientation
        setContentView(R.layout.activity_main);

        // initialized sound and music
       final MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.se2);
        mus = MediaPlayer.create(this,R.raw.music);
        mus.start();
        mus.setLooping(true); // loop the music

        //initialized automatically
        setchm(1);
        setchs(1);



        play = findViewById(R.id.play);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chs == 1) // play or not the sound of the button
                    buttonsound.start();
                else
                    buttonsound.pause();

                //link between Chapter activity
                Intent chap = new Intent(getApplicationContext(), Chapter.class);
                chap.putExtra("chs",getChs()); // fllow an int to know if button has to have a sound or not
                startActivity(chap);
            }
        });

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chs == 0)
                    buttonsound.pause();
                else
                    buttonsound.start();
               settingpopup();
            }
        });
        rules = findViewById(R.id.Rules);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
                rulespopup();
            }
        });

    }

    public void settingpopup(){
        // create a popup setting thanks to an xml file
        dialogbuilder = new AlertDialog.Builder(this);
        final View popuset = getLayoutInflater().inflate(R.layout.activity_popup, null);
        Music = (Switch) popuset.findViewById(R.id.music);
        Soud = (Switch) popuset.findViewById(R.id.sound1);
        credits = (Button) popuset.findViewById(R.id.credit);
        close = (ImageButton) popuset.findViewById(R.id.close);
        test = (TextView) popuset.findViewById(R.id.textView);

        // to keep the value and the button at the right place
        if (chm == 1)
            Music.setChecked(true);
        else
            Music.setChecked(false);

        if (chs == 1)
            Soud.setChecked(true);
        else
            Soud.setChecked(false);

        MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.se2);

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creditspopu();
                if (chs == 1) // play or not the sound of the button
                    buttonsound.start();
                else
                    buttonsound.pause();


            }
        });


        Soud.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    setchs(1);
                }
                else{
                    setchs(0);

                }

            }
        });
        // played an stoped music
        Music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    setchm(1);
                    mus.start();
                    mus.setLooping(true);
                }
                else
                {
                    setchm(0);
                    mus.pause();
                }

            }
        });

        //create the popup
        dialogbuilder.setView(popuset);
        dialog = dialogbuilder.create();
        dialog.show();



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    public void rulespopup(){
        //create a rules popup with xml file
        dialogbuilder = new AlertDialog.Builder(this);
        final View popuset = getLayoutInflater().inflate(R.layout.popup_rules, null);
        rule = (TextView) popuset.findViewById(R.id.exp);

        close2 = (ImageButton) popuset.findViewById(R.id.close2);

        dialogbuilder.setView(popuset);
        dialog = dialogbuilder.create();
        dialog.show();

        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void creditspopu(){
        //create a credit popup with xml file
        dia = new AlertDialog.Builder(this);
        final View popuset = getLayoutInflater().inflate(R.layout.popupcredit, null);
       close = popuset.findViewById(R.id.close3);

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

}