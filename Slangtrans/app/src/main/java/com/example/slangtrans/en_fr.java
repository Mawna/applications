package com.example.slangtrans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class en_fr extends AppCompatActivity /*implements Addword.Test*/ {

    RecyclerView recyclerView;


    String s1[], s2[],s3[]; // my array with my informations
    int bin = R.drawable.delete;
    int imagae = R.drawable.en;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_fr);

        recyclerView = findViewById(R.id.recyclerView); // init my recyclerview

        //Datas already save
        s1 = getResources().getStringArray((R.array.English_word));
        s2 = getResources().getStringArray((R.array.French_word));
        s3 = getResources().getStringArray((R.array.French_word2));


        MyAdapter myAdapter = new MyAdapter(this,s1,s2,s3,imagae, bin); // create link with mmy adaptater
        recyclerView.setAdapter(myAdapter); // set the adapter that provides the datas
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //to set the layout of the contents


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater(); // create menu from xml resources
        inf.inflate(R.menu.menu,menu);
        return true;
    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); // pick up the id of our item to choose what app have to do
        if(id == R.id.item1) {
            Intent list = new Intent(getApplicationContext(), fr_en.class);
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