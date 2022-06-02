package com.example.slangtrans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fr_en extends AppCompatActivity{

    // like en_fr

    String s3[], s4[], s5[];
    int image = R.drawable.fr;
    int bin = R.drawable.delete;


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fr_en);

        //checkup.setCheck(2);

        recyclerView = findViewById(R.id.recyclerView2);



        s3 = getResources().getStringArray((R.array.Frenchslang_word));
        s4 = getResources().getStringArray((R.array.English_wordtrad));
        s5 = getResources().getStringArray((R.array.Englexplain3));


        MyAdapter2 myAdapter2 = new MyAdapter2(this, s3,s4,s5,image, bin);
        recyclerView.setAdapter(myAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item1) {
            Intent list = new Intent(getApplicationContext(), en_fr.class);
            startActivity(list);
            finish();
        }
        if(id == R.id.item3) {
            Intent list1 = new Intent(getApplicationContext(), Ownwords.class);
            startActivity(list1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}