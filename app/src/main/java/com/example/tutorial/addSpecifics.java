package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class addSpecifics extends AppCompatActivity {

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specifics);
        Intent intent = getIntent();
        Uri filePath = intent.getParcelableExtra("filepath");
        //change uri to string
        String imageString;
        imageString = filePath.toString();

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = (ImageView)findViewById(R.id.imageViewx);
        imageView.setImageBitmap(bitmap);

        //spinner
        Spinner sp_cat = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(yearAdapter);
        String spinnerString = sp_cat.getSelectedItem().toString();

        //wordcounter
        TextView wordcounter = (TextView)findViewById(R.id.wordcounter);
        EditText editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        String descString = editTextDesc.getText().toString();
        editTextDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = editTextDesc.getText().toString();
                wordcounter.setText(input.length()+" / 100");
                if(input.length() == 100)
                    wordcounter.setTextColor(Color.parseColor("#FF0000"));
                else
                    wordcounter.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //gettexts
        EditText editTextTitle = (EditText) findViewById(R.id.productTitle);



        // titleString, imageString, descString, spinnerString




        database = FirebaseDatabase.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();



        Button postButton = (Button) findViewById(R.id.post);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleString = editTextTitle.getText().toString();
                String spinnerString = sp_cat.getSelectedItem().toString();
                String descString = editTextDesc.getText().toString();
                String imageString = filePath.toString();
                String email = user.getEmail();
                firebase_add(titleString,imageString,email,descString,spinnerString);

            }
        });
    }

    public void firebase_add(String titleString,String imageString,String email,String descString, String spinnerString){
        database = FirebaseDatabase.getInstance();
        DatabaseReference query = database.getReference("post");
        String key = query.push().getKey();

        Map<String, Object> map = new HashMap<>();
        map.put("cat", spinnerString);
        map.put("img", imageString);
        map.put("postId", email);
        map.put("title",titleString);
        map.put("desc",descString);
        map.put()

        query.child(key).setValue(map);
    }

}