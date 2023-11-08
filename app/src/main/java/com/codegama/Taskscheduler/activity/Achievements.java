package com.codegama.Taskscheduler.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codegama.Taskscheduler.R;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Achievements extends AppCompatActivity {


    TextView cur_rank,title,nd_more,nxt_rank;
    ImageView nxt,cur;
    FirebaseDatabase user;
    DatabaseReference cuser ;
    ProgressBar progress;
    int total=0,rank,needed,lvl;
    String ranks[]={"Bronge","Silver","Gold","Diamond","Platinum","titanium"};
    int[] rank_ic = {R.drawable.bronge,R.drawable.silver,R.drawable.gold,R.drawable.diamond,R.drawable.platinum,R.drawable.titanium};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //XML elements finding
        setContentView(R.layout.activity_achievements);
        cur_rank=findViewById(R.id.currank);
        nxt_rank=findViewById(R.id.nxtrank);
        title=findViewById(R.id.rank);
        nd_more=findViewById(R.id.more);
        nxt=findViewById(R.id.nxticon);
        cur=findViewById(R.id.curicon);
        progress=findViewById(R.id.prog);
        
        //calculating total completed task
        SharedPreferences sharedPreferences=getSharedPreferences("our_data", Context.MODE_PRIVATE);

        for(int i=1;i<=31;i++)
        {
            String key=Integer.toString(i);
            String result_string=sharedPreferences.getString(key,"0");
            Integer tsk=Integer.parseInt(result_string);
            total+=tsk;

        }
        //calculating rank,level,needed task for next leve of user
        rank =total/20;
        needed=20-(total%20);
        nd_more.setText("complete "+needed+" more tasks to complete this level");
        progress.setProgress((total%20)*5);
        lvl=rank+1;
        title.setText("Rank "+ lvl);
        //displaying data
        nxt.setImageResource(rank_ic[lvl]);
        cur.setImageResource(rank_ic[rank]);
        cur_rank.setText(ranks[rank]+" Badge");
        nxt_rank.setText(ranks[lvl]+" Level");





    }
}
