package com.israf.makesurvey.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;

import java.util.ArrayList;

public class SurveyAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Surveys> s;
    private  SurveyDetails details;
    Context c;
ArrayList<String> link =new ArrayList<String>();
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    public SurveyAdapter(Activity activity, ArrayList<Surveys> s) {
        c=activity;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.s = s;
    }

    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int position) {
        return s.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Surveys surveys=s.get(position);
       View satir =layoutInflater.inflate(R.layout.survey,null);
        TextView isim = satir.findViewById(R.id.surveyname);
        TextView number= satir.findViewById(R.id.number);
        Button   delete = satir.findViewById(R.id.listdelete);
        Button result = satir.findViewById(R.id.ResultButton);
        Button publish = satir.findViewById(R.id.listpublishlink);
        TextView time = satir.findViewById(R.id.Surveycreatedtimeforcreator);


        DatabaseReference dbRef=  mDatabase.child("AnswerofSurveys").child(user.getUid()).child(surveys.getName());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.getChildrenCount()==0){
                   result.setEnabled(false);
               }else {
                   result.setEnabled(true);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference dbRef2=  mDatabase.child("SurveyLink").child(user.getUid()).child(surveys.getName());
        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()==0){
                    publish.setEnabled(false);

                }else {
                    publish.setEnabled(true);

                }

                for (DataSnapshot ds :snapshot.getChildren()){
                    String a=ds.getValue(String.class);
                   link.add(a);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        isim.setText(surveys.getName());

        DatabaseReference description =mDatabase.child("SurveysDetails").child(user.getUid()).child(surveys.getName());
        description.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){

                       details=ds.getValue(SurveyDetails.class);

                }
number.setText(details.getSurveyDescription());
time.setText(details.getCreatedDate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        number.setText("Questions number: "+surveys.getQuestionnumber());


        result.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {  Bundle result = new Bundle();
                result.putString("SurveyName",surveys.getName());
                result.putInt("QuestionNumber",surveys.getQuestionnumber());

        Intent intent =new  Intent(satir.getContext(),Result.class);
        intent.putExtras(result);

             v.getContext().startActivity(intent);
             v.dispatchFinishTemporaryDetach();


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Surveys").child(surveys.getUserid()).child(surveys.getName()).removeValue();
                mDatabase.child("SurveyLink").child(surveys.getName()).removeValue();
               delete.setEnabled(false);
               delete.setText("Deleted");
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(v.getContext(),link.get(1));
                Toast.makeText(v.getContext(), "Copied the link", Toast.LENGTH_LONG).show();
            }
        });


        return satir;
    }
    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
