package com.codegama.Taskscheduler.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.codegama.Taskscheduler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dashboard extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    TextView txt,usd,bio;
    FirebaseAuth mAuth;
    ImageView tasklist,cf,history,aboutapp,todays_task,achievement;
    DatabaseReference databaseReference,databaseReference1;
    CircularImageView image;
    StorageReference dpp;
    public int value =0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        tasklist=findViewById(R.id.ictasklist);
        todays_task=findViewById(R.id.ictoday);
        aboutapp=findViewById(R.id.icabout);
        achievement =findViewById(R.id.icconnect);
        navigationView.setItemIconTintList(null);
        cf=findViewById(R.id.iccf);
        history=findViewById(R.id.ichistory);
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


       // implement navigation bar

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(),dashboard.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        break;
                    case R.id.logout:
                        SharedPreferences settings =getSharedPreferences("quit", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        settings=getSharedPreferences("our_data", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        mAuth.signOut();
                        startActivity(new Intent(dashboard.this,sign_in.class));
                        break;
                    case R.id.share:
                        Intent intent1=new Intent( Intent.ACTION_SEND);
                        intent1.setType("text/plain");
                        intent1.putExtra(Intent.EXTRA_SUBJECT,"checkout this cool App");
                        intent1.putExtra(Intent.EXTRA_TEXT,"https://drive.google.com/file/d/1vnXVYpjH9lpQTTzNx-ZPNHR0nVDtuifI/view?usp=sharing");
                        startActivity(Intent.createChooser(intent1,"ShareVia"));
                        break;
                    case R.id.feedback:
                        Intent intent2 = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","mahedihassanrabby420@gmail.com", null));
                        intent2.putExtra(Intent.EXTRA_SUBJECT,"feedback of Task Scheduler App");
                        intent2.putExtra(Intent.EXTRA_TEXT, "message");
                        startActivity(Intent.createChooser(intent2, ""));
                }
                return true;
            }
        });

        tasklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=1;
                startActivity(new Intent(dashboard.this,MainActivity.class));
            }
        });
        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this,cfreminder.class));
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this,history.class));
            }
        });
        aboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this,about.class));
            }
        });
        todays_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //using shared preference

                databaseReference= FirebaseDatabase.getInstance().getReference("task");
                SharedPreferences sharedPreferences=getSharedPreferences("our_data", MODE_PRIVATE);
                SharedPreferences share=getSharedPreferences("quit",MODE_PRIVATE);
                SharedPreferences shared=getSharedPreferences("profile_data", MODE_PRIVATE);

                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);
                String s=thisDate.substring(0,2);
                Integer s2int=Integer.parseInt(s);
                String date_final_string=Integer.toString(s2int);
                String completed_task=sharedPreferences.getString(date_final_string,"0");
                String quit_task=share.getString(date_final_string,"0");
                Integer total_task=Integer.parseInt(completed_task);
                Integer total_quit=Integer.parseInt(quit_task);
                String currentUserId = mAuth.getCurrentUser().getUid().toString();
                FirebaseUser user =mAuth.getCurrentUser();
                String name=user.getEmail();

                String username=shared.getString(currentUserId,name);


                 String url ;
                String filename=FirebaseAuth.getInstance().getCurrentUser().getEmail();

                //get url of profile pic and upload it on
                // firebase storage

                FirebaseStorage.getInstance().getReference("images/"+filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseStorage.getInstance().getReference("images/"+filename).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String imageurl =task.getResult().toString();

                                model_class model_class=new model_class(total_task,total_quit,username,imageurl);
                                databaseReference.child(currentUserId).setValue(model_class);
                                startActivity(new Intent(dashboard.this,leaderboard.class));
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String imageurl="https://firebasestorage.googleapis.com/v0/b/todolistapplication-f557b.appspot.com/o/images%2Fdefault.jpg?alt=media&token=e63378e8-0437-4ac5-a5c8-6ff10bcfb466";

                        model_class model_class=new model_class(total_task,total_quit,username,imageurl);
                        databaseReference.child(currentUserId).setValue(model_class);
                        startActivity(new Intent(dashboard.this,leaderboard.class));

                    }
                });
            }
        });
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this,Achievements.class));
            }
        });

    }
    protected void onStart() {
        super.onStart();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(dashboard.this,sign_up.class));
        }
        else
        {
            SharedPreferences sharedPreferences=getSharedPreferences("profile_data", MODE_PRIVATE);
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();


            String name=user.getEmail();
            View header = navigationView.getHeaderView(0);
            txt = header.findViewById(R.id.profilename);
            txt.setText(sharedPreferences.getString(currentUserId,"set my name"));
            currentUserId=currentUserId+"add";

            image=header.findViewById(R.id.profilepic);
            bio=header.findViewById(R.id.biog);
            bio.setText(sharedPreferences.getString(currentUserId,"set my bio"));

           // add profile pic in navigation bar if
            // user already upload it
            FirebaseStorage.getInstance().getReference("images/"+name).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    FirebaseStorage.getInstance().getReference("images/"+name).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String profileImageUrl=task.getResult().toString();
                            Glide.with(dashboard.this)
                                    .load(profileImageUrl)
                                    .into(image);
                        }
                    });
                }
            });
        }
    }

}