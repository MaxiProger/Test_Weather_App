package com.example.kolot.myweatherapp.activity.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.kolot.myweatherapp.R;
import com.example.kolot.myweatherapp.activity.adapters.SectionPagerAdapter;
import com.example.kolot.myweatherapp.activity.history.HistoryFragment;
import com.example.kolot.myweatherapp.activity.networking.DataSource;
import com.example.kolot.myweatherapp.activity.online.OnlineFragment;

public class MainActivity extends AppCompatActivity {

    private SectionPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager_container);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setUpViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

    }

    public void setUpViewPager(ViewPager viewPager){
        pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new OnlineFragment(), "Погода сейчас");
        pagerAdapter.addFragment(new HistoryFragment(), "История запросов");
        viewPager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new DataSource().getDisposable().dispose();
    }
}
