package com.example.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String spText = sf.getString("uid","");


        edt_id = (EditText) findViewById(R.id.emailInputmain);
        edt_pw = (EditText) findViewById(R.id.passwordInputmain);

        mAuth = FirebaseAuth.getInstance();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);

        if(spText.equals("")){}
        else{
            String id = sf.getString("id","");
            String pw = sf.getString("pw","");

            mAuth.signInWithEmailAndPassword(id,pw)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                String uid = mAuth.getCurrentUser().getUid();
                                text.setText("Login Successful!");
                                //Toast toast = new Toast(getApplicationContext());
                                //toast.setGravity(Gravity.BOTTOM, 0, 0);
                                //toast.setDuration(Toast.LENGTH_SHORT);
                                //toast.setView(layout);
                                //toast.show();
                                Snackbar.make(findViewById(android.R.id.content), "Login Successful!", Snackbar.LENGTH_LONG)
                                        .show();

                                // Activity가 종료되기 전에 저장한다.
                                //SharedPreferences를 sFile이름, 기본모드로 설정
                                SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

                                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("uid",uid); // key, value를 이용하여 저장하는 형태
                                editor.putString("id",id);
                                editor.putString("pw",pw);
                                editor.commit();

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
                    });
        }

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
                                    String uid = mAuth.getCurrentUser().getUid();
                                    text.setText("Login Successful!");
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();

                                    // Activity가 종료되기 전에 저장한다.
                                    //SharedPreferences를 sFile이름, 기본모드로 설정
                                    SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

                                    //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("uid",uid); // key, value를 이용하여 저장하는 형태
                                    editor.putString("id",id);
                                    editor.putString("pw",pw);
                                    editor.commit();

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