package com.israf.makesurvey.ui.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.israf.makesurvey.R;

import java.util.ArrayList;

public class AnswerAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Surveys> s;
private SurveyDetails details;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    public AnswerAdapter(Activity activity, ArrayList<Surveys> s) {
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
        View satir =layoutInflater.inflate(R.layout.survey2,null);
        TextView isim = satir.findViewById(R.id.surveyname2);
        TextView number= satir.findViewById(R.id.number2);
        TextView time =satir.findViewById(R.id.dateforanswers);

        DatabaseReference deteails = mDatabase.child("SurveysDetails").child(surveys.getUserid()).child(surveys.getName());
        deteails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){

                    details=ds.getValue(SurveyDetails.class);

                }
                assert details != null;
                number.setText(details.getSurveyDescription());
                time.setText(details.getCreatedDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        isim.setText(surveys.getName());

        number.setText("Questions number: "+surveys.getQuestionnumber());

        return satir;
    }
}
