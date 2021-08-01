package com.example.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private FirebaseDatabase database;

    private FirebaseAuth mAuth;
    EditText edt_id,edt_pw,edt_pwc,editTextPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();

        edt_id = (EditText) findViewById(R.id.emailInput);
        edt_pw = (EditText) findViewById(R.id.passwordInput);
        edt_pwc = (EditText) findViewById(R.id.passwordInput2);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        CountryCodePicker ccp;
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        Typeface typeFace=Typeface.createFromAsset(getApplicationContext().getAssets(),"Lato-Bold.ttf");
        ccp.setTypeFace(typeFace);

        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button button2 = (Button) findViewById(R.id.button2) ;
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = edt_id.getText().toString().trim();
                final String pw = edt_pw.getText().toString().trim();
                final String pwc = edt_pwc.getText().toString().trim();

                if(id.equals("")||pw.equals("")){
                    Toast.makeText(signup.this, "Please put in your Email and Password.", Toast.LENGTH_SHORT).show();
                }
                else if(pw.equals(pwc)){
                    mAuth.createUserWithEmailAndPassword(id,pw)
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(), "Sign Up Successful!",
                                                Toast.LENGTH_SHORT).show();

                                        String phoneString = editTextPhone.getText().toString();
                                        String countryString = ccp.getSelectedCountryName();
                                        String uidString = mAuth.getUid();

                                        firebase_add(id,phoneString,countryString,uidString);
                                        Intent intent = new Intent(signup.this, MainActivity.class) ;
                                        startActivity(intent) ;
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(getApplicationContext(), "Please check your credentials.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else if(!pw.equals(pwc)){
                    Toast.makeText(getApplicationContext(), "Please confirm your password.", Toast.LENGTH_SHORT).show();
                    edt_pw.setText("");
                    edt_pwc.setText("");
                }

            }
        });

    }

    public void firebase_add(String idString,String phoneString,String countryString,String uidString){
        database = FirebaseDatabase.getInstance();
        DatabaseReference query = database.getReference("info");
        String key = query.push().getKey();

        Map<String, Object> map = new HashMap<>();
        map.put("id", idString);
        map.put("pn", phoneString);
        map.put("country", countryString);
        map.put("uid", uidString);

        query.child(key).setValue(map);
    }

}