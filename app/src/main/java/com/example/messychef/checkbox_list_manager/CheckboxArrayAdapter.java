package com.example.messychef.checkbox_list_manager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.messychef.R;
import com.example.messychef.utils.IndexValue;
import com.example.messychef.utils.SelectedIndex;

import java.util.ArrayList;


public class CheckboxArrayAdapter extends ArrayAdapter<IndexValue<String>> {

    final private Activity owner;
    final private ArrayList<IndexValue<String>> names;
    final private ArrayList<SelectedIndex> selected;

    public CheckboxArrayAdapter(Activity context, int resource, ArrayList<IndexValue<String>> objects) {
        super(context, resource, objects);
        owner = context;
        names = objects;
        selected = initializeSelected();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = owner.getLayoutInflater();
            View view = inflater.inflate(R.layout.checkbox_list_element, parent, false);
            initializeCheckbox(position, view);
            convertView = view;
        }
        return convertView;
    }

    public ArrayList<SelectedIndex> getSelected() {
        return selected;
    }

    public void setSelectedIndex(int index) {
        selected.get(index).setSelected(true);
    }

    private void initializeCheckbox(int position, View view) {
        CheckBox checkBox = view.findViewById(R.id.checkbox_list_element);
        checkBox.setText(names.get(position).getValue());
        if (selected.get(position).isSelected())
            checkBox.setChecked(true);

        checkBox.setOnClickListener((v) ->
                selected.get(position).setSelected(checkBox.isChecked())
        );
    }


    private ArrayList<SelectedIndex> initializeSelected() {
        ArrayList<SelectedIndex> output = new ArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++) {
            SelectedIndex index = new SelectedIndex(names.get(i).getIndex());
            output.add(index);
        }
        System.out.println(output);
        System.out.println(output.size());
        return output;
    }

}
