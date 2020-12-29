package com.israf.makesurvey.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragAndDropPermissions;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.israf.makesurvey.Auth.MainPage;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private  String SurveyName;
    private int QuestionNumber;
    private  ArrayList<SurveyAnswer> Surveys= new ArrayList<SurveyAnswer>();
    private  ArrayList<SurveyAnswer> Answers= new ArrayList<SurveyAnswer>();
    private FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();
    private  ArrayList<AnswerString> cevaplar = new ArrayList<AnswerString>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        LinearLayout layout = (LinearLayout)findViewById(R.id.frameLayoutForFragments);

        SurveyName=bundle.getString("SurveyName");
        QuestionNumber=bundle.getInt("QuestionNumber");
        init();
        DatabaseReference dbRef=  mDatabase.getReference("AnswerofSurveys").child(auth.getCurrentUser().getUid()).child(SurveyName);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){


                    for (DataSnapshot ds2 :ds.getChildren()){
                        for (DataSnapshot ds3:ds2.getChildren()){

                            SurveyAnswer surveyAnswer = ds3.getValue(SurveyAnswer.class);
                            Answers.add(surveyAnswer);
                        }

                    }


                }

                for (int a = 0; a <QuestionNumber; a++){
                    cevaplar.add(new AnswerString(Answers.get(a).getQuestion()));
                    for ( int i= a;i<Answers.size();i=i+QuestionNumber){
                            if (Answers.get(i).getId()==1){
                                cevaplar.get(a).setSorutipi("other");
                                cevaplar.get(a).setanswer(Answers.get(i).getAnswer1());
                            }else {
                                cevaplar.get(a).setSorutipi("select");

                                if (Answers.get(i).getAnswer1().equals("true")){
                                    cevaplar.get(a).Ans1();
                                }
                                if (Answers.get(i).getAnswer2().equals("true")){
                                    cevaplar.get(a).Ans2();
                                }if (Answers.get(i).getAnswer3().equals("true")){
                                    cevaplar.get(a).Ans3();
                                }if (Answers.get(i).getAnswer4().equals("true")){
                                    cevaplar.get(a).Ans4();
                                }

                            }


                    }
                }

for (int a=0; a<QuestionNumber;a++){
    if (cevaplar.get(a).getSorutipi()=="other"){


          ClassicAnswer c = new ClassicAnswer();
            c.sendAnswerString(cevaplar.get(a));

        LinearLayout frame = new LinearLayout(getApplicationContext());
        frame.setId(a+1);
        layout.addView(frame);
        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(a+1,c);

        fragmentTransaction.commit();

    }
    else{
        DatabaseReference dbRef=  mDatabase.getReference("Surveys").child(user.getUid()).child(SurveyName);
        int finalA = a;

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds :snapshot.getChildren() ){
                    for (DataSnapshot ds2 : ds.getChildren()) {


                        SurveyAnswer surveyAnswer = ds2.getValue(SurveyAnswer.class);

                        Surveys.add(surveyAnswer);
                    }
                }

                SelectedAnswer c = new SelectedAnswer();
                c.sendAnswerString(cevaplar.get(finalA));
                c.Survey(Surveys.get(finalA));

                LinearLayout frame = new LinearLayout(getApplicationContext());
                frame.setId(finalA +1);
                layout.addView(frame);
                FragmentManager fragmentManager =  getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.add(finalA +1,c);

                fragmentTransaction.commit();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
    }
}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Selected c = new Selected();



}



    public  void init(){
        toolbar= findViewById(R.id.Resulttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(SurveyName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain,menu);
        super.onCreateOptionsMenu(menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.mainlogout)
            auth.signOut();
        Intent firstpage = new Intent(Result.this, MainPage.class);
        startActivity(firstpage);
        finish();


        return true;
    }
}