package com.israf.makesurvey.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.MakeTabs.Answers;
import com.israf.makesurvey.ui.main.MakeTabs.FixturesTabs;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter  {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(MainActivity context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){

           case 0 :
               FixturesTabs fixturesTabs = new FixturesTabs();
               return fixturesTabs;
           case 1:
               Answers answers = new Answers();
               return answers;
           default:        return PlaceholderFragment.newInstance(position + 1);
       }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}