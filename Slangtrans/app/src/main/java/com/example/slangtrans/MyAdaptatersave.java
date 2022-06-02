package com.example.slangtrans;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptatersave extends RecyclerView.Adapter<MyAdaptatersave.MyView1> {
    // important to link our layout and our recyclerview

    private ArrayList id,english,french; //array with our information

    int pos;

     int images;

     String id1;


    private Context context;

    // constructor

     MyAdaptatersave(Context ct, ArrayList id1, ArrayList eng, ArrayList fre, int im)
    {
        this.context = ct;
        this.id = id1;
        this.images = im;
        this.english = eng;
        this.french = fre;
    }

    @NonNull
    @Override
    public MyAdaptatersave.MyView1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(context);
        View view = inf.inflate(R.layout.my_row, parent, false); // inflate our layout with our xml file my_row which is our reference layout
        return new MyView1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdaptatersave.MyView1 holder, int position) { // holder = endroit ou je stocke toutes les variables de mon layput
        //set the text
        holder.engs.setText(String.valueOf(english.get(position)));
        holder.frs.setText(String.valueOf(french.get(position)));
        holder.myim.setImageResource(images);
        // delete in the database
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(position);

            }
        });

        // link my layouts with an activity
        holder.mly2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, fourth.class);
                intent.putExtra("w1",String.valueOf(english.get(position)));
                intent.putExtra("w2",String.valueOf(french.get(position)));
                intent.putExtra("myImage",images);
                context.startActivity(intent);
            }
        });


    }
    void confirmDialog(int position )
    {
        // create a popu with a confirmation of the delete
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete "+ english.get(position) + " ?");
        builder.setMessage("Are you sure you want to delete this word ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Mydatabase my = new Mydatabase(context);
                my.deletemain(String.valueOf(english.get(position)));
                Intent ne= new Intent(context, Ownwords.class); // restart activity
                context.startActivity(ne);

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing if it is no
            }
        });
        builder.create().show();
    }


    @Override
    public int getItemCount() {
        return id.size();
    } // count the number of item

    public class MyView1 extends RecyclerView.ViewHolder {

        TextView engs, frs;
        ImageView myim, delete;
        ConstraintLayout mly2;

        public MyView1(@NonNull View itemView) {
            // get id of text views
            super(itemView);
            engs = itemView.findViewById(R.id.English_wordtxt);
            frs = itemView.findViewById(R.id.French_wordtxt);
            myim = itemView.findViewById(R.id.myImage);
            mly2 = itemView.findViewById(R.id.mainLayout);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
