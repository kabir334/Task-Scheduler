package com.codegama.Taskscheduler.activity;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codegama.Taskscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConestAdapter extends RecyclerView.Adapter<ConestAdapter.PostViewHolder> {
    //List for all upcoming cf contest
    List<Cfcontest> contestList;
    Context context;

    public ConestAdapter(Context context , List<Cfcontest> contests){
        this.context = context;
        contestList = contests;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cfcontesttile , parent , false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ConestAdapter.PostViewHolder holder, int position) {
        //getting current position of list
        Cfcontest cf = contestList.get(position);
        
        //Displaying contest Title
        holder.name.setText(cf.getName());

        //setting contest link
        String contest_link= cf.getUrl();
        holder.status.setText(contest_link);
        String clink =String.format("<a href=\"%s\">Enter</a> ", contest_link);
        holder.status.setText(Html.fromHtml(clink));
        holder.status.setMovementMethod(LinkMovementMethod.getInstance());
        
        //convetring gmt time to bd time and displaying start time of contest
        String gmt=cf.getStartTime().substring(11,13);
        Integer bd=Integer.parseInt(gmt);
        bd=(bd+6)%24;
        String st = cf.getStartTime().substring(0,10)+" ";
        if(bd<10)
            st=st+"0";

        st=st+bd+cf.getStartTime().substring(13,19);
        holder.start_time.setText(st);
        
        //checking if the contest is running or not
        if(cf.getStatus().equals("BEFORE"))
        {
            //calculating remaining time of the contest
            Date contestDate = null;
            try {
                contestDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(st);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date curDate =new Date();
            int diff= (int) (contestDate.getTime()-curDate.getTime());
            String bef;
            int hh,mm,dd;
            dd=diff/(1000*60*60*24);
            diff=diff%(1000*60*60*24);
            hh=diff/(1000*60*60);
            diff=diff%(1000*60*60);
            mm=diff/(1000*60);
            if(dd>1)
                bef="Before start: \n"+dd+" days";
            else if(dd==1)
                bef="Before start: \n"+(hh+24)+" hours "+mm+" min";
            else
                bef="Before start: \n"+hh+" hours "+mm+" min";

            holder.duration.setText(bef);
        }
        else
            holder.duration.setText("Runnnig");


    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        TextView name,duration,start_time,status;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            //XML elements finding
            name = itemView.findViewById(R.id.contitle);
            duration = itemView.findViewById(R.id.link);
            start_time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);

        }
    }

}
