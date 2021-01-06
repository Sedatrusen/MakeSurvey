package com.israf.makesurvey.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.israf.makesurvey.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

        que.setText(s.getQuestion());
        String[] Questions = new String[5];
        if (s.getAnswer1().length()>10){
            Questions[0]=s.getAnswer1().substring(0,9)+"...";

        }else {
            Questions[0]=s.getAnswer1();
        }
        if (s.getAnswer2().length()>10){
            Questions[1]=s.getAnswer2().substring(0,9)+"...";

        }else {
            Questions[1]=s.getAnswer2();
        }
        if (s.getAnswer3().length()>10){
            Questions[2]=s.getAnswer3().substring(0,9)+"...";

        }else {
            Questions[2]=s.getAnswer3();
        }
        if (s.getAnswer4().length()>10){
            Questions[3]=s.getAnswer4().substring(0,9)+"...";

        }else {
            Questions[3]=s.getAnswer4();
        }



        HorizontalBarChart chart =  v.findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, a.getAns1()));
        entries.add(new BarEntry(1, a.getAns2()));
        entries.add(new BarEntry(2, a.getAns3()));
        entries.add(new BarEntry(3, a.getAns4()));

        BarDataSet set = new BarDataSet(entries, "Answers");

        BarData data = new BarData(set);

        chart.setData(data);
        chart.getDescription().setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Questions[(int) value];
            }
        });
        set.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.invalidate(); // refresh
        return v;
    }


}