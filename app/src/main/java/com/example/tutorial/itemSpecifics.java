package com.example.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class itemSpecifics extends AppCompatActivity {
    FirebaseDatabase database;
    TextView title,desc;
    ImageView img;
    String strPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_specifics);

        Intent intent=getIntent();
        String key=intent.getStringExtra("key");

        title = (TextView)findViewById(R.id.textView10);
        desc = (TextView)findViewById(R.id.textView8);
        img = (ImageView)findViewById(R.id.imageView3);


        database = FirebaseDatabase.getInstance();
        database.getReference("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    if(messageData.getKey().equals(key)){
                        String strDesc = messageData.child("desc").getValue().toString();
                        String strTitle = messageData.child("title").getValue().toString();
                        String strImgId = messageData.child("img").getValue().toString();
                        strPostId = messageData.child("postId").getValue().toString();

                        title.setText(strTitle);
                        desc.setText(strDesc);
                        FirebaseStorage storage = FirebaseStorage.getInstance("gs://meditrade-e4574.appspot.com");
                        StorageReference storageRef = storage.getReference();
                        storageRef.child("images/"+strImgId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //이미지 로드 성공시
                                Glide.with(getApplicationContext()).load(uri).centerCrop().into(img);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //이미지 로드 실패시
                            }
                        });

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Button button4 = (Button) findViewById(R.id.button4) ;
        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemSpecifics.this, donorInfo.class) ;
                intent.putExtra("postId",strPostId);
                startActivity(intent) ;
            }
        });
    }
}