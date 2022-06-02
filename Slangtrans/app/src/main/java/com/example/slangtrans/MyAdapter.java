package com.example.slangtrans;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyView>
{
    //like adaptatersave

    private String data1[], data2[], data3[];
    private int images, bins;
    private Context context;

    MyAdapter(Context ct, String s1[], String s2[],String s3[], int img, int binss)
    {
        this.context = ct;
        this.data1 = s1;
        this.data2 = s2;
        this.data3 = s3;
        this.bins=binss;
        this.images = img;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent,false);
        return new MyView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.english.setText(data1[position]);
        holder.french.setText(data2[position]);
        holder.myimage.setImageResource(images);
        holder.bin.setVisibility(View.INVISIBLE);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Second.class);
                intent.putExtra("data1",data1[position]);
                intent.putExtra("data2",data2[position]);
                intent.putExtra("data3",data3[position]);
                intent.putExtra("myImage",images);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView english, french;
        ImageView myimage, bin;
        ConstraintLayout mainLayout;


        public MyView(@NonNull View itemView) {
            super(itemView);
            english = itemView.findViewById(R.id.English_wordtxt);
            french = itemView.findViewById(R.id.French_wordtxt);
            myimage = itemView.findViewById(R.id.myImage);
            bin = itemView.findViewById(R.id.delete);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }


}
