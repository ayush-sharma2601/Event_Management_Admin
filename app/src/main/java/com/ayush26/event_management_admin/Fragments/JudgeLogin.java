package com.ayush26.event_management_admin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.FragmentJudgeLoginBinding;

public class JudgeLogin extends Fragment {

    View view;
    FragmentJudgeLoginBinding judgeLoginBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_judge_login, container, false);
        return view;
    }
}