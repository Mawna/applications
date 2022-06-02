package com.example.slangtrans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyView2> {
    //like adaptatersave

    String data3[],data4[],data5[];
    int image2,bins;
    Context context;

    public MyAdapter2(Context ct, String s3[], String s4[],String s5[], int images, int bin){
        context = ct;
        data3 = s3;
        data4 = s4;
        bins = bin;
        data5 = s5;
        image2 = images;
    }

    @NonNull
    @Override
    public MyView2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent,false);
        return new MyAdapter2.MyView2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView2 holder, int position) {
        holder.fr.setText((data3[position]));
        holder.eng.setText((data4[position]));
        holder.im.setImageResource(image2);
        holder.bin.setVisibility(View.INVISIBLE);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, third.class);
                intent.putExtra("data4",data4[position]);
                intent.putExtra("data3",data3[position]);
                intent.putExtra("data5",data5[position]);
                intent.putExtra("myImage",image2);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data3.length;
    }

    public class MyView2 extends RecyclerView.ViewHolder {

        TextView fr, eng;
        ImageView im, bin;
        ConstraintLayout mainLayout;


        public MyView2(@NonNull View itemView) {
            super(itemView);
            bin = itemView.findViewById(R.id.delete);
            fr = itemView.findViewById(R.id.French_wordtxt);
            eng = itemView.findViewById(R.id.English_wordtxt);
            im = itemView.findViewById(R.id.myImage);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
