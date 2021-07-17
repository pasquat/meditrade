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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import static java.security.AccessController.getContext;

public class addSpecifics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specifics);
        Intent intent = getIntent();
        Uri filePath = intent.getParcelableExtra("filepath");
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
        TextView wordcounter = (TextView)findViewById(R.id.wordcounter);
        EditText editTextDesc = (EditText) findViewById(R.id.editTextDesc);
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
                //String currentText = editable.toString();
                //int currentLength = currentText.length();
                //wordcounter.setText(currentLength + "/100");
            }
        });

    }

}