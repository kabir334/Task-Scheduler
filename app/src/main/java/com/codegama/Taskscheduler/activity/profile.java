package com.codegama.Taskscheduler.activity;

import static com.google.api.AnnotationsProto.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codegama.Taskscheduler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class profile extends AppCompatActivity {

   public EditText username,bio,gender;
   public TextView email,nam,biodata;
   public Button btn,btn2,load;
    private DatabaseReference databaseReference;
    private CircularImageView dp;
    StorageReference storageReference,loadref;
    Uri image_uri;
    ProgressDialog progressDialog;
    RadioButton radioButton;
    RadioGroup radioGroup;
    NavigationView navigationView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dp=findViewById(R.id.prfile_pic);


        username=findViewById(R.id.fullname);
        bio=findViewById(R.id.fab);
        radioGroup=findViewById(R.id.group);
       // load=findViewById(R.id.load);
        email=findViewById(R.id.email);
        //btn2=findViewById(R.id.btn2);
        String ema=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email.setText(ema);
        btn=findViewById(R.id.update);
        databaseReference= FirebaseDatabase.getInstance().getReference("users_info");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                String name=username.getText().toString();
                String bios=bio.getText().toString();
                String gender;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                gender=radioButton.getText().toString();

                SharedPreferences sharedPreferences=getSharedPreferences("profile_data", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(currentUserId,name);
                currentUserId=currentUserId+"add";
                editor.putString(currentUserId,bios);
                currentUserId=currentUserId+"add";
                editor.putString(currentUserId,gender);
                editor.commit();

                upimage();
            }
        });
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimage();
            }
        });
    }

    // load already uploaded profile pic

    private void loadimage() {
        String filename=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        loadref=FirebaseStorage.getInstance().getReference("images/"+filename);
        loadref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                String profileImageUrl=task.getResult().toString();
                Glide.with(profile.this)
                        .load(profileImageUrl)
                        .into(dp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "aaa "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //uploading image in firebase storage

    private void upimage() {

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Updating profile");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String filename=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        storageReference= FirebaseStorage.getInstance().getReference("images/"+filename);
        storageReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 Toast.makeText(getApplicationContext(),"Successfully uploaded",Toast.LENGTH_SHORT).show();
                 if(progressDialog.isShowing())
                     progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"upload failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // select image from device

    private void selectimage() {
          Intent intent=new Intent();
          intent.setType("image/*");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && data!=null && data.getData()!=null)
        {
           image_uri=data.getData();
           dp.setImageURI(image_uri);
        }
    }
}