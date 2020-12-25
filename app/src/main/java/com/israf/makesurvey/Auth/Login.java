package com.israf.makesurvey.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;

public class Login extends AppCompatActivity {
    private Toolbar toolbar;
private EditText Mail,Password;
Button Login;
private FirebaseAuth auth;

public  void init(){
        toolbar= findViewById(R.id.Logintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth= FirebaseAuth.getInstance();
        init();

        Mail = findViewById(R.id.loginUserEmailAddress);
        Password=findViewById(R.id.loginUserPassword);

        Login = findViewById(R.id.Loginbutton);
        Login.setEnabled(false);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
auth.signInWithEmailAndPassword(Mail.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
            Intent makesurvey = new Intent(com.israf.makesurvey.Auth.Login.this, MainActivity.class);
            startActivity(makesurvey);

            finish();

        }else {
            Login.setError(task.getException().getLocalizedMessage());
            Mail.setError(task.getException().getLocalizedMessage());
        }
    }
});
            }
        });

        Mail.addTextChangedListener(loginTextWatcher);
        Password.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String mail = Mail.getText().toString().trim();
            String password = Password.getText().toString().trim();
            if (!mail.contains("@") && !mail.contains(".com")){
                Mail.setError("It must be mail");


            }else if (password.length()<5){
                Password.setError("Password must be 5 or more letter");


            } else{
                Login.setEnabled(true);
            }

        }
    };
}