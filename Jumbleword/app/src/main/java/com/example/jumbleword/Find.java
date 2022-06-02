package com.example.jumbleword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Find extends AppCompatActivity  {

    private TextView wordjumble, nbtry;
    private EditText wordtry;
    private String good;
    Button valid;
    ImageView back;
    private String[] list ;


    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private Button level;
    private ImageButton close;

    ArrayList<String> list2 = new ArrayList<>();


    int i,nb,j,chs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_find);

        final MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.se2);

        //get back the chs value
        if(getIntent().hasExtra("chs") )
        {
            chs = getIntent().getIntExtra("chs",0);
        }

        // get back the right array and the right numbers of try in terms of levels with the name of the put extra an

        if(getIntent().hasExtra("l1") )
        {
            Chapter c = new Chapter();
            list = c.getL1();
            nb = getIntent().getIntExtra("l1",10);

        }

        if(getIntent().hasExtra("l2") )
        {
            Chapter c = new Chapter();
            list = c.getL2();
            nb = getIntent().getIntExtra("l2",8);
        }
        if(getIntent().hasExtra("l3") )
        {
            Chapter c = new Chapter();
            list = c.getL3();
            nb = getIntent().getIntExtra("l3",6);
        }
        if(getIntent().hasExtra("l4") )
        {
            Chapter c = new Chapter();
            list = c.getL4();
            nb = getIntent().getIntExtra("l4",4);
        }
        if(getIntent().hasExtra("l5") )
        {
            Chapter c = new Chapter();
            list = c.getL5();
            nb = getIntent().getIntExtra("l5",2);
        }
        if(getIntent().hasExtra("l6") )
        {
            Chapter c = new Chapter();
            list = c.getL6();
            nb = getIntent().getIntExtra("l6",1);
        }

        back = findViewById(R.id.back);

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


        // fill the list with the words in the array
        for(int p = 0 ; p<list.length; p++ )
        {
            list2.add(list[p]);
        }
        this.wordjumble = findViewById(R.id.anagram);
        this.wordtry = findViewById(R.id.tryword);
        this.nbtry = findViewById(R.id.tryw);

        valid = findViewById(R.id.valid);
         i =0; // counter

        //new random to take a random wor in the list
        Random rnd = new Random();
        j = rnd.nextInt(list2.size());
        next(list2, j);
        int tmp = nb;
        int l = list.length;

        nbtry.setText("Try : " + nb);


        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
                String w = wordtry.getText().toString().toUpperCase(); // word to enter

                nbtry.setText("Try : " + nb); // try that user have
                if (i>=l ) // if i>10 means that no word in array then user wins
                {
                   winpopup();
                }
                else
                {
                    if(good.equals(w)) // good = right word comapr to word given by user
                    {
                        Toast.makeText(Find.this, "Congratulation ! the word were : "+ good, Toast.LENGTH_SHORT).show();
                        i++;
                        if (i >= l)
                            winpopup();
                        else {
                            list2.remove(j); // remove to don't have several time a same word
                            nb = tmp;
                            Random rnd = new Random(); // take a new random in the new list
                            j = rnd.nextInt(list2.size());
                            next(list2, j);
                            nbtry.setText("Try : " + nb);


                             }
                    }
                    else {
                        nb--;

                        if (nb <= 0) { // is nb try < 0 user loose
                            losepopup();
                            valid.setClickable(false); // avoid player to click on validate
                        }
                        else {

                            Toast.makeText(Find.this, "Retry", Toast.LENGTH_SHORT).show();


                        }

                    }

                }
            }
        });

    }

    public String jumble (String word){ // to switch letter
        //String res = word;

        if(word != null && !"".equals(word)){ // check if the word is not null
            char a[] = word.toCharArray(); // crate an array with the string

            for(int i = 0;i<a.length;i++)
            {
                int j = (int) (Math.random() * word.length()-1 ); //  create a random then a swap to swap letter
                char tmp = a[i];
                a[i]=a[j];
                a[j]= tmp;
            }
            return new String(a);
        }
        return word;

    }


    private void next(ArrayList<String> tab, int j)
    {
        good = tab.get(j); //  set good to the word pick up by the random in the list
        String anagram = jumble(good); // jumble the word
        wordjumble.setText(anagram); // set the settext with the anagram
        wordtry.getText().toString();

    }


    public void losepopup(){// create loose popup
        dialogbuilder = new AlertDialog.Builder(this);
        final View popuset = getLayoutInflater().inflate(R.layout.popupfind, null);
        level = (Button) popuset.findViewById(R.id.level2);

        final MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.se2);

        dialogbuilder.setView(popuset);
        dialog = dialogbuilder.create();
        dialog.show();



        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
                dialog.dismiss();
                finish();
            }
        });

    }
    public void winpopup(){ // create win popup
        dialogbuilder = new AlertDialog.Builder(this);
        final View popuset = getLayoutInflater().inflate(R.layout.popupcongrat, null);
        level = (Button) popuset.findViewById(R.id.level3);

        final MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.se2);

        dialogbuilder.setView(popuset);
        dialog = dialogbuilder.create();
        dialog.show();

        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chs == 1)
                    buttonsound.start();
                else
                    buttonsound.pause();
                dialog.dismiss();
                finish();
            }
        });

    }


}