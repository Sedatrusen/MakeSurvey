package com.israf.makesurvey.ui.main;

import android.content.Context;
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
 * Use the {@link Classic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Classic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText Question;
    private static ArrayList<SurveyAnswer> Surveys = new ArrayList<SurveyAnswer>();
    private SurveyAnswer survey;
    private Button SaveButton,DeleteButton;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Classic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Classic.
     */
    // TODO: Rename and change types and number of parameters
    public static Classic newInstance(String param1, String param2) {
        Classic fragment = new Classic();
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
        View V  = inflater.inflate(R.layout.fragment_classic, container, false);
        Question=V.findViewById(R.id.classicquestion);
         SaveButton=V.findViewById(R.id.SaveButton);
        DeleteButton=V.findViewById(R.id.DeleteButton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                survey=new SurveyAnswer(Question.getText().toString(),1);
                EventBus.getDefault().postSticky(survey);

                SaveButton.setVisibility(View.GONE);


                DeleteButton.setVisibility(View.VISIBLE);
            }
        });//Anket sorusu kayıt edildi
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EventBus.getDefault().postSticky(survey);

                getFragmentManager().beginTransaction().remove(Classic.this).commit();


            }//Anket sorusu silinir
        });


        return V;
    }

}