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

public class Ownwords extends AppCompatActivity {

    //like en_fr

    RecyclerView recyclerView;
    FloatingActionButton add3;
    ImageView delete;

    Mydatabase myDB;
    ArrayList<String> id, english, french;
    MyAdaptatersave myadasave;
    //String id ;
    int image = R.drawable.en;
    String id1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownwords);

        recyclerView = findViewById(R.id.recyclerView3);
        //delete = findViewById(R.id.delete);


        myDB = new  Mydatabase(Ownwords.this);
        id = new ArrayList<>();
        english = new ArrayList<>();
        french = new ArrayList<>();



        storeData();

        //delete.setOnClickListener();

        myadasave = new MyAdaptatersave(Ownwords.this, id,english, french,image);
        recyclerView.setAdapter(myadasave);
        recyclerView.setLayoutManager(new LinearLayoutManager(Ownwords.this));

        add3 = findViewById(R.id.add3);
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ownwords.this,Addword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void storeData(){ // store my data in the right collumn
        Cursor cur = myDB.readData(); // read the data of the database
        if(cur.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cur.moveToNext()){
                // pick up the data in the database
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
                Mydatabase mdb = new Mydatabase(Ownwords.this);
                mdb.deleall();
                Intent ne= new Intent(Ownwords.this, Ownwords.class); // restart activity
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
            Intent list = new Intent(getApplicationContext(), fifth.class);
            startActivity(list);
            finish();
        }
        if(id == R.id.item12) {
            Intent list1 = new Intent(getApplicationContext(), en_fr.class);
            startActivity(list1);
            finish();
        }
        if(id == R.id.deleteall) {
            confirmDialogALL();

        }

        return super.onOptionsItemSelected(item);
    }

}