package com.example.slangtrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends AppCompatActivity {

    // when user will clic on one layout this actiity appears

    ImageView back,ima2;
    TextView eng, fre;
    String data1,data3;

    int im;
    //int check = MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ima2 = findViewById(R.id.imageView);

        eng = findViewById(R.id.TitleEn);
        fre = findViewById(R.id.descriptionFr);

        getData();
        setData();

        this.back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(),en_fr.class);
                startActivity(change);
                finish();
            }
        });


    }
    private void getData() {
        if(getIntent().hasExtra("data1")&& getIntent().hasExtra("data3")) // verify if data exist
        {
            data1 = getIntent().getStringExtra("data1"); // pick up data in our array
            data3 = getIntent().getStringExtra("data3");

        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

        }

    }
    private void setData(){
        eng.setText(data1); // set the text at the right place
        fre.setText(data3);

    }

}