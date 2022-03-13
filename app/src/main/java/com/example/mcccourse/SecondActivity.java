package com.example.mcccourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class SecondActivity extends AppCompatActivity {

    FirebaseStorage fs;
    StorageReference parentRef;
    StorageReference childRef;
    ImageView imageView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = findViewById(R.id.img_view);
        progressBar = findViewById(R.id.progressBar);

        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 1);

        String path = "storage/emulated/0/Pictures/IMG_20220110_080453.jpg";
        File file = new File(path);
        Uri uri = Uri.fromFile(file);

        fs = FirebaseStorage.getInstance();

        parentRef = fs.getReference();

        Long fileLiveName = System.currentTimeMillis()/1000;
        String fileLivePath = "uploads/images/"+fileLiveName;
        childRef = parentRef.child(fileLivePath);

        UploadTask uploadTask = childRef.putFile(uri);

        //progressBar.setVisibility(View.VISIBLE);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressBar.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(uri).into(imageView);
                        Log.d("ttt", uri.toString());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", e.getMessage());
            }
        });


    }
}