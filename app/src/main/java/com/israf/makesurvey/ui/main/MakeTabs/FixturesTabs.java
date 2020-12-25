package com.israf.makesurvey.ui.main.MakeTabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.MakeSurvey;
import com.israf.makesurvey.ui.main.PlaceholderFragment;
import com.israf.makesurvey.ui.main.Surveylist;

/**

 */
public class FixturesTabs extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fixtures_tabs,container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        Adapter adapter = new Adapter(view.getContext(),getChildFragmentManager());
        viewPager.setAdapter(adapter);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);


        return view;

    }




    static class Adapter extends FragmentPagerAdapter {

        @StringRes
        private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
        private final Context mContext;
        public Adapter(Context context,FragmentManager manager) {
            super(manager);
            mContext=context;
        }

        @Override
        public Fragment getItem(int position) {
             switch (position){
                case 0 :MakeSurvey makeSurvey = new MakeSurvey();
                    return makeSurvey;
                case 1 : Surveylist surveylist = new Surveylist();
                    return  surveylist;

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

}