package com.codegama.Taskscheduler.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codegama.Taskscheduler.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class custom_adapter extends FirebaseRecyclerAdapter<model_class,custom_adapter.my_view_holder> {

    public custom_adapter(@NonNull FirebaseRecyclerOptions<model_class> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull my_view_holder holder, int position, @NonNull model_class model) {

          // set data per view from
         //  realtime database
           holder.email.setText(model.getUsername());
           holder.number.setText("task completed :"+model.getTotal());
           holder.quit.setText("task missed: "+model.getQuit());

            Glide.with(holder.itemView.getContext())
                .load(model.getImg_url())
                .into(holder.image);
    }
    @NonNull
    @Override
    public my_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout,parent,false);
        return new my_view_holder(view);
    }
    class  my_view_holder extends RecyclerView.ViewHolder
    {
        TextView email,number,quit;
        CircularImageView image;

        // initialize feild that used per view

        public my_view_holder(@NonNull View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.ans);
            number=itemView.findViewById(R.id.number);
            quit=itemView.findViewById(R.id.quit_);
            image=itemView.findViewById(R.id.img);
        }
    }
}
