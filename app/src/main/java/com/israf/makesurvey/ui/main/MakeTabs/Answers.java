package com.israf.makesurvey.ui.main.MakeTabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.Answer.ForAnswerSurvey;

import com.israf.makesurvey.ui.main.Answer.UserAnswer;
import com.israf.makesurvey.ui.main.MakeSurvey;
import com.israf.makesurvey.ui.main.PlaceholderFragment;
import com.israf.makesurvey.ui.main.SurveyAnswer;
import com.israf.makesurvey.ui.main.Surveylist;
import com.israf.makesurvey.ui.main.Surveys;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**

 */
public class Answers extends Fragment {
    private ArrayList<Surveys> Survey=new ArrayList<Surveys>();
    private ViewPager viewPager;  private  Surveys surveys;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answers,container, false);
        // Setting ViewPager for each Tabs
         viewPager = (ViewPager) view.findViewById(R.id.answerviewpager);
        Answers.AdapterAnswer adapter = new AdapterAnswer(view.getContext(),getChildFragmentManager());
        viewPager.setAdapter(adapter);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.answerresult_tabs);
        tabs.setupWithViewPager(viewPager);


        return view;

    }




    static class AdapterAnswer extends FragmentPagerAdapter {

        @StringRes
        private static final int[] TAB_TITLES = new int[]{R.string.tab_text_3, R.string.tab_text_4};

        private final Context mContext;
        public AdapterAnswer(Context context, FragmentManager manager) {
            super(manager);
            mContext=context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 :
                   ForAnswerSurvey forAnswerSurvey = new ForAnswerSurvey();
                    return forAnswerSurvey;
                case 1 :
                    UserAnswer userAnswer = new UserAnswer();
                    return userAnswer;

                default:        return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

    }
    @Subscribe
    public void onEvent(String s) {


        viewPager.setCurrentItem(0);



    }

    @Subscribe
    public void onEvent(Surveys Survey) {

        surveys=Survey;
        viewPager.setCurrentItem(1);



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