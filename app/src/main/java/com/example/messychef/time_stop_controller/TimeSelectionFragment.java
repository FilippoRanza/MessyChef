package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.messychef.R;

import java.util.ArrayList;


public class TimeSelectionFragment extends Fragment {


    private ListView hourList;
    private ListView minuteList;
    private ListView secondList;
    private TextView textView;

    private TimeUpdateListener listener;

    final private Activity owner;

    private static class LoopAdapter extends ArrayAdapter<String> {

        private ArrayList<String> items;
        private Activity owner;
        private int current;

        public LoopAdapter(Activity context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            items = objects;
            owner = context;
            current = initCurrent() - 2;
        }



        public View getView(int position, View convertView, ViewGroup parent) {
            position = position  % items.size();
            if(convertView == null) {
                LayoutInflater inflater = owner.getLayoutInflater();
                View view  = inflater.inflate(R.layout.list_element, parent, false);
                initializeTextView(position, view);
                convertView = view;
            } else {
                initializeTextView(position, convertView);
            }
            return convertView;
        }

        private void initializeTextView(int position, View view) {
            TextView tv = view.findViewById(R.id.text_view_list_element);
            tv.setText(items.get(position));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        public int getCurrent() {
            return current;
        }

        private int initCurrent() {
            int middle = Integer.MAX_VALUE / 2;
            int mod = middle % items.size();
            return middle - mod;
        }

        public int getCount() {
            return  Integer.MAX_VALUE;
        }

    }



    public TimeSelectionFragment(Activity owner) {
        this.owner = owner;
    }

    public TimeSelectionFragment setTimeUpdateListener(TimeUpdateListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_selection, container, false);
        hourList = initializeListView(view, R.id.hour_dial, 24);
        minuteList = initializeListView(view, R.id.minute_dial, 60);
        secondList = initializeListView(view, R.id.seconds_dial, 60);
        textView = view.findViewById(R.id.selected_time_value);

        return view;
    }



    private ArrayList<String> makeDialList(int count) {
        ArrayList<String> output = new ArrayList<>(count);
        for(int i = 0; i < count; i++){
            String value = String.valueOf(i);
            output.add(value);
        }
        return output;
    }

    private ListView initializeListView(View v, int id, int count) {
        ListView lv = v.findViewById(id);
        ArrayList<String> valueList = makeDialList(count);
        LoopAdapter stringArrayAdapter = new LoopAdapter(owner, R.layout.list_element, valueList);
        lv.setAdapter(stringArrayAdapter);
        lv.setSelection(stringArrayAdapter.getCurrent());
        lv.setDivider(null);
        lv.setItemsCanFocus(false);

        AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    lv.setSelection(lv.getFirstVisiblePosition());
                    updateTextView();
                    listener.callback(getHour(), getMinute(), getSecond());
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        };
        lv.setOnScrollListener(onScrollListener);


        return lv;
    }

    private void updateTextView() {
        String text = String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
        textView.setText(text);
    }


    public int getHour() {
        return getSelection(hourList) % 24;
    }

    public int getMinute() {
        return getSelection(minuteList) % 60;
    }

    public int getSecond() {
        return getSelection(secondList) % 60;
    }

    private int getSelection(ListView lv) {
        return lv.getFirstVisiblePosition() + 2;
    }

}