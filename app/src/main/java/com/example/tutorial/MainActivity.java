package com.example.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edt_id,edt_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_id = (EditText) findViewById(R.id.emailInputmain);
        edt_pw = (EditText) findViewById(R.id.passwordInputmain);

        mAuth = FirebaseAuth.getInstance();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);

        Button button1 = (Button) findViewById(R.id.loginmain) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = edt_id.getText().toString().trim();
                final String pw = edt_pw.getText().toString().trim();
                if(id.equals("")||pw.equals("")){
                    text.setText("Username or Password is incorrect.");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0, 50);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }else{mAuth.signInWithEmailAndPassword(id,pw)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    text.setText("Login Successful!");
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    Intent intent = new Intent(MainActivity.this, NewActivity.class) ;
                                    startActivity(intent) ;
                                } else {
                                    // If sign in fails, display a message to the user.

                                    text.setText("Username or Password is incorrect.");
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                }
                            }
                        });}
            }
        });

        Button button2 = (Button) findViewById(R.id.signupMain) ;
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class) ;

                startActivity(intent) ;
            }
        });

    }
}