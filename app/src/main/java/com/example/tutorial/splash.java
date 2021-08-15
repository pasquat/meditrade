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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private FirebaseAuth mAuth;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String spText = sf.getString("uid","");

        mAuth = FirebaseAuth.getInstance();

        if(spText.equals("")){ Intent intent = new Intent(splash.this, MainActivity.class) ;
            startActivity(intent) ;}

        else{
            String id = sf.getString("id","");
            String pw = sf.getString("pw","");

            mAuth.signInWithEmailAndPassword(id,pw)
                    .addOnCompleteListener(splash.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(splash.this, NewActivity.class) ;
                                startActivity(intent) ;
                            } else {
                                Intent intent = new Intent(splash.this, MainActivity.class) ;
                                startActivity(intent) ;
                            }
                        }
                    });
        }
    }
}