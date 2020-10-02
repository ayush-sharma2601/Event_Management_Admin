package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.transition.FragmentTransitionSupport;

import android.os.Bundle;

import com.ayush26.event_management_admin.Adapters.RegisterPagerAdapter;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding regBinding;
    RegisterPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regBinding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(regBinding.getRoot());

        pagerAdapter = new RegisterPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,2);

        regBinding.registrationPager.setAdapter(pagerAdapter);
    }


}