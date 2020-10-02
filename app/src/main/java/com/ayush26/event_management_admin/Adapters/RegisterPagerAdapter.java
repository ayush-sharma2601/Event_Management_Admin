package com.ayush26.event_management_admin.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ayush26.event_management_admin.Fragments.JudgeLogin;
import com.ayush26.event_management_admin.Fragments.LoginFragment;

public class RegisterPagerAdapter extends FragmentPagerAdapter {
    private int noOfTabs;
    int code;

    public RegisterPagerAdapter(@NonNull FragmentManager fm, int behavior, int noOfTabs) {
        super(fm, behavior);
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                return new LoginFragment();
            case 1:
                return new JudgeLogin();

            default:return null;
        }


    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
