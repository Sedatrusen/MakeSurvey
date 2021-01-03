package com.israf.makesurvey.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassicAnswer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassicAnswer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private  AnswerString s;
    public ClassicAnswer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassicAnswer.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassicAnswer newInstance(String param1, String param2) {
        ClassicAnswer fragment = new ClassicAnswer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void sendAnswerString(AnswerString s) {
        this.s=s;

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
       View v= inflater.inflate(R.layout.fragment_classic_answer, container, false);
        TextView t=v.findViewById(R.id.classicAnswerQuestion);
        t.setText(s.soru);
        ListView listView = v.findViewById(R.id.clasiclistview);
       ClassicAnwserAdapter adapter= new ClassicAnwserAdapter(getActivity(),s.answer);
        listView.setAdapter(adapter);




        return v;
    }
}
 class ClassicAnwserAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<String> s;


    public ClassicAnwserAdapter(Activity activity, ArrayList<String> s) {

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
        String surveys=s.get(position);
        @SuppressLint("ViewHolder") View satir =layoutInflater.inflate(R.layout.classicanswerone,null);
        TextView t = satir.findViewById(R.id.classicanswerone);
        t.setText(surveys);
        return satir;
    }
}
