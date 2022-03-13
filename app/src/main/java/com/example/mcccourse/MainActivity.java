package com.example.mcccourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();



        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.name_edit_txt);
                String name = et.getText().toString();

                //Add a new user to collection users
                Map<String, Object> user = new HashMap<>();
                user.put("name", name);
                user.put("email", "mona@gmail.com");
                firestore.collection("users")
                        .add(user).addOnSuccessListener(documentReference -> {
                    Log.d("KKR", "Added is done with id: "+documentReference.getId());
                })
                        .addOnFailureListener(e -> {
                            Log.d("KKR", "My Error: "+e.getMessage());
                        });

            }
        });

        /*//Read All users data
        firestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot result = task.getResult();
                    for (QueryDocumentSnapshot document: result) {
                        String id = document.getId();
                        Map<String, Object> data= document.getData();

                        Log.d("KKR", "id: "+id+" name: "+data.get("name")+" email: "+data.get("email"));
                    }
                }
            }
        });*/


    }
}