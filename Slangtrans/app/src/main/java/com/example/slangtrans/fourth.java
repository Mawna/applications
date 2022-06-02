package com.example.slangtrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class fourth extends AppCompatActivity {

    //like second en_fr


    ImageView back,ima2;
    TextView eng, fre;
    String data1,data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        ima2 = findViewById(R.id.imageView);

        eng = findViewById(R.id.TitleEn);
        fre = findViewById(R.id.descriptionFr);

        getData();
        setData();

        this.back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(),Ownwords.class);
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
        eng.setText(data1);
        fre.setText(data2);

    }
}