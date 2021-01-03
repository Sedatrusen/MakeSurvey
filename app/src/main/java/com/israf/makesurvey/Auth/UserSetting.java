package com.israf.makesurvey.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.israf.makesurvey.R;

import java.util.Objects;
import java.util.jar.Attributes;

public class UserSetting extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbar;
    private Button NameSave,EmailSave,PasswordSave;

    private TextInputEditText  EmailEdit,NameEdit,OldPasswordEdit,NewPasswordEdit;
   private TextInputLayout NameLayout,EmailLayout,OldPasswordLayout,NewPasswordLayout;
    public  void init(){

        toolbar= findViewById(R.id.usersettingtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        init();

        NameEdit();
        EmailEdit();
        PasswordEdit();


    }


private  void NameEdit(){
        NameEdit=findViewById(R.id.NameEdit);
        NameLayout=findViewById(R.id.NameLayout);
    NameSave=findViewById(R.id.NameSave);
        NameEdit.setText(user.getDisplayName());
    NameSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Namechange();
        }
    });

}
    private  void Namechange(){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(NameEdit.getText().toString())
               .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            NameEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);
                        }else {
                            NameLayout.setError(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

private  void EmailEdit(){
        EmailEdit=findViewById(R.id.EmailEdit);
        EmailSave=findViewById(R.id.EmailSave);
        EmailLayout=findViewById(R.id.EmailLayout);
        EmailEdit.setText(user.getEmail());

        EmailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateEmail(EmailEdit.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    EmailLayout.setError(null);
                                   EmailEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);
                                }else{
                                    EmailLayout.setError(task.getException().getLocalizedMessage());

                                }
                            }
                        });
            }
        });















}
private  void PasswordEdit(){
        OldPasswordEdit=findViewById(R.id.OldEditPassword);
        OldPasswordLayout=findViewById(R.id.OldPasswordLayout);
        NewPasswordEdit=findViewById(R.id.NewEditPassword);
        NewPasswordLayout=findViewById(R.id.NewPasswordLayout);
        PasswordSave=findViewById(R.id.PasswordSave);

      PasswordSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (OldPasswordEdit.getText().toString().trim().length()==0 ) {
                  OldPasswordLayout.setError("Can not be Empty");
            }else if( NewPasswordEdit.getText().toString().trim().length()==0){
                  NewPasswordLayout.setError("Can not be Empty");
              }
              else {
                  AuthCredential credential = EmailAuthProvider
                          .getCredential(Objects.requireNonNull(user.getEmail()), OldPasswordEdit.getText().toString());
                  user.reauthenticate(credential)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {

                                  if (task.isSuccessful()) {
                                      OldPasswordLayout.setError(null);
                                      OldPasswordEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);
                                      updateUserEmail();
                                  } else {
                                      // Password is incorrect
                                      OldPasswordLayout.setError(task.getException().getLocalizedMessage());
                                  }
                              }
                          });
              }
          }
      });




}
private void updateUserEmail(){
    user.updatePassword(NewPasswordEdit.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        NewPasswordLayout.setError(null);
                        NewPasswordEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);
                    }else {
                        NewPasswordLayout.setError(task.getException().getLocalizedMessage());
                    }
                }
            });


}



}


