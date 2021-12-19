package com.example.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class donorInfo extends AppCompatActivity {
    FirebaseDatabase database;
    String email;
    String pn;
    String email2;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_info);
        Intent intent = getIntent();
        String strPostId = intent.getStringExtra("postId");
        title = intent.getStringExtra("title");
        database = FirebaseDatabase.getInstance();
        database.getReference("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String postId2 = messageData.child("postId").getValue().toString();
                    if(postId2.equals(strPostId)){
                        email = messageData.child("postId").getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        database.getReference("info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String id = messageData.child("id").getValue().toString();
                    if(id.equals(email)){
                        //Toast.makeText(donorInfo.this, messageData.child("pn").getValue().toString(), Toast.LENGTH_SHORT).show();
                        pn = messageData.child("pn").getValue().toString();
                        email2 = messageData.child("id").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Button email = (Button) findViewById(R.id.button7);
        email.setOnClickListener(new Button.OnClickListener(){
            @Override
        public void onClick(View v){
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",email2,null));
                    //intent.putExtra(Intent.EXTRA_SUBJECT, "Meditrade: Interest for "+title);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(donorInfo.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
        }
    });

        Button text = (Button) findViewById(R.id.button3);
        text.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("address",pn);
                startActivity(sendIntent);

            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.button6);
        next.setOnClickListener(new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(donorInfo.this, add.class) ;
            //after going to marketplace other location's stuff comes up
            startActivity(intent) ;
        }
    });
}
}