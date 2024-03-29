package com.israf.makesurvey.ui.main.Answer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.israf.makesurvey.Auth.User;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.AnswerAdapter;
import com.israf.makesurvey.ui.main.SurveyAnswer;
import com.israf.makesurvey.ui.main.SurveyDetails;
import com.israf.makesurvey.ui.main.Surveys;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;


public class ForAnswerSurvey extends Fragment {
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
        View v =inflater.inflate(R.layout.fragment_for_answer_survey, container, false);
        listView=v.findViewById(R.id.AnswerListView);
        final DatabaseReference dbRef=mDatabase.getReference("Surveys");
        init( dbRef);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {

                Surveys s=Survey.get(position);
                Bundle result = new Bundle();
                result.putString("surveyname", Survey.get(position).getName());
                result.putString("id",Survey.get(position).getUserid());
                result.getInt("position",position);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                EventBus.getDefault().postSticky(s);


            }});
        listView.setEmptyView(v.findViewById(R.id.Answersurveyemptyfield));
        return v;

    }
    private void init(DatabaseReference dbRef) {
        AnswerAdapter adapter= new AnswerAdapter(getActivity(),Survey);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds2 : snapshot.getChildren()) {
            String userid = ds2.getKey();
            if (!userid.equals(auth.getCurrentUser().getUid())) {
                for (DataSnapshot ds : ds2.getChildren()) {
                    String SurveyName = ds.getKey();
                    int NumberOfQuestions = (int) ds.child("Questions").getChildrenCount();

                 DatabaseReference userinforef=mDatabase.getReference("usersinfo").child(auth.getCurrentUser().getUid());
                 userinforef.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         User userinfo = snapshot.getValue(User.class);

                         DatabaseReference surveydetailsref=mDatabase.getReference("SurveysDetails").child(userid).child(SurveyName).child("Details");
                         surveydetailsref.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 SurveyDetails surveyDetails=snapshot.getValue(SurveyDetails.class);
                                 Calendar cal = Calendar.getInstance();
                                 int year = cal.get(Calendar.YEAR);
                                int currentuserage = year-userinfo.getBirthyear();
                                if (surveyDetails.getTargetages()==0) {

                                    Survey.add(new Surveys(SurveyName, NumberOfQuestions, userid));
                                    adapter.notifyDataSetChanged();
                                }else{
                                    if (surveyDetails.getTargetages()<=currentuserage &&currentuserage<=surveyDetails.getTargetages()+9){

                                        Survey.add(new Surveys(SurveyName, NumberOfQuestions, userid));
                                         adapter.notifyDataSetChanged();

                                    }
                                }

                           }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });
                   }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });

                }
            }
        }


                dbRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        listView.setAdapter(adapter);


    }}
