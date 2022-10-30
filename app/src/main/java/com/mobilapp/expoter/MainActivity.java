package com.mobilapp.expoter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.mobilapp.expoter.Controller.SessionManager;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);


    /*tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager
        );


        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter=new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new HomeFragment(),"Products");
        vpAdapter.addFragment(new HomeFragment(),"Products2");
        vpAdapter.addFragment(new HomeFragment(),"Products3");

        viewPager.setAdapter(vpAdapter);
*/
    }
}