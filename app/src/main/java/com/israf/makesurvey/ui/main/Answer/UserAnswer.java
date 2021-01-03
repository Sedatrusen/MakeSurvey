package com.israf.makesurvey.ui.main.Answer;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.Selected;
import com.israf.makesurvey.ui.main.SurveyAdapter;
import com.israf.makesurvey.ui.main.SurveyAnswer;
import com.israf.makesurvey.ui.main.Surveys;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


public class UserAnswer extends Fragment {
private  Surveys Surveythis;
    private  ArrayList<SurveyAnswer> Survey= new ArrayList<SurveyAnswer>();
    private  ArrayList<SurveyAnswer> Answer= new ArrayList<SurveyAnswer>();
    private String SurveyName;
private  String se="aa";
    private int position;
    private  LayoutInflater inflater;
    private  LinearLayout linearLayout;
    private DatabaseReference mDatabase2;

private String result;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_user_answer, container, false);
        view.destroyDrawingCache();
        Button b = view.findViewById(R.id.Answersave);
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        LinearLayout linearLayout = view.findViewById(R.id.my_linear_layout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase2.child("AnswerofSurveys").child(Surveythis.getUserid()).child(SurveyName).child("Answers").child(auth.getCurrentUser().getUid()).setValue(Answer);

         linearLayout.removeAllViews();
                EventBus.getDefault().postSticky(se);
            }
        });




        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported

                linearLayout.removeAllViews();

                String  surveyname = bundle.getString("surveyname");
                String  userid = bundle.getString("id");
                position=bundle.getInt("position");
                Surveythis= new Surveys(surveyname,0,userid);
            DatabaseReference dbRef=  mDatabase.getReference("Surveys").child(Surveythis.getUserid()).child(Surveythis.getName());

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SurveyName= snapshot.getKey();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            for (DataSnapshot ds2 : ds.getChildren()) {


                                SurveyAnswer surveyAnswer = ds2.getValue(SurveyAnswer.class);

                                Survey.add(surveyAnswer);
                            }

                        }

                        dbRef.removeEventListener(this);
for (int i=0;i<Survey.size();i++)
{
    String question=Survey.get(i).getQuestion();
    if  (Survey.get(i).getId()==0 ){
                        View v = inflater.inflate(R.layout.selectedq, linearLayout, false);
                        TextView t = v.findViewById(R.id.selectedQuestion);

    CheckBox a1 = v.findViewById(R.id.surveyanswer1);
    CheckBox a2 = v.findViewById(R.id.surveyanswer2);
    CheckBox a3 = v.findViewById(R.id.surveyanswer3);
    CheckBox a4 = v.findViewById(R.id.surveyanswer4);
if (Survey.get(i).getAnswer1().isEmpty()){
    a1.setVisibility(View.GONE);
}if (Survey.get(i).getAnswer2().isEmpty()){
        a2.setVisibility(View.GONE);
    }if (Survey.get(i).getAnswer3().isEmpty()){
        a3.setVisibility(View.GONE);
    }if (Survey.get(i).getAnswer4().isEmpty()){
        a4.setVisibility(View.GONE);
    }
    a1.setText(Survey.get(i).getAnswer1().toString());
    a2.setText(Survey.get(i).getAnswer2().toString());
    a3.setText(Survey.get(i).getAnswer3().toString());
    a4.setText(Survey.get(i).getAnswer4().toString());

    final String[] Ans1 = {"false"};
    final String[] Ans2 = {"false"};
    final String[] Ans3 = {"false"};
    final String[] Ans4 = {"false"};
    int finalI1 = i;
    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
    Answer.add(0,se);

    a1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

              if(isChecked){

                  Ans1[0] ="true";
            SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
            if (Answer.size()> finalI1){
                Answer.remove(finalI1);}

            Answer.add(finalI1,se);
        } else {
                  Ans1[0] ="false";
            SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
            if (Answer.size()> finalI1){
            Answer.remove(finalI1);}

            Answer.add(finalI1,se);}}
    }); a2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                Ans2[0] ="true";
                SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                if (Answer.size()> finalI1){
                    Answer.remove(finalI1);}

                Answer.add(finalI1,se);
            } else {
                 Ans2[0] ="false";
                SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                if (Answer.size()> finalI1){
                    Answer.remove(finalI1);}

                Answer.add(finalI1,se);}}
    }); a3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                Ans3[0] ="true";
                SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                if (Answer.size()> finalI1){
                    Answer.remove(finalI1);}

                Answer.add(finalI1,se);
            } else {
                Ans3[0] ="false";
                SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                if (Answer.size()> finalI1){
                    Answer.remove(finalI1);}

                Answer.add(finalI1,se);}}
    });; a4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                Ans4[0] ="true";
                SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                if (Answer.size()> finalI1){
                    Answer.remove(finalI1);}

                Answer.add(finalI1,se);
            } else {
                Ans4[0] ="false";
                SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                if (Answer.size()> finalI1){
                    Answer.remove(finalI1);}

                Answer.add(finalI1,se);}}
    });


                        t.setText(Survey.get(i).getQuestion());   linearLayout.addView(v);



                    }
    if  (  Survey.get(i).getId()==2){
        View v = inflater.inflate(R.layout.selectedq, linearLayout, false);
        TextView t = v.findViewById(R.id.selectedQuestion);

        CheckBox a1 = v.findViewById(R.id.surveyanswer1);
        CheckBox a2 = v.findViewById(R.id.surveyanswer2);
        CheckBox a3 = v.findViewById(R.id.surveyanswer3);
        CheckBox a4 = v.findViewById(R.id.surveyanswer4);
        if (Survey.get(i).getAnswer1().isEmpty()){
            a1.setVisibility(View.GONE);
        }if (Survey.get(i).getAnswer2().isEmpty()){
            a2.setVisibility(View.GONE);
        }if (Survey.get(i).getAnswer3().isEmpty()){
            a3.setVisibility(View.GONE);
        }if (Survey.get(i).getAnswer4().isEmpty()){
            a4.setVisibility(View.GONE);
        }
        a1.setText(Survey.get(i).getAnswer1().toString());
        a2.setText(Survey.get(i).getAnswer2().toString());
        a3.setText(Survey.get(i).getAnswer3().toString());
        a4.setText(Survey.get(i).getAnswer4().toString());

        final String[] Ans1 = {"false"};
        final String[] Ans2 = {"false"};
        final String[] Ans3 = {"false"};
        final String[] Ans4 = {"false"};
        int finalI1 = i;
        SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
        Answer.add(0,se);

        a1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    a2.setClickable(false);
                    a3.setClickable(false);
                    a4.setClickable(false);

                    Ans1[0] ="true";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);
                } else {
                    a2.setClickable(true);
                    a3.setClickable(true);
                    a4.setClickable(true);
                    Ans1[0] ="false";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);}}
        }); a2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Ans2[0] ="true";
                    a1.setClickable(false);
                    a3.setClickable(false);
                    a4.setClickable(false);

                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);
                } else {
                    a1.setClickable(true);
                    a3.setClickable(true);
                    a4.setClickable(true);
                    Ans2[0] ="false";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);}}
        }); a3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    a1.setClickable(false);
                    a2.setClickable(false);
                    a4.setClickable(false);
                    Ans3[0] ="true";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);
                } else {
                    a1.setClickable(true);
                    a2.setClickable(true);
                    a4.setClickable(true);
                    Ans3[0] ="false";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);}}
        });; a4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    a1.setClickable(false);
                    a2.setClickable(false);
                    a3.setClickable(false);
                    Ans4[0] ="true";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);
                } else {
                    a1.setClickable(true);
                    a2.setClickable(true);
                    a3.setClickable(true);
                    Ans4[0] ="false";
                    SurveyAnswer   se =new SurveyAnswer(question, Ans1[0], Ans2[0], Ans3[0], Ans4[0],0);
                    if (Answer.size()> finalI1){
                        Answer.remove(finalI1);}

                    Answer.add(finalI1,se);}}
        });


        t.setText(Survey.get(i).getQuestion());   linearLayout.addView(v);



    }
    if  (Survey.get(i).getId()==1){
        View v = inflater.inflate(R.layout.classicq, linearLayout, false);
        TextView t = v.findViewById(R.id.classicQuestion);
        EditText e = v.findViewById(R.id.classicAnswer);


        int finalI = i;
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String a= e.getText().toString();
                     SurveyAnswer   se =new SurveyAnswer(question,a,null,null,null,1);
                if (Answer.size()>finalI){
                Answer.remove(finalI);}

                Answer.add(finalI,se);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t.setText(Survey.get(i).getQuestion());   linearLayout.addView(v);



    }
} Survey= new ArrayList<SurveyAnswer>();}

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // Do something with the result







            }
        });

  return view;


    }


}