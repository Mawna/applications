package com.example.slangtrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Addword2 extends AppCompatActivity {

    // same as add word but for the second table in my database

    private ImageView back;
    private Button Cancel;


    EditText english, french;
    Button add2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword2);

        this.back = findViewById(R.id.arrow2);
        this.Cancel = findViewById(R.id.cancel2);


        english = findViewById(R.id.en_word2);
        french = findViewById(R.id.fr_word2);
        add2 = findViewById(R.id.save2);



        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mydatabase myDB = new Mydatabase(Addword2.this);
                String en = english.getText().toString();
                String fr = french.getText().toString();
                if (en.isEmpty() && fr.isEmpty())
                    Toast.makeText(Addword2.this, "Please, put informations", Toast.LENGTH_SHORT).show();
                else
                    myDB.addslangsec(en.trim(),fr.trim());

            }
        });


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v /*Test ch*/ ) {

                Intent change = new Intent(getApplicationContext(), fifth.class); //a changer en fonction de la page !!!!
                startActivity(change);
                finish();


            }
        });
        back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent change = new Intent(getApplicationContext(),fifth.class);
                startActivity(change);
                finish();
            }
        });
    }
}