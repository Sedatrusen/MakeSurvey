package com.israf.makesurvey.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.israf.makesurvey.R;

public class MainPage extends AppCompatActivity {
private Button Login,Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Login =findViewById(R.id.Login);
        Register=findViewById(R.id.Register);
           Register.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent register =new Intent(MainPage.this, com.israf.makesurvey.Auth.Register.class);
                   startActivity(register);

               }
           });
           Login.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent login=new Intent(MainPage.this, com.israf.makesurvey.Auth.Login.class);
                   startActivity(login);

               }
           });


            }
}