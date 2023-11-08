package com.codegama.Taskscheduler.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codegama.Taskscheduler.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class leaderboard extends AppCompatActivity {

    RecyclerView recview;
    custom_adapter adapter;
    TextView txt;
    private DatabaseReference post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        txt=findViewById(R.id.users_info);
        recview=findViewById(R.id.recview);



        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        txt.setText("user's info on "+thisDate);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recview.setLayoutManager(linearLayoutManager);

        // get data from firebase database
        // and order by completed task in recyclerview

        post=FirebaseDatabase.getInstance().getReference().child("task");
        Query query=post.orderByChild("total");

        FirebaseRecyclerOptions<model_class> options =
                new FirebaseRecyclerOptions.Builder<model_class>()
                        .setQuery(query, model_class.class)
                        .build();

        adapter=new custom_adapter(options);
        recview.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}