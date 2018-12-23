package com.example.rza.socialmedialogins;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Meal extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new Breakfast(),"Breakfast");
        pagerAdapter.addFragment(new Lunch(),"Lunch");
        pagerAdapter.addFragment(new Dinner(),"Dinner");
        viewPager.setAdapter(pagerAdapter);
    }
}
