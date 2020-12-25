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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.israf.makesurvey.R;

public class Register extends AppCompatActivity {
    private Toolbar toolbar;
private EditText Mail,Password;
private Button register;
private FirebaseAuth auth;
    public  void init(){
        toolbar= findViewById(R.id.Registertoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        auth= FirebaseAuth.getInstance();
        Mail = findViewById(R.id.registerUserEmailAddress);
        Password=findViewById(R.id.registerUserPassword);
        register=findViewById(R.id.registerbutton);
register.setEnabled(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.createUserWithEmailAndPassword(Mail.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent login = new Intent(Register.this,Login.class);
                            startActivity(login);
                            finish();
                        }else {
                            register.setError("Fail");
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
            String mail = Mail.getText().toString().trim();
            String password = Password.getText().toString().trim();
            register.setEnabled(!mail.isEmpty() && !password.isEmpty());

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
                register.setEnabled(true);
            }

        }
    };
}
        /* Intent intent = new Intent(getContext(),Anket.class);
         intent.putExtra("name",Survey.get(position).getName());
         intent.putExtra("id",Survey.get(position).getUserid());
         startActivity(intent);*/
