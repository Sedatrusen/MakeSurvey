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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.israf.makesurvey.R;

public class Register extends AppCompatActivity {
    private Toolbar toolbar;
private EditText Mail,Password;
private TextInputLayout MailLayout,PasswordLayout;
private Button register;
private FirebaseAuth auth;
boolean MailCheck=false,Passwordcheck=false;
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
        Mail = findViewById(R.id.RegisterUserEmailAddress);
        MailLayout=findViewById(R.id.RegisterMailLayout);
        PasswordLayout=findViewById(R.id.RegisterPasswordLayout);
        Password=findViewById(R.id.RegisterUserPassword);
        register=findViewById(R.id.registerbutton);
register.setEnabled(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.createUserWithEmailAndPassword(Mail.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            MailLayout.setError(null);
                            Intent login = new Intent(Register.this,Login.class);
                            startActivity(login);
                            finish();
                        }else {
                            register.setError("Fail");
                            MailLayout.setError(task.getException().getLocalizedMessage());
                        }
                    }
                });

            }
        });
        Mail.addTextChangedListener(MailTextWatcher);
        Password.addTextChangedListener(PasswordTextWatcher);

    }
    private TextWatcher MailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String mail = Mail.getText().toString().trim();

            if (!mail.contains("@") && !mail.contains(".com")){
                MailLayout.setError("It must be mail");
                MailCheck=false;

            }else {
                MailLayout.setError(null);
                MailCheck=true;
            }

            if (MailCheck&&Passwordcheck){
                register.setEnabled(true);
            }else{
                register.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher PasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {

            String password = Password.getText().toString().trim();
            if (password.length()<5){
                PasswordLayout.setError("Password must be 5 or more letter");
                Passwordcheck=false;

            } else{
                PasswordLayout.setError(null);
                Passwordcheck=true;
            }
            if (MailCheck&&Passwordcheck){
                register.setEnabled(true);
            }else{
                register.setEnabled(false);
            }
        }
    };
}
        /* Intent intent = new Intent(getContext(),Anket.class);
         intent.putExtra("name",Survey.get(position).getName());
         intent.putExtra("id",Survey.get(position).getUserid());
         startActivity(intent);*/
