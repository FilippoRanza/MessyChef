package com.example.messychef.checkbox_list_manager;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.messychef.R;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckBoxListFragment extends Fragment {

    public static final int LIST_ID = R.id.checkbox_list;

    final private Activity owner;
    final private CheckboxArrayAdapter adapter;


    public CheckBoxListFragment(Activity owner, Stream<String> names) {
        this.owner = owner;
        ArrayList<String> arrayList = names.collect(Collectors.toCollection(ArrayList::new));
        adapter = new CheckboxArrayAdapter(owner, R.layout.checkbox_list_element, arrayList);
    }

    public ArrayList<Boolean> getSelected() {
        return adapter.getSelected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_box_list, container, false);
        AdapterView<ArrayAdapter<String>> list = view.findViewById(R.id.checkbox_list);
        list.setAdapter(adapter);

        return view;
    }
}