package com.israf.makesurvey.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.israf.makesurvey.R;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Selected#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Selected extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static ArrayList<SurveyAnswer> Surveys = new ArrayList<SurveyAnswer>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button SaveButton,DeleteButton;
    private EditText Question,Answer1,Answer2,Answer3,Answer4;
    private SurveyAnswer survey;
    public Selected() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Selected.
     */
    // TODO: Rename and change types and number of parameters
    public static Selected newInstance(String param1, String param2) {
        Selected fragment = new Selected();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V  = inflater.inflate(R.layout.fragment_selected, container, false);
        Question=V.findViewById(R.id.question);
        Answer1=V.findViewById(R.id.Answer1);
        Answer2=V.findViewById(R.id.Answer2);
        Answer3=V.findViewById(R.id.Answer3);
        Answer4=V.findViewById(R.id.Answer4);
        SaveButton=V.findViewById(R.id.SaveButton);
        DeleteButton=V.findViewById(R.id.DeleteButton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                survey=new SurveyAnswer(Question.getText().toString(),Answer1.getText().toString(),Answer2.getText().toString(),Answer3.getText().toString(),Answer4.getText().toString(),0);

                EventBus.getDefault().postSticky(survey);
                SaveButton.setVisibility(View.GONE);


                DeleteButton.setVisibility(View.VISIBLE);
            }
        });//Anket sorusu kayıt edildi
               DeleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
                public void onClick(View v) {



               EventBus.getDefault().postSticky(survey);
               getFragmentManager().beginTransaction().remove(Selected.this).commit();


           }//Anket sorusu silinir
});


        return V;
    }


}