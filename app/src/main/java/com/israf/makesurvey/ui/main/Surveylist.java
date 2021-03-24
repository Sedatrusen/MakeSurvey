package com.israf.makesurvey.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.israf.makesurvey.R;

import java.util.ArrayList;


public class Surveylist extends Fragment {
private ArrayList<Surveys> Survey=new ArrayList<Surveys>();
private ListView listView;
private FirebaseAuth auth = FirebaseAuth.getInstance();
private FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v =inflater.inflate(R.layout.fragment_surveylist, container, false);
       listView=v.findViewById(R.id.SurveyListView);
       final DatabaseReference dbRef=mDatabase.getReference("Surveys").child(auth.getCurrentUser().getUid());
        init( dbRef);
        listView.setEmptyView(v.findViewById(R.id.surveyemptyfield));
return v;
    }
    private void init(DatabaseReference dbRef) {

        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Survey=new ArrayList<Surveys>();
                SurveyAdapter adapter= new SurveyAdapter(getActivity(),Survey);
                String userid=snapshot.getKey();
                for (DataSnapshot ds:snapshot.getChildren()){
                    String SurveyName= ds.getKey();
                   int NumberOfQuestions= (int) ds.child("Questions").getChildrenCount();
                    Survey.add(new Surveys(SurveyName,NumberOfQuestions,userid));
                   adapter.notifyDataSetChanged();
                }

                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

}