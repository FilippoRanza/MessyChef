package com.example.messychef.utils;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

public class FragmentInstaller {

    private final AppCompatActivity owner;

    public FragmentInstaller(AppCompatActivity owner) {
        this.owner = owner;
    }

    public FragmentInstaller installFragment(int id, Fragment f) {
        owner.getSupportFragmentManager()
                .beginTransaction()
                .add(id, f)
                .commit();
        return this;
    }

    public FragmentInstaller removeFragment(int id) {
        FragmentContainerView fcv = owner.findViewById(id);
        fcv.removeAllViews();
        return this;
    }


}
