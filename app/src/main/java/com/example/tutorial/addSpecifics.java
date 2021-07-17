package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import static java.security.AccessController.getContext;

public class addSpecifics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specifics);
        Intent intent = getIntent();
        Uri filePath = intent.getParcelableExtra("filepath");
        Toast.makeText(this, filePath+"", Toast.LENGTH_SHORT).show();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = (ImageView)findViewById(R.id.imageViewx);
        imageView.setImageBitmap(bitmap);
        Spinner sp_year = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(yearAdapter);
    }
}