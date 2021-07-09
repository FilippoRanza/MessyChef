package com.example.messychef.utils;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FragmentInstaller {

    private AppCompatActivity owner;

    public FragmentInstaller(AppCompatActivity owner) {
        this.owner = owner;
    }

    public void installFragment(int id, Fragment f) {
        owner.getSupportFragmentManager()
                .beginTransaction()
                .add(id, f)
                .commit();
    }


}
