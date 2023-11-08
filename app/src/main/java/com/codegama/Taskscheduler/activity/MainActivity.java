package com.codegama.Taskscheduler.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codegama.Taskscheduler.R;
import com.codegama.Taskscheduler.bottomSheetFragment.CreateTaskBottomSheetFragment;
import com.codegama.Taskscheduler.broadcastReceiver.AlarmBroadcastReceiver;
import com.codegama.Taskscheduler.database.DatabaseClient;
import com.codegama.Taskscheduler.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements CreateTaskBottomSheetFragment.setRefreshListener {

    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;
    FloatingActionButton addTask;
    @BindView(R.id.relaxtext)
    TextView relax;
    TaskAdapter taskAdapter;
    List<Task> tasks = new ArrayList<>();
    @BindView(R.id.noDataImage)
    ImageView noDataImage;
    RelativeLayout relativeLayout;
    TextView first,second,third,fourth,fifth,sixth,headline;
    LinearLayout linearLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpAdapter();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ComponentName receiver = new ComponentName(this, AlarmBroadcastReceiver.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Glide.with(getApplicationContext()).load(R.drawable.chill).into(noDataImage);
        addTask=findViewById(R.id.addTask);
        relativeLayout=findViewById(R.id.rela);
        first=findViewById(R.id.first);
        second=findViewById(R.id.FIRST);
        third=findViewById(R.id.second);
        fourth=findViewById(R.id.SECOND);
        fifth=findViewById(R.id.third);
        sixth=findViewById(R.id.THIRD);
        headline=findViewById(R.id.headline);
        linearLayout=findViewById(R.id.linear);


        // set animation of suggested task

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        first.startAnimation(animation);
        third.startAnimation(animation);
        fifth.startAnimation(animation);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate2);
        second.startAnimation(animation);
        fourth.startAnimation(animation);
        sixth.startAnimation(animation);




        SharedPreferences sharedPreferences=getSharedPreferences("our_data", Context.MODE_PRIVATE);


        addTask.setOnClickListener(view -> {
            CreateTaskBottomSheetFragment createTaskBottomSheetFragment = new CreateTaskBottomSheetFragment();
            createTaskBottomSheetFragment.setTaskId(0, false, this, MainActivity.this);
            createTaskBottomSheetFragment.show(getSupportFragmentManager(), createTaskBottomSheetFragment.getTag());
        });
        getSavedTasks();


    }

    public void setUpAdapter() {
        taskAdapter = new TaskAdapter(this, tasks, this);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        taskRecycler.setAdapter(taskAdapter);
    }

    private void getSavedTasks() {

        class GetSavedTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                tasks = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .dataBaseAction()
                        .getAllTasksList();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                noDataImage.setVisibility(tasks.isEmpty() ? View.VISIBLE: View.GONE);
                relax.setVisibility(tasks.isEmpty()?View.VISIBLE:View.GONE);
                relativeLayout.setVisibility(tasks.isEmpty()?View.VISIBLE:View.GONE);
                setUpAdapter();
            }
        }
        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    @Override
    public void refresh() {
        getSavedTasks();
    }
}
