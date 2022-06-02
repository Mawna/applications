package com.example.slangtrans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class fifth extends AppCompatActivity {

    // as second activity

    RecyclerView recyclerView;
    FloatingActionButton add4;
    ImageView delete2;

    Mydatabase myDB;
    ArrayList<String> id, english, french;
    MyAdaptatersave2 myadasave2;
    int image = R.drawable.fr;


    String id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);


        recyclerView = findViewById(R.id.recyclerView4);
        //delete = findViewById(R.id.delete);


        myDB = new  Mydatabase(fifth.this);
        id = new ArrayList<>();
        english = new ArrayList<>();
        french = new ArrayList<>();

        storeData();

        //delete.setOnClickListener();

        myadasave2 = new MyAdaptatersave2(fifth.this, id,english, french,image);
        recyclerView.setAdapter(myadasave2);
        recyclerView.setLayoutManager(new LinearLayoutManager(fifth.this));

        add4 = findViewById(R.id.add4);
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fifth.this,Addword2.class);
                startActivity(intent);
                finish();
            }
        });


    }
    void storeData(){
        Cursor cur = myDB.readDataSec();
        if(cur.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cur.moveToNext()){
                id.add(cur.getString(0));
                english.add(cur.getString(1));
                french.add(cur.getString(2));
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu2,menu);
        return true;
    }

    void confirmDialogALL()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure ypu want to delete every words?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Mydatabase mdb = new Mydatabase(fifth.this);
                mdb.deleallsec();
                Intent ne= new Intent(fifth.this, fifth.class); // restart activity
                startActivity(ne);
                finish();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.create().show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.item11) {
            Intent list = new Intent(getApplicationContext(), Ownwords.class);
            startActivity(list);
            finish();
        }
        if(id == R.id.item12) {
            Intent list1 = new Intent(getApplicationContext(), en_fr.class);
            startActivity(list1);
            finish();
        }
        if(id == R.id.deleteall) {
            //Toast.makeText(this, "delete all", Toast.LENGTH_SHORT).show();
            confirmDialogALL();


        }

        return super.onOptionsItemSelected(item);
    }
}