package com.example.messychef.checkbox_list_manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.messychef.R;
import com.example.messychef.utils.IndexValue;
import com.example.messychef.utils.SelectedIndex;

import java.util.ArrayList;

public class CheckBoxListFragment extends Fragment {

    public static final int LIST_ID = R.id.checkbox_list;

    final private CheckboxArrayAdapter adapter;


    public CheckBoxListFragment(Activity owner, ArrayList<IndexValue<String>> names) {
        adapter = new CheckboxArrayAdapter(owner, R.layout.checkbox_list_element, names);
    }

    public ArrayList<SelectedIndex> getSelected() {
        return adapter.getSelected();
    }

    public void setSelectedIndex(int index) {
        adapter.setSelectedIndex(index);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_box_list, container, false);
        AdapterView<ArrayAdapter<IndexValue<String>>> list = view.findViewById(R.id.checkbox_list);
        list.setAdapter(adapter);

        return view;
    }
}