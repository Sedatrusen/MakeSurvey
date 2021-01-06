package com.israf.makesurvey.ui.main;

import android.app.UiAutomation;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.israf.makesurvey.R;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Selected#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Multiple extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static ArrayList<SurveyAnswer> Surveys = new ArrayList<SurveyAnswer>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button DeleteButton;
    private EditText Question,Answer1,Answer2,Answer3,Answer4;
    private SurveyAnswer survey;
    public Multiple() {
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
        View V  = inflater.inflate(R.layout.fragment_multiple, container, false);
        Question=V.findViewById(R.id.question);
        Answer1=V.findViewById(R.id.Answer1);
        Answer2=V.findViewById(R.id.Answer2);
        Answer3=V.findViewById(R.id.Answer3);
        Answer4=V.findViewById(R.id.Answer4);

        DeleteButton=V.findViewById(R.id.DeleteButton);



        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                getFragmentManager().beginTransaction().remove(Multiple.this).commit();


            }//Anket sorusu silinir
        });


        return V;
    }

    @Subscribe
    public void onEvent(Integer s) {
        survey=new SurveyAnswer(Question.getText().toString(),Answer1.getText().toString(),Answer2.getText().toString(),Answer3.getText().toString(),Answer4.getText().toString(),0);
        EventBus.getDefault().post(survey);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

}