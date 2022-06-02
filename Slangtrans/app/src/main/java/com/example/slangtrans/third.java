package com.example.slangtrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class third extends AppCompatActivity {

    //like second activity

    ImageView back,ima2;
    TextView fre, eng;
    String data4,data5;

    int im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ima2 = findViewById(R.id.imageView);

        fre = findViewById(R.id.TitleFr);
        eng = findViewById(R.id.descriptionEn);

        getData();
        setData();

        this.back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(),fr_en.class);
                startActivity(change);
                finish();
            }
        });


    }
    private void getData() {
        if(getIntent().hasExtra("data3")&& getIntent().hasExtra("data4")) // verify if data exist
        {
            data4 = getIntent().getStringExtra("data4");
            data5 = getIntent().getStringExtra("data5");

        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

        }

    }
    private void setData(){
        fre.setText(data4);
        eng.setText(data5);

    }
    }
