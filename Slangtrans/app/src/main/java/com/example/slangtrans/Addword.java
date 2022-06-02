package com.example.slangtrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Addword extends AppCompatActivity {
    // addword

    private ImageView back;
    private Button Cancel;

    EditText english, french;
    Button add1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        // pick up id
        this.back = findViewById(R.id.arrow);
        this.Cancel = findViewById(R.id.cancel);


        english = findViewById(R.id.English_word);
        french = findViewById(R.id.French_word);
        add1 = findViewById(R.id.save);

        //allow to add a word in our data base
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// link data base to add button

                Mydatabase myDB = new Mydatabase(Addword.this);
                String en = english.getText().toString(); // pick up the string in the edit view
                String fr = french.getText().toString();
                if (en.isEmpty() && fr.isEmpty()) // user can't add an empty new layout
                    Toast.makeText(Addword.this, "Please, put informations", Toast.LENGTH_SHORT).show();
                else
                    myDB.addslang(en.trim(),fr.trim());
                myDB.sort(); // sort the data base but doesn't work 

            }
        });

        // make back and cancel for come backing in the right activity



        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v /*Test ch*/ ) {

                    Intent change = new Intent(getApplicationContext(), Ownwords.class);
                    startActivity(change);
                    finish();


            }
        });
        back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(),Ownwords.class);
                startActivity(change);
                finish();
            }
        });

    }

}