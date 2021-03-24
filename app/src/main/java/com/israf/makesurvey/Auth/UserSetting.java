package com.israf.makesurvey.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.israf.makesurvey.Auth.img.GlideApp;
import com.israf.makesurvey.Auth.img.ImagePickerActivity;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.SurveyAnswer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;

import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class UserSetting extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbar;
    private Button Save,Next;
    private static int PICK_IMAGE = 123;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private  User Currentuser;
    private FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ImageView profileImageView;

    private int Year,Day,Mounth,Info;
    private TextInputEditText  EmailEdit,NameEdit,OldPasswordEdit,NewPasswordEdit,countrytext,BirthdayText,PhoneNumberEdit ;
   private TextInputLayout NameLayout,EmailLayout,OldPasswordLayout,NewPasswordLayout,BirthdayLayout,country,PhoneNumberLayout;
    public static final int REQUEST_IMAGE = 100;
    DatabaseReference dbRef=  mDatabase.getReference("usersinfo").child(user.getUid());
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
        Bundle bundle = getIntent().getExtras();
        Info=bundle.getInt("info");
        Next= findViewById(R.id.Next); Save = findViewById(R.id.PasswordSave);
        if (Info==0){
            Next.setVisibility(View.VISIBLE);
            Save.setVisibility(View.GONE);
        }

        Save.setEnabled(false);




dbRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

if (snapshot.getValue()!=null) {
    Currentuser = snapshot.getValue(User.class);


}else{
    Currentuser= new User();
}
        BirthdayEdit();
        PhoneNumberEdit();
        CountrEdit();
        NameEdit();
        EmailEdit();
        PasswordEdit();
        ProfilPic();

        dbRef.removeEventListener(this);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

});
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!user.getDisplayName().equals(NameEdit.getText().toString())){
                Namechange();}
               if (!user.getEmail().equals(EmailEdit.getText().toString())){
                EmailEditSave();}


                if (OldPasswordEdit.getText().toString().trim().length() > 0) {
                    PasswordSave();
                }

               if (Currentuser.getBirthyear()!=Year ){
               Birthdaysave();}
               if(!Currentuser.getPhoneNumber().equals(PhoneNumberEdit.getText().toString())){
                   PhoneNumberSave();
               }
               if (!Currentuser.getCountry().equals(countrytext.getText().toString())){
                   CountrySave();
               }

            }
        });
Next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    

            Namechange();


            Birthdaysave();

            PhoneNumberSave();


            CountrySave();

        Intent login = new Intent(UserSetting.this, MainActivity.class);

        startActivity(login);

    }
});

    }

    private void  PhoneNumberSave(){
        Currentuser.setPhoneNumber(PhoneNumberEdit.getText().toString());
        dbRef.setValue(Currentuser);
        PhoneNumberEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);

    }
    private void PhoneNumberEdit(){
        PhoneNumberEdit=findViewById(R.id.PhoneText);

        PhoneNumberEdit.setText(Currentuser.getPhoneNumber());
    PhoneNumberEdit.addTextChangedListener(SaveTextWatcher);
    }
private void CountrySave(){

        Currentuser.setCountry(countrytext.getText().toString());
        dbRef.setValue(Currentuser);
    countrytext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);
}

    private  void  CountrEdit(){

       country = findViewById(R.id.CountryLayout);

         countrytext = findViewById(R.id.CountryText);
        countrytext.setKeyListener(null);
if (Currentuser!=null) {
    countrytext.setText(Currentuser.getCountry());
}
        countrytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                        countrytext.setText(name);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

            }
        });
countrytext.addTextChangedListener(SaveTextWatcher);
    }

private  void Birthdaysave(){
    Currentuser.setBirthday(Day);
    Currentuser.setBirthmonth(Mounth);
    Currentuser.setBirthyear(Year);
    dbRef.setValue(Currentuser).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            BirthdayText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_done_24, 0);
        }
    });


}
    private  void BirthdayEdit(){

      BirthdayText=findViewById(R.id.BirthdayText);
        BirthdayText.setKeyListener(null);
if (Currentuser!=null) {
    String date = Currentuser.getBirthday() + "/" + Currentuser.getBirthmonth() + "/" + Currentuser.getBirthyear();
    BirthdayText.setText(date);
}
        BirthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year ;
                int month ;
                int day ;
                if (Currentuser.getBirthyear()!=0){
                    year = Currentuser.getBirthyear();
                    month = Currentuser.getBirthmonth();
                    day = Currentuser.getBirthday();
                }else{
                    Calendar cal = Calendar.getInstance();
                   year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                     day = cal.get(Calendar.DAY_OF_MONTH);
                }



                DatePickerDialog dialog = new DatePickerDialog(
                        UserSetting.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                Year=year;
                Day=day;
                Mounth=month;
                String date = day + "/" + month+ "/" + year;
                BirthdayText.setText(date);
            }
        };
        BirthdayText.addTextChangedListener(SaveTextWatcher);

    }

private void ProfilPic(){
    profileImageView = findViewById(R.id.update_imageView);
    Bitmap bitmap = null;
   if (!(user.getPhotoUrl() ==null)) {
       Picasso.get().load(user.getPhotoUrl()).fit().centerInside().into(profileImageView);
   }

    AlertDialog.Builder builder = new AlertDialog.Builder(this);


    builder.setMessage(R.string.are_u_sure)
            .setTitle(R.string.Update_Profil_Picture);
    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // User clicked OK button
            onProfileImageClick();
        }
    });
    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
            dialog.dismiss();
        }
    });

    AlertDialog dialog = builder.create();
    profileImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.show();


        }
    });

}



private  void NameEdit(){
        NameEdit=findViewById(R.id.NameEdit);
        NameLayout=findViewById(R.id.NameLayout);

        NameEdit.setText(user.getDisplayName());
        NameEdit.addTextChangedListener(SaveTextWatcher);

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

        EmailLayout=findViewById(R.id.EmailLayout);
        EmailEdit.setText(user.getEmail());

         EmailEdit.addTextChangedListener(SaveTextWatcher);
    if (Info==0){
        EmailLayout.setVisibility(View.GONE);

    }

}
private void EmailEditSave(){
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

private  void PasswordEdit(){
        OldPasswordEdit=findViewById(R.id.OldEditPassword);
        OldPasswordLayout=findViewById(R.id.OldPasswordLayout);
        NewPasswordEdit=findViewById(R.id.NewEditPassword);
        NewPasswordLayout=findViewById(R.id.NewPasswordLayout);
if (Info==0){
    OldPasswordLayout.setVisibility(View.GONE);
    NewPasswordLayout.setVisibility(View.GONE);
}
        OldPasswordEdit.addTextChangedListener(SaveTextWatcher);
}
private  void  PasswordSave(){
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
                            updateUserPassword();
                        } else {
                            // Password is incorrect
                            OldPasswordLayout.setError(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

}
private void updateUserPassword(){
    user.updatePassword(NewPasswordEdit.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        NewPasswordLayout.setError(null);
                        OldPasswordLayout.setEndIconDrawable(R.drawable.ic_baseline_done_24);
                        NewPasswordLayout.setEndIconDrawable(R.drawable.ic_baseline_done_24);
                    }else {
                        NewPasswordLayout.setError(task.getException().getLocalizedMessage());
                    }
                }
            });


}

    private TextWatcher SaveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Save.setEnabled(true);

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Save.setEnabled(true);


        }

        @Override
        public void afterTextChanged(Editable s) {
Save.setEnabled(true);
            BirthdayText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
           OldPasswordEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
           NameEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            countrytext.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            PhoneNumberEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            NewPasswordEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            EmailEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    };

    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(UserSetting.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(UserSetting.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");

                    // You can update this bitmap to your server

                    StorageReference imageReference = storage.getReference().child(user.getUid()).child("Images").child("Profile Pic"); //User id/Images/Profile Pic.jpg
                    UploadTask uploadTask = imageReference.putFile(uri);
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return imageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(user.getDisplayName())
                                        .setPhotoUri(downloadUri)
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });

                    // loading profile image from local cache
                    loadProfile(uri.toString());

            }
        }
    }


    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        GlideApp.with(this).load(url)
                .into(profileImageView);
        profileImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }
    private void loadProfileDefault() {
        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
                .into(profileImageView);
        profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSetting.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}




