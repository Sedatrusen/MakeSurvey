package com.israf.makesurvey.ui.main;

import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.israf.makesurvey.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedAnswer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedAnswer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   private SurveyAnswer s;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectedAnswer() {
        // Required empty public constructor
    }
   private AnswerString a;
    public void sendAnswerString(AnswerString s) {
        this.a=s;

    }
   public void  Survey(SurveyAnswer s){
        this.s=s;
   }
    public static SelectedAnswer newInstance(String param1, String param2) {
        SelectedAnswer fragment = new SelectedAnswer();
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
        View v= inflater.inflate(R.layout.fragment_selected_answer, container, false);
        TextView que = v.findViewById(R.id.SelectedAnswerQuestion);
        TextView ans1= v.findViewById(R.id.SelectedAnswer1);
        TextView ans2= v.findViewById(R.id.SelectedAnswer2);
        TextView ans3= v.findViewById(R.id.SelectedAnswer3);
        TextView ans4= v.findViewById(R.id.SelectedAnswer4);
        que.setText(s.getQuestion());
        ans1.setText("1-)"+s.getAnswer1());
        ans2.setText("2-)"+s.getAnswer2());
        ans3.setText("3-)"+s.getAnswer3());
        ans4.setText("4-)"+s.getAnswer4());




        BarChart chart = (BarChart) v.findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, a.getAns1()));
        entries.add(new BarEntry(1f, a.getAns2()));
        entries.add(new BarEntry(2f, a.getAns3()));
        entries.add(new BarEntry(3f, a.getAns4()));

        BarDataSet set = new BarDataSet(entries, "Answers");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        Description des = new Description();
        des.setText("");
        chart.setDescription(des);
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
        return v;
    }


}