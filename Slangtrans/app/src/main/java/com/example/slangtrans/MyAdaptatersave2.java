package com.example.slangtrans;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptatersave2 extends RecyclerView.Adapter<MyAdaptatersave2.MyView2> {
    // like my adaptatersave
    private ArrayList id,english,french;

    int pos;

    int images;

    String id1;


    private Context context;

    MyAdaptatersave2(Context ct, ArrayList id1, ArrayList eng, ArrayList fre, int im)
    {
        this.context = ct;
        this.id = id1;
        this.images = im;
        this.english = eng;
        this.french = fre;
    }

    @NonNull
    @Override
    public MyAdaptatersave2.MyView2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(context);
        View view = inf.inflate(R.layout.my_row, parent, false);
        return new MyAdaptatersave2.MyView2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdaptatersave2.MyView2 holder, int position) {
        //String id11 = id1.toString();
        this.pos = position;
        holder.engs.setText(String.valueOf(french.get(position)));
        holder.frs.setText(String.valueOf(english.get(position)));
        holder.myim.setImageResource(images);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(position);

            }
        });

        holder.mly2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, six.class);
                intent.putExtra("w1",String.valueOf(english.get(position)));
                intent.putExtra("w2",String.valueOf(french.get(position)));
                intent.putExtra("myImage",images);
                context.startActivity(intent);
            }
        });


    }
    void confirmDialog(int position )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete "+ english.get(position) + " ?"); // have to change this !!
        builder.setMessage("Are you sure you want to delete this word ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Mydatabase my = new Mydatabase(context);
                my.deletemainSec(String.valueOf(english.get(position)));
                Intent ne= new Intent(context, fifth.class); // restart activity
                context.startActivity(ne);

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
    public int getItemCount() {
        return id.size();
    }

    public class MyView2 extends RecyclerView.ViewHolder {

        TextView engs, frs;
        ImageView myim, delete;
        ConstraintLayout mly2;

        public MyView2(@NonNull View itemView) {
            super(itemView);
            frs = itemView.findViewById(R.id.English_wordtxt);
            engs = itemView.findViewById(R.id.French_wordtxt);
            myim = itemView.findViewById(R.id.myImage);
            mly2 = itemView.findViewById(R.id.mainLayout);
            delete = itemView.findViewById(R.id.delete);
        }
    }




}

