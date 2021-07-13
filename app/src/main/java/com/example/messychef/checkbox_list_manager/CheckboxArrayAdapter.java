package com.example.messychef.checkbox_list_manager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;


import com.example.messychef.R;

import java.util.ArrayList;
import java.util.List;


public class CheckboxArrayAdapter extends ArrayAdapter<String> {

    final private Activity owner;
    final private List<String> names;
    final private ArrayList<Boolean> selected;

    public CheckboxArrayAdapter(Activity context, int resource, List<String> objects) {
        super(context, resource, objects);
        owner = context;
        names = objects;
        selected = initializeSelected(names.size());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = owner.getLayoutInflater();
            View view  = inflater.inflate(R.layout.checkbox_list_element, parent, false);
            initializeCheckbox(position, view);
            convertView = view;
        }
        return convertView;
    }

    public ArrayList<Boolean> getSelected() {
        return selected;
    }

    private void initializeCheckbox(int position, View view) {
        CheckBox checkBox = view.findViewById(R.id.checkbox_list_element);
        checkBox.setText(names.get(position));
        checkBox.setOnClickListener((v) ->
            selected.set(position, checkBox.isChecked()));
    }




    private ArrayList<Boolean> initializeSelected(int size) {
        ArrayList<Boolean> output = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            output.add(false);
        }
        return output;
    }

}
