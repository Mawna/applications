package com.example.slangtrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class six extends AppCompatActivity {

    //like second activity

    ImageView back,ima2;
    TextView eng, fre;
    String data1,data2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        ima2 = findViewById(R.id.imageView);

        fre = findViewById(R.id.TitleFr);
        eng = findViewById(R.id.descriptionEn);

        getData();
        setData();

        this.back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(),fifth.class);
                startActivity(change);
                finish();
            }
        });


    }

    private void getData() {
        if(getIntent().hasExtra("w1")&& getIntent().hasExtra("w2")) // verify if data exist
        {
            data1 = getIntent().getStringExtra("w1");
            data2 = getIntent().getStringExtra("w2");


        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

        }

    }
    private void setData(){
        fre.setText(data1);
        eng.setText(data2);

    }
}